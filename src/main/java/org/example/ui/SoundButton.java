package org.example.ui;

import org.example.utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.example.utilz.Constants.UI.PauseSoundButton.*;

public class SoundButton extends PauseButton {
    private BufferedImage[] soundImgs;
    private boolean mouseOver, mousePress;
    private boolean muted;
    private int rowIndex, colIndex;

    public SoundButton(int x, int y, int width, int height) {
        super(x, y, width, height);
        loadSoundImgs();
    }

    private void loadSoundImgs() {
        BufferedImage img = LoadSave.GetSpriteAtLas(LoadSave.SOUND_BUTTONS);
        soundImgs = new BufferedImage[2];
        for (int i = 0; i < soundImgs.length; i++)
            soundImgs[i] = img.getSubimage(i * 14, 0, 14, 14);
    }

    public void update() {
        if (muted)
            rowIndex = 1;
        else
            rowIndex = 0;
        colIndex = 0;
        if (mouseOver)
            colIndex = 1;

    }

    public void draw(Graphics g) {
        g.drawImage(soundImgs[rowIndex], getX() +3*colIndex, getY()+3*colIndex, SOUND_WIDTH-6*colIndex, SOUND_HEIGHT-6*colIndex, null);
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

    public boolean isMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }

    public void resetBoolean(){
        this.mouseOver=false;
        this.mousePress=false;
    }

}
