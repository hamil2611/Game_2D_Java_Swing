package org.example;

import org.example.utilz.LoadSave;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainTest extends Canvas {

    PanelTest panelTest;
    public MainTest() {
        panelTest = new PanelTest(this);
    }


    public static void main(String[] args) {
        MainTest mainTest = new MainTest();
        JFrame jFrame = new JFrame();
        jFrame.add(mainTest.panelTest);
        jFrame.setSize(500, 500);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
    }
}
