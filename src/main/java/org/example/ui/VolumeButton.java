package org.example.ui;

import org.example.utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.example.utilz.Constants.UI.VolumeButton.*;

public class VolumeButton extends PauseButton {
    private BufferedImage[] imgs;
    private BufferedImage slider;
    private int buttonX, minX, maxX;

    public VolumeButton(int x, int y, int width, int height) {
        super(x, y, width, height);
        loadImgs();
        minX = x + VOLUME_WIDTH;
        maxX = x + VOLUME_WIDTH * 5;
        buttonX = getX() + VOLUME_WIDTH * 3;
    }

    public void loadImgs() {
        BufferedImage img = LoadSave.GetSpriteAtLas(LoadSave.VOLUME_BUTTONS);
        imgs = new BufferedImage[6];
        for (int i = 0; i < imgs.length; i++) {
            imgs[i] = img.getSubimage(10 * i, 0, 10, 12);
        }
        slider = imgs[0];
    }

    public void update() {

    }

    public void changeSlider(int x) {
        if (x >= minX && x <= maxX) {
            buttonX = x;
        }

    }

    public void draw(Graphics g) {
        for (int i = 1; i <= 5; i++)
            g.drawImage(imgs[i], getX() + VOLUME_WIDTH * i, getY(), VOLUME_WIDTH, VOLUME_HEIGHT, null);
        g.drawImage(slider, buttonX, getY(), VOLUME_WIDTH, VOLUME_HEIGHT, null);
    }


}
