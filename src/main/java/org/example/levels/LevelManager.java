package org.example.levels;

import org.example.main.Game;
import org.example.utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.example.main.Game.*;
import static org.example.main.Game.TILES_SIZE;

public class LevelManager {
    private Game game;
    private BufferedImage[] loadSprites;
    private Level levelMatrix;
    private int level;
    public LevelManager(Game game, int level){
        this.game = game ;
        this.level = level;
        loadLevelMap(level);
    }
    public void loadLevelMap(int level){
        levelMatrix = new Level(LoadSave.getLevelMap(level));
        importOutsideSprites();
    }
    private void importOutsideSprites() {
        BufferedImage img = LoadSave.GetSpriteAtLas(LoadSave.LEVEL_ATLAS);
        loadSprites = new BufferedImage[48];
        for(int i=0;i<4;i++)
            for (int j=0;j<12;j++){
                int index = 12*i+j;
                loadSprites[index] = img.getSubimage(32*j,32*i,32,32);
            }

    }

    public void draw(Graphics g, int xLvlOffset){
        for (int h = 0; h < levelMatrix.getLevelMap().length; h++)
            for (int w = 0; w < levelMatrix.getLevelMap()[0].length; w++) {
                int index = levelMatrix.getSpriteIndex(h,w);
                if (index > -1 && index <48) {
                    g.drawImage(loadSprites[index], w * TILES_SIZE - xLvlOffset, h * TILES_SIZE,TILES_SIZE,TILES_SIZE, null);
                }

            }
    }
    public void  update(){
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Level getCurrentLevel(){
        return levelMatrix;
    }
}
