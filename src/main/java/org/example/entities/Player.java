package org.example.entities;

import org.example.main.Game;
import org.example.utilz.LoadSave;

import javax.imageio.ImageIO;
import java.awt.*;
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
    private int aniTick = 0, aniIndex = 0, aniSpeed = 15;
    private int playerAction = IDLE;
    private int playerDirection = -1;
    private boolean moving = false, attacking = false;
    private boolean left, right, up, down, jump;
    private int playerSpeed = 1;
    private int[][] levelMap;
    private float xDrawOffset = 65 * Game.SCALE;
    private float yDrawOffset = 68* Game.SCALE;
    //Gravity
    private float airSpeed = 0f;
    private float gravity = 0.04f * Game.SCALE;
    private float jumpSpeed = -2.25f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
    private boolean inAir = false;

    public Player(float x, float y, int width, int hetght) {
        super(x, y, width, hetght);
        loadAnimations();
        initHitbox(x, y, 16 * Game.SCALE, 28* Game.SCALE);
    }

    public void update() {
        updatePos();
        updateAnimationsTick();
        setAnimation();
    }

    public void render(Graphics g) {
        g.drawImage(animations[playerAction][aniIndex], (int) (hitbox.x - xDrawOffset), (int) (hitbox.y - yDrawOffset), width, height, null);
        drawHitbox(g);
    }

    public void setAnimation() {
        int startAni = playerAction;
        if (moving)
            playerAction = RUN;
        else
            playerAction = IDLE;
        if(inAir){
            if(airSpeed <0)
                playerAction = JUMP;
            else
                playerAction = FALL;
        }
        if (attacking)
            playerAction = ATTACK2;

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
            }
        }
    }

    private void updatePos() {
        moving = false;
        if(jump)
            jump();
        if (!right && !left && !inAir)
            return;
        float xSpeed = 0;
        if (left) {
            xSpeed -= playerSpeed;
        }
        if (right) {
            xSpeed += playerSpeed;
        }
        if(!inAir){
            if(!IsEntityOnFloor(hitbox,levelMap)){
                inAir=true;
            }
        }

        if (inAir) {
            if(CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width,hitbox.height,levelMap)){
                hitbox.y +=airSpeed;
                airSpeed+=gravity;
                updateXPos(xSpeed);
            }else{
                hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
                if(airSpeed > 0)
                    resetInAir();
                else
                    airSpeed=fallSpeedAfterCollision;
                updateXPos(xSpeed);
            }
        } else
            updateXPos(xSpeed);
        moving = true;

    }

    private void jump() {
        if(inAir)
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
        }else {
            hitbox.x = GetEntityXPosNextToWall(hitbox,xSpeed);
        }
    }



    private void loadAnimations() {
        animations = new BufferedImage[8][8];
        BufferedImage img = LoadSave.GetSpriteAtLas(LoadSave.PLAYER_ATLAS);
        for (int i = 0; i < animations.length; i++)
            for (int j = 0; j < animations[i].length; j++)
                animations[i][j] = img.getSubimage(250 * j, 250 * i, 250, 250);
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
}
