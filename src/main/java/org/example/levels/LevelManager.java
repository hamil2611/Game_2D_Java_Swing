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
    private Level levelOne;
    public LevelManager(Game game){
        this.game = game ;
        levelOne = new Level(LoadSave.getLevelMap());
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
        for (int h = 0; h < levelOne.getLevelMap().length; h++)
            for (int w = 0; w < levelOne.getLevelMap()[0].length; w++) {
                int index = levelOne.getSpriteIndex(h,w);
                if (index > -1 && index <48) {
                    g.drawImage(loadSprites[index], w * TILES_SIZE - xLvlOffset, h * TILES_SIZE,TILES_SIZE,TILES_SIZE, null);
                }

            }
    }
    public void  update(){

    }

    public Level getCurrentLevel(){
        return levelOne;
    }
}
