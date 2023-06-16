package org.example.gamestates;

import org.example.main.Game;
import org.example.ui.PauseButton;
import org.example.ui.SoundButton;
import org.example.ui.UrmButton;
import org.example.ui.VolumeButton;
import org.example.utilz.Constants;
import org.example.utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static org.example.utilz.Constants.UI.PauseSoundButton.*;
import static org.example.utilz.Constants.UI.PauseURMButton.*;
import static org.example.utilz.Constants.UI.VolumeButton.*;

public class PauseOverlay {
    private BufferedImage background;
    private SoundButton musicButton, sfxButton;
    private UrmButton resumeButton, replayButton, menuButton;
    private VolumeButton volumeButton;
    private int pauX, pauY, pauW, pauH;
    private Playing playing;

    public PauseOverlay(Playing playing) {
        this.playing = playing;
        loadBackground();
        createSoundButton();
        createURMButton();
        createVolumeButton();
    }

    private void createVolumeButton() {
        int volumeX = (int) (274 * Game.SCALE);
        int volumeY = (int) (235 * Game.SCALE);
        volumeButton = new VolumeButton(volumeX, volumeY, VOLUME_WIDTH, VOLUME_HEIGHT);
    }

    private void createURMButton() {
        int urmY = (int) (300 * Game.SCALE);
        int resumeX = (int) (315 * Game.SCALE);
        int newX = (int) (385 * Game.SCALE);
        int homeX = (int) (455 * Game.SCALE);
        resumeButton = new UrmButton(resumeX, urmY, URM_WIDTH, URM_HEIGHT, 0);
        replayButton = new UrmButton(newX, urmY, URM_WIDTH, URM_HEIGHT, 1);
        menuButton = new UrmButton(homeX, urmY, URM_WIDTH, URM_HEIGHT, 2);
    }

    private void createSoundButton() {
        int soundX = (int) (440 * Game.SCALE);
        int musicY = (int) (128 * Game.SCALE);
        int sfxY = (int) (158 * Game.SCALE);
        musicButton = new SoundButton(soundX, musicY, SOUND_WIDTH, SOUND_HEIGHT);
        sfxButton = new SoundButton(soundX, sfxY, SOUND_WIDTH, SOUND_HEIGHT);

    }

    private void loadBackground() {
        background = LoadSave.GetSpriteAtLas(LoadSave.PAUSE_BACKGROUND);
        pauW = (int) ((background.getWidth() + 125) * Game.SCALE);
        pauH = (int) ((background.getHeight() + 175) * Game.SCALE);
        pauX = Game.GAME_WIDTH / 2 - pauW / 2;
        pauY = (int) (35 * Game.SCALE);
    }

    public void update() {
        musicButton.update();
        sfxButton.update();
        resumeButton.update();
        replayButton.update();
        menuButton.update();
        volumeButton.update();
    }

    public void draw(Graphics g) {
        g.drawImage(background, pauX, pauY, pauW, pauH, null);
        musicButton.draw(g);
        sfxButton.draw(g);
        resumeButton.draw(g);
        replayButton.draw(g);
        menuButton.draw(g);
        volumeButton.draw(g);
    }

    public void mouseDragged(MouseEvent e) {
        volumeButton.changeSlider(e.getX());
    }

    public void mousePressed(MouseEvent e) {
        if (isIn(e, musicButton))
            musicButton.setMousePress(true);
        else if (isIn(e, sfxButton))
            sfxButton.setMousePress(true);
        else if (isIn(e, resumeButton))
            resumeButton.setMousePress(true);
        else if (isIn(e, replayButton))
            replayButton.setMousePress(true);
        else if (isIn(e, menuButton))
            menuButton.setMousePress(true);
    }

    public void mouseReleased(MouseEvent e) {
        if (isIn(e, musicButton)) {
            if (musicButton.isMousePress())
                musicButton.setMuted(!musicButton.isMuted());
        } else if (isIn(e, sfxButton)) {
            if (sfxButton.isMousePress())
                sfxButton.setMuted(!sfxButton.isMuted());
        } else if (isIn(e, resumeButton)) {
            if (resumeButton.isMousePress()) {
                playing.resumeGame();

            }
        } else if (isIn(e, replayButton)) {
            if (replayButton.isMousePress()) {
                playing.resetAll();
            }
        } else if (isIn(e, menuButton)) {
            if (menuButton.isMousePress()) {
                GameState.state = GameState.MENU;

            }
        }
        musicButton.resetBoolean();
        sfxButton.resetBoolean();
        resumeButton.resetBoolean();
        replayButton.resetBoolean();
        menuButton.resetBoolean();
    }

    public void mouseMoved(MouseEvent e) {
        musicButton.setMouseOver(false);
        sfxButton.setMouseOver(false);
        resumeButton.setMouseOver(false);
        menuButton.setMouseOver(false);
        replayButton.setMouseOver(false);
        if (isIn(e, musicButton))
            musicButton.setMouseOver(true);
        else if (isIn(e, sfxButton))
            sfxButton.setMouseOver(true);
        else if (isIn(e, resumeButton))
            resumeButton.setMouseOver(true);
        else if (isIn(e, replayButton))
            replayButton.setMouseOver(true);
        else if (isIn(e, menuButton))
            menuButton.setMouseOver(true);
    }

    private boolean isIn(MouseEvent e, PauseButton pauseButton) {
        return pauseButton.getBounds().contains(e.getX(), e.getY());
    }

}
