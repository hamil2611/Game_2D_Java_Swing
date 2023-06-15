package org.example.utilz;

import org.example.main.Game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.example.main.Game.*;

public class LoadSave {
    public final static String PLAYER_ATLAS = "Player.png";
    public final static String LEVEL_ATLAS = "map/outside_sprites.png";
    public final static String BUTTONS_ATLAS = "buttons_atlas.png";
    public final static String MENU_BACKGROUND = "menu_background.png";
    public final static String PAUSE_BACKGROUND="pause_menu.png";
    public final static String SOUND_BUTTONS ="sound_button.png";
    public final static String URM_BUTTONS = "urm_button.png";
    public final static String VOLUME_BUTTONS = "volume_button.png";
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

    public static int[][] getLevelMap()  {
        int[][] levelMap = new int[TILES_IN_HEIGHT][TILES_IN_WIDTH];
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("src/main/resources/static/map/level_one.txt"));
            List<String> lines = bufferedReader.lines().collect(Collectors.toList());
            for (int h = 0; h < TILES_IN_HEIGHT; h++) {
                String[] values = lines.get(h).split("\s* ");
                for (int w = 0; w < TILES_IN_WIDTH; w++) {
                    levelMap[h][w] = Integer.parseInt(values[w]);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return levelMap;
    }
}
