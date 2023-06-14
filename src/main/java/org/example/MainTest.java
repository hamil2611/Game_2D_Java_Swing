package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainTest extends Canvas {

    public void paint(Graphics g){
        try {
            File file = new File("src/main/resources/static/map/outside_sprites.png");
            BufferedImage img = ImageIO.read(file);
            g.drawImage(img.getSubimage(32*2,0,32,32),0,0,null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        MainTest mainTest = new MainTest();
        JFrame jFrame = new JFrame();
        jFrame.add(mainTest);
        jFrame.setSize(500,500);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
    }
}
