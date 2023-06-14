package org.example.levels;

import org.example.utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;

import static org.example.main.Game.*;

public class Level {
    private int[][] levelMap;

    public Level(int[][] levelMap){
        this.levelMap = levelMap;
    }

    public int getSpriteIndex(int x, int y){
        return levelMap[x][y];
    }

    public int[][] getLevelMap() {
        return levelMap;
    }
}
