package org.example.entities;

import org.example.main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static org.example.utilz.Constants.Directions.LEFT;
import static org.example.utilz.Constants.Directions.RIGHT;
import static org.example.utilz.Constants.EnemyConstants.*;
import static org.example.utilz.HelpMethods.*;
import static org.example.utilz.HelpMethods.IsEnemyOnFloor;

public class Goblin extends Enemy {
    private Rectangle2D.Float attackBox;
    private int attackBoxOffset;
    public Goblin(float x, float y, int minX, int maxX) {
        super(x, y, GOBLIN_WIDTH, GOBLIN_HEIGHT, GOBLIN, minX, maxX);
        initHitbox(x, y, 20 * Game.SCALE, 30 * Game.SCALE);
        initAttackBox();
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x,y,(int)(15*Game.SCALE),(int)(30*Game.SCALE));
        attackBoxOffset = (int)(5*Game.SCALE);
    }

    public void update(int[][] levelMap, Player player) {
        updateFlip();
        updateBehavior(levelMap, player);
        updateAnimationTick();
        updateAttackBox();
    }

    private void updateAttackBox() {
        if(walkDir == RIGHT){
            attackBox.x = hitbox.x + hitbox.width ;
        }
        else if(walkDir==LEFT){
            attackBox.x = hitbox.x- hitbox.width  +  attackBoxOffset;
        }
        attackBox.y = hitbox.y;
    }


    private void updateBehavior(int[][] levelMap, Player player) {
        if (firstUpdate)
            firstUpdateCheck(levelMap);
        if (inAir)
            updateInAir(levelMap);
        else {
            switch (enemyState) {
                case IDLE:
                    newSate(RUN);
                    break;
                case RUN:
                    if (canSeePlayer(levelMap, player))
                        turnTowardsPlayer(player);
                    if(isPlayerCloseForAttack(player))
                        newSate(ATTACK);
                    move(levelMap);
                    break;
                case ATTACK:
                    if(aniIndex==0)
                        attackCheck=false;
                    if(aniIndex==6 && !attackCheck)
                        checkEnemyHitPlayer(attackBox,player);
                case TAKE_HIT:
            }
        }
    }




    public void drawAttackBox(Graphics g, int xLvlOffset) {
        g.setColor(Color.PINK);
        g.drawRect((int)attackBox.x - xLvlOffset,(int)attackBox.y,(int)attackBox.width,(int)attackBox.height);
    }


}
