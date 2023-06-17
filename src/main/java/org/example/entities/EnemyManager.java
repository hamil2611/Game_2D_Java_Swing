package org.example.entities;

import org.example.gamestates.Playing;
import org.example.main.Game;
import org.example.utilz.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static org.example.utilz.Constants.EnemyConstants.*;
import static org.example.utilz.Constants.PlayerConstants.*;

public class EnemyManager {
    private Playing playing;
    private BufferedImage[][] goblinImg;
    private List<Goblin> goblins = new ArrayList<>();
    private float xDrawOffset = 40 * Game.SCALE;
    private float yDrawOffset = 50 * Game.SCALE;


    public EnemyManager(Playing playing) {
        this.playing = playing;
        loadEnemyImg();
        addEnemies();
    }

    public void addEnemies() {
        goblins = LoadSave.getGoblins(playing.getCurrentLevel());
    }

    public void update(int[][] levelMap) {
        for (Goblin goblin : goblins)
            if (goblin.isActive())
                goblin.update(levelMap, playing.getPlayer());
    }

    public void draw(Graphics g, int xLvlOffset) {
        drawGoblins(g,xLvlOffset);
    }

    private void drawGoblins(Graphics g, int xLvlOffset) {
        int cnt=0;
        for (Goblin goblin : goblins){
            if(goblin.isActive()){
                g.drawImage(goblinImg[goblin.getEnemyState()][goblin.getAniIndex()], (int) (goblin.getHitbox().x -xDrawOffset) - xLvlOffset + goblin.flipX , (int) (goblin.getHitbox().y-yDrawOffset), GOBLIN_WIDTH* goblin.flipW, GOBLIN_HEIGHT, null);
//                goblin.drawHitbox(g,xLvlOffset);
//                goblin.drawAttackBox(g,xLvlOffset);
                cnt++;
            }
        }
        if(cnt==0)
            playing.setCompleted(true);
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox){
        for (Goblin goblin:goblins){
            if(attackBox.intersects(goblin.getHitbox())){
                goblin.hurt(GetPlayerDmg(ATTACK_NORMAL));
                return;
            }
        }
    }


    private void loadEnemyImg() {
        BufferedImage img = LoadSave.GetSpriteAtLas(LoadSave.GOBLIN_IMAGE);
        goblinImg = new BufferedImage[5][8];
        for (int i = 0; i < goblinImg.length; i++)
            for (int j = 0; j < goblinImg[i].length; j++) {
                goblinImg[i][j] = img.getSubimage(150 * j, 150 * i, 150, 150);
            }
    }

    public void resetAllEnemies() {
        for (Goblin goblin:goblins){
            goblin.resetEnemy();
        }
    }
}
