package org.example.utilz;

import org.example.entities.Goblin;
import org.example.main.Game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.example.main.Game.*;
import static org.example.utilz.Constants.EnemyConstants.*;

public class LoadSave {
    public final static String PLAYER_ATLAS = "Player.png";
    public final static String LEVEL_ATLAS = "map/outside_sprites.png";
    public final static String BUTTONS_ATLAS = "buttons_atlas.png";
    public final static String MENU_BACKGROUND = "menu_background.png";
    public final static String PAUSE_BACKGROUND = "pause_menu.png";
    public final static String SOUND_BUTTONS = "sound_button.png";
    public final static String URM_BUTTONS = "urm_button.png";
    public final static String VOLUME_BUTTONS = "volume_button.png";
    public final static String BACKGROUND_MENU = "background_menu1.png";
    public final static String PLAYING_BACKGROUND = "playing_background.png";
    public final static String GOBLIN_IMAGE = "goblin.png";
    public final static String HEALTH_POWER_BAR = "health_power_bar.png";

    public static BufferedImage GetSpriteAtLas(String fileName) {
        InputStream is = LoadSave.class.getResourceAsStream("/static/" + fileName);
        BufferedImage img;
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return img;
    }

    public static List<Goblin> getGoblins() {
        List<Goblin> list = new ArrayList<>();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("src/main/resources/static/map/level_one.txt"));
            List<String> lines = bufferedReader.lines().collect(Collectors.toList());
            for (int h = 0; h < lines.size(); h++) {
                String[] values = lines.get(h).split("\s* ");
                for (int w = 0; w < values.length; w++) {
                    if (Integer.parseInt(values[w]) == GOBLIN)
                        list.add(new Goblin(w * TILES_SIZE, h * TILES_SIZE, 0, w * TILES_SIZE + DISTANCE_MOVE_ENEMY));
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public static int[][] getLevelMap() {
        int[][] levelMap;
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("src/main/resources/static/map/level_one.txt"));
            List<String> lines = bufferedReader.lines().collect(Collectors.toList());
            levelMap = new int[lines.size()][lines.get(0).split("\s* ").length];
            for (int h = 0; h < levelMap.length; h++) {
                String[] values = lines.get(h).split("\s* ");
                for (int w = 0; w < levelMap[h].length; w++) {
                    levelMap[h][w] = Integer.parseInt(values[w]);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return levelMap;
    }
}
