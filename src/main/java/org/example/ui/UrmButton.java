package org.example.ui;

import org.example.utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.example.utilz.Constants.UI.PauseURMButton.*;

public class UrmButton extends PauseButton{
    private BufferedImage[] urmImgs;
    private int imgIndex, index;
    private boolean mouseOver, mousePress;
    public UrmButton(int x, int y, int width, int height, int colIndex) {
        super(x, y, width, height);
        this.imgIndex = colIndex;
        loadImgs();
    }

    private void loadImgs() {
        BufferedImage img = LoadSave.GetSpriteAtLas(LoadSave.URM_BUTTONS);
        urmImgs = new BufferedImage[3];
        for(int i=0;i<urmImgs.length;i++)
            urmImgs[i] = img.getSubimage(14*i,0,14,14);
    }

    public void update(){
        index=0;
        if(mouseOver)
            index=1;
        if(mousePress)
            index=2;
    }

    public void draw(Graphics g){
        g.drawImage(urmImgs[imgIndex], getX() +3*index, getY()+3*index, getWidth()-6*index, getHeight()-6*index, null);
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

    public void resetBoolean(){
        this.mouseOver=false;
        this.mousePress=false;
    }
}
