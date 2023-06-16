package org.example.entities;

import org.example.main.Game;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static org.example.utilz.Constants.EnemyConstants.*;
import static org.example.utilz.HelpMethods.*;
import static org.example.utilz.Constants.Directions.*;

public class Enemy extends Entity {
    protected int aniIndex, enemyState = 1, enemyType;
    protected int aniTick, aniSpeed = 25;
    protected boolean firstUpdate = true;
    protected boolean inAir;
    protected float fallSpeed;
    protected float gravity = 0.04f * Game.SCALE;
    protected float walkSpeed = 0.2f * Game.SCALE;
    protected float walkDir = LEFT;
    protected int minX, maxX;
    protected int tileY;
    protected int flipX = 0;
    protected int flipW = 1;
    protected float attackDistance = Game.TILES_SIZE;
    protected int maxHealth;
    protected int currentHealth;
    protected boolean active = true;
    protected boolean attackCheck;
    public Enemy(float x, float y, int width, int height, int enemyType, int minX, int maxX) {
        super(x, y, width, height);
        this.minX = minX;
        this.maxX = maxX;
        this.enemyType = enemyType;
        maxHealth = GetMaxHeath(enemyType);
        currentHealth = maxHealth;
    }

    protected void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(enemyType, enemyState)) {
                aniIndex = 0;
                switch (enemyState){
                    case ATTACK -> enemyState=IDLE;
                    case TAKE_HIT -> enemyState = RUN;
                    case DEATH -> active = false;
                }
            }
        }
    }

    public boolean canSeePlayer(int[][] levelMap, Player player) {
        int playerTileY = (int) (player.getHitbox().y / Game.TILES_SIZE);
//        if(((hitbox.x+fallSpeed)<=maxX&& walkDir == RIGHT)){
//
//        }
        if (playerTileY == tileY)
            if (isPlayerInRange(player)) {
                if (isSightClear(levelMap, hitbox, player.hitbox, tileY)) {
//                    maxX = Game.GAME_WIDTH*levelMap[0].length-(int)(35*Game.SCALE);
                    return true;
                }

            }
        return false;
    }

    protected boolean isPlayerInRange(Player player) {
        int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
        return absValue <= attackDistance * 3;
    }

    protected boolean isPlayerCloseForAttack(Player player) {
        int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
        int playerTileY = (int) (player.getHitbox().y / Game.TILES_SIZE);
        return absValue <= attackDistance && playerTileY == tileY;
    }

    protected void newSate(int enemyState) {
        this.enemyState = enemyState;
        aniIndex = 0;
        aniTick = 0;
    }

    protected void firstUpdateCheck(int[][] levelMap) {
        if (!IsEntityOnFloor(hitbox, levelMap)) {
            inAir = true;
        }
        firstUpdate = false;
    }

    protected void updateInAir(int[][] levelMap) {
        if (CanMoveHere(hitbox.x, hitbox.y, hitbox.width, hitbox.height, levelMap)) {
            hitbox.y += fallSpeed;
            fallSpeed += gravity;
        } else {
            inAir = false;
            hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, aniSpeed);
            tileY = (int) (hitbox.y / Game.TILES_SIZE);
        }
    }
    protected void checkEnemyHitPlayer(Rectangle2D.Float attackBox,Player player) {
        if(attackBox.intersects(player.hitbox))
            player.changeHealth(-GetEnemyDmg(enemyType));
        attackCheck=true;
    }
    protected void move(int[][] levelMap) {
        float xSpeed = 0;
        if (walkDir == LEFT)
            xSpeed -= walkSpeed;
        else
            xSpeed += walkSpeed;
        if (CanMoveHere(hitbox.x, hitbox.y, hitbox.width, hitbox.height, levelMap) && (hitbox.x + xSpeed) >= minX && (hitbox.x + xSpeed) <= maxX)
            if (!IsFloor(hitbox, xSpeed, levelMap) && IsEnemyOnFloor(hitbox, xSpeed, levelMap)) {
                hitbox.x += xSpeed;
                return;
            }
        changeWalkDir();
    }

    protected void turnTowardsPlayer(Player player) {
        if (player.hitbox.x > hitbox.x)
            walkDir = RIGHT;
        else
            walkDir = LEFT;
    }

    protected void updateFlip() {
        if (walkDir == LEFT) {
            flipX = width;
            flipW = -1;
        } else {
            flipX = 0;
            flipW = 1;
        }
    }
    public void hurt(int amount) {
        currentHealth -=amount;
        if (currentHealth<=0)
            newSate(DEATH);
        else
            newSate(TAKE_HIT);
    }
    private void changeWalkDir() {
        if (walkDir == LEFT)
            walkDir = RIGHT;
        else
            walkDir = LEFT;
    }
    public void resetEnemy() {
        hitbox.x=x;
        hitbox.y=y;
        firstUpdate=true;
        currentHealth=maxHealth;
        newSate(IDLE);
        active=true;
        fallSpeed=0;
    }
    public int getAniIndex() {
        return aniIndex;
    }

    public int getEnemyState() {
        return enemyState;
    }

    public boolean isActive() {
        return active;
    }
}
