package org.example.entities;

import org.example.gamestates.Playing;
import org.example.main.Game;
import org.example.utilz.LoadSave;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLOutput;

import static org.example.utilz.Constants.Directions.*;
import static org.example.utilz.Constants.Directions.DOWN;
import static org.example.utilz.Constants.PlayerConstants.*;
import static org.example.utilz.HelpMethods.*;


public class Player extends Entity {
    private BufferedImage[][] animations;
    private int aniTick = 0, aniIndex = 0, aniSpeed = 12;
    private int playerAction = IDLE;
    private int playerDirection = -1;
    private boolean moving = false, attacking = false;
    private boolean left, right, up, down, jump;
    private int playerSpeed = 1;
    private int[][] levelMap;
    private float xDrawOffset = 65 * Game.SCALE;
    private float yDrawOffset = 68 * Game.SCALE;
    //Gravity
    private float airSpeed = 0f;
    private float gravity = 0.04f * Game.SCALE;
    private float jumpSpeed = -2.25f * Game.SCALE;
    private boolean inAir = false;
    //Life Bar
    private BufferedImage[][] statusBarImgs;
    private int maxHealth = 100;
    private int currentHealth = maxHealth;
    private Rectangle2D.Float attackBox;
    private int flipX = 0;
    private int flipW = 1;
    private boolean attackChecked;
    private Playing playing;

    public Player(float x, float y, int width, int hetght, Playing playing) {
        super(x, y, width, hetght);
        this.playing = playing;
        loadAnimations();
        initHitbox(x, y, 18 * Game.SCALE, 28 * Game.SCALE);
        initAttackBox();
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(hitbox.x + hitbox.width + (int) (20 * Game.SCALE), y, (int) (40 * Game.SCALE), (int) (40 * Game.SCALE));
    }

    public void update() {
        if(currentHealth <=0){
            playerAction = DEATH;
            playing.setGameOver(true);
        }
        updatePos();
        updateAttackBox();
        if (attacking)
            checkAttack();
        updateAnimationsTick();
        setAnimation();

    }

    private void checkAttack() {
        if (attackChecked || aniIndex != 4)
            return;
        attackChecked = true;
        playing.checkEnemyHit(attackBox);
    }

    private void updateAttackBox() {
        if (right) {
            attackBox.x = hitbox.x + hitbox.width + (int) (20 * Game.SCALE);
        } else if (left) {
            attackBox.x = hitbox.x - hitbox.width - (int) (40 * Game.SCALE);
        }
        attackBox.y = hitbox.y - (int) (10 * Game.SCALE);
    }

    public void render(Graphics g, int xLvlOffset) {
        g.drawImage(animations[playerAction][aniIndex], (int) (hitbox.x - xDrawOffset) - xLvlOffset + flipX, (int) (hitbox.y - yDrawOffset), width * flipW, height, null);
//        drawHitbox(g, xLvlOffset);
//        drawAttackBox(g, xLvlOffset);
        drawUI(g);
    }

    private void drawAttackBox(Graphics g, int xLvlOffset) {
        g.setColor(Color.PINK);
        g.drawRect((int) attackBox.x - xLvlOffset, (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
    }

    private void drawUI(Graphics g) {
        for (int i = 0; i < statusBarImgs[0].length; i++) {
            g.drawImage(statusBarImgs[0][i], (int) ((30 + 32 * i) * Game.SCALE), (int) (10 * Game.SCALE), (int) (48 * Game.SCALE), (int) (48 * Game.SCALE), null);
        }
        g.setColor(Color.RED);
        g.fillRect((int) (55 * Game.SCALE), (int) (30 * Game.SCALE), (int) (148 * (currentHealth / (float) 100) * Game.SCALE), (int) (5 * Game.SCALE));
    }

    public void setAnimation() {
        int startAni = playerAction;
        if (moving)
            playerAction = RUN;
        else
            playerAction = IDLE;
        if (inAir) {
            if (airSpeed < 0)
                playerAction = JUMP;
            else
                playerAction = FALL;
        }
        if (attacking){
            playerAction = ATTACK1;
//            if(startAni!=ATTACK1){
//                aniIndex=1;
//                aniTick = 0;
//                return;
//            }
        }
        if (startAni != playerAction)
            resetAniTick();
    }

    private void resetAniTick() {
        aniTick = 0;
        aniIndex = 0;
    }

    private void updateAnimationsTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= getFramesAmount(playerAction)) {
                aniIndex = 0;
                attacking=false;
                attackChecked=false;
            }
        }
    }

    private void updatePos() {
        moving = false;
        if (!inAir) {
            if (!IsEntityOnFloor(hitbox, levelMap)) {
                inAir = true;
            }
        }
        if (jump)
            jump();
        if (!inAir)
            if ((!right && !left) || (right && left))
                return;
        float xSpeed = 0;
        if (left) {
            xSpeed -= playerSpeed;
            flipX = width;
            flipW = -1;
        }
        if (right) {
            xSpeed += playerSpeed;
            flipX = 0;
            flipW = 1;
        }
        if (inAir) {
            if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, levelMap)) {
                hitbox.y += airSpeed;
                airSpeed += gravity;
                updateXPos(xSpeed);
            } else {
                hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
                float fallSpeedAfterCollision = 0.5f * Game.SCALE;
                if (airSpeed > 0)
                    resetInAir();
                else
                    airSpeed = fallSpeedAfterCollision;
                updateXPos(xSpeed);
            }
        } else
            updateXPos(xSpeed);
        moving = true;

    }

    public void changeHealth(int value) {
        currentHealth += value;
        if (currentHealth <= 0)
            currentHealth = 0;
        if (currentHealth >= maxHealth)
            currentHealth = maxHealth;
    }

    private void jump() {
        if (inAir)
            return;
        inAir = true;
        airSpeed = jumpSpeed;
    }

    private void resetInAir() {
        this.inAir = false;
        airSpeed = 0;
    }

    private void updateXPos(float xSpeed) {
        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, levelMap)) {
            hitbox.x += xSpeed;
        } else {
            hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
        }
    }


    private void loadAnimations() {
        animations = new BufferedImage[8][8];
        BufferedImage img = LoadSave.GetSpriteAtLas(LoadSave.PLAYER_ATLAS);
        for (int i = 0; i < animations.length; i++)
            for (int j = 0; j < animations[i].length; j++)
                animations[i][j] = img.getSubimage(250 * j, 250 * i, 250, 250);
        BufferedImage statusImg = LoadSave.GetSpriteAtLas(LoadSave.HEALTH_POWER_BAR);
        statusBarImgs = new BufferedImage[2][5];
        for (int i = 0; i < statusBarImgs.length; i++)
            for (int j = 0; j < statusBarImgs[i].length; j++) {
                statusBarImgs[i][j] = statusImg.getSubimage(j * 32, i * 32, 32, 32);
            }
    }


    public void resetDirectionBoolean() {
        left = false;
        right = false;
        up = false;
        down = false;
    }

    public void loadLevelMap(int[][] levelMap) {
        this.levelMap = levelMap;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public void resetAll() {
        resetDirectionBoolean();
        resetInAir();
        attacking=false;
        moving=false;
        playerAction= IDLE;
        currentHealth=maxHealth;
        hitbox.x= x;
        hitbox.y=y;
        if(!IsEntityOnFloor(hitbox,levelMap))
            inAir=true;
    }
}
