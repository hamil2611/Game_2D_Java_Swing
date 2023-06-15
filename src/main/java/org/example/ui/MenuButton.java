package org.example.ui;

import org.example.gamestates.GameState;
import org.example.utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.example.utilz.Constants.UI.Button.*;

public class MenuButton {
    private int xPos, yPos, rowIndex, index;
    private int xOffsetCenter = B_WIDTH / 2;
    private GameState state;
    private BufferedImage[] imgs;
    private Rectangle bounds;
    private boolean mouseOver, mousePress;

    public MenuButton(int xPos, int yPos, int rowIndex, GameState state) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.rowIndex = rowIndex;
        this.state = state;
        loadImgs();
        initBounds();
    }

    private void initBounds() {
        bounds = new Rectangle(xPos - xOffsetCenter, yPos, B_WIDTH, B_HEIGHT);
    }

    private void loadImgs() {
        imgs = new BufferedImage[3];
        BufferedImage img = LoadSave.GetSpriteAtLas(LoadSave.BUTTONS_ATLAS);
        for(int i=0;i<imgs.length;i++){
            imgs[i] = img.getSubimage(0,i*28,70,28);
        }
    }

    public void draw(Graphics g) {
        g.drawImage(imgs[rowIndex], xPos - xOffsetCenter + 5 * index, yPos + 5 * index, B_WIDTH - 10 * index, B_HEIGHT - 10 * index, null);
    }

    public void update() {
        index = 0;
        if (mouseOver)
            index = 1;
        if (mousePress)
            index = 2;

    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePress() {
        return mousePress;
    }

    public void setMousePress(boolean mousePress) {
        this.mousePress = mousePress;
    }

    public Rectangle getBounds() {
        return this.bounds;
    }

    public void applyGamestate() {
        GameState.state = state;
    }

    public void resetBoolean() {
        mousePress = false;
        mouseOver = false;
    }
}

