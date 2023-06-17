package org.example.ui;

import org.example.gamestates.GameState;
import org.example.gamestates.Playing;
import org.example.main.Game;
import org.example.utilz.LoadSave;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static org.example.utilz.Constants.UI.PauseURMButton.*;

public class LevelCompletedOverlay {
    private Playing playing;
    private BufferedImage background;
    private UrmButton continueButton, menuButton;
    private int x,y,w,h;

    public LevelCompletedOverlay(Playing playing) {
        this.playing = playing;
        loadBackGround();
        createButton();
    }

    private void createButton() {
        int urmY = (int) (250 * Game.SCALE);
        int continueX = (int) (345 * Game.SCALE);
        int homeX = (int) (425 * Game.SCALE);
        continueButton = new UrmButton(continueX,urmY,URM_WIDTH,URM_HEIGHT,0);
        menuButton = new UrmButton(homeX,urmY,URM_WIDTH,URM_HEIGHT,2);

    }

    private void loadBackGround() {
        background = LoadSave.GetSpriteAtLas(LoadSave.LEVEL_COMPLETED_BACKGROUND);
        w = (int)(250*Game.SCALE);
        h = (int)(250*Game.SCALE);
        x = Game.GAME_WIDTH/2 - w/2;
        y = (int)(100*Game.SCALE);
    }
    public void update(){
        continueButton.update();
        menuButton.update();
    }
    public void draw(Graphics g){
        g.drawImage(background,x,y,w,h,null);
        continueButton.draw(g);
        menuButton.draw(g);
    }

    public void mouseDragged(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {
        if (isIn(e, continueButton))
            continueButton.setMousePress(true);
        else if (isIn(e, menuButton))
            menuButton.setMousePress(true);
    }

    public void mouseReleased(MouseEvent e) {
        if (isIn(e, continueButton)) {
            if (continueButton.isMousePress()){
                if(playing.getCurrentLevel()< playing.getMaxLevel())
                    playing.setCurrentLevel(playing.getCurrentLevel()+1);
                playing.resetAll();
            }
        } else if (isIn(e, menuButton)) {
            if (menuButton.isMousePress()) {
                GameState.state = GameState.MENU;
            }
        }
        continueButton.resetBoolean();
        menuButton.resetBoolean();
    }

    public void mouseMoved(MouseEvent e) {
        continueButton.setMouseOver(false);
        menuButton.setMouseOver(false);
        if (isIn(e, continueButton))
            continueButton.setMouseOver(true);
        else if (isIn(e, menuButton))
            menuButton.setMouseOver(true);
    }

    private boolean isIn(MouseEvent e, PauseButton pauseButton) {
        return pauseButton.getBounds().contains(e.getX(), e.getY());
    }
}
