package org.example.main;

import org.example.entities.Player;
import org.example.gamestates.GameState;
import org.example.gamestates.Menu;
import org.example.gamestates.Playing;
import org.example.levels.LevelManager;

import java.awt.*;

public class Game implements Runnable {
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;
    private Playing playing;
    private Menu menu;

    public final static int TILES_DEFAULT_SIZE = 32;
    public final static float SCALE = 1.5f;
    public final static int TILES_IN_WIDTH = 26;
    public final static int TILES_IN_HEIGHT = 14;
    public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
    public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

    public Game() {
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();
        startGameLoop();
        initClass();
    }
    private void initClass(){
        menu = new Menu(this);
        playing = new Playing(this);
    }


    public void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();

    }

    public void update() {
        switch (GameState.state) {
            case MENU:
                menu.update();
                break;
            case PLAYING:
                playing.update();
                break;
            default:
                break;
        }
    }

    public void render(Graphics g) {
        switch (GameState.state) {
            case MENU:
                menu.draw(g);
                break;
            case PLAYING:
                playing.draw(g);
                break;
            default:
                break;
        }
    }

    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;

        long previousTime = System.nanoTime();

        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();

        double deltaUPS = 0;
        double deltaFPS = 0;
        while (true) {
            long currentTime = System.nanoTime();
            deltaUPS += (currentTime - previousTime) / timePerUpdate;
            deltaFPS += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;
            if (deltaUPS >= 1) {
                update();
                updates++;
                deltaUPS--;
            }
            if (deltaFPS >= 1) {
                gamePanel.repaint();
                frames++;
                deltaFPS--;
            }
            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }
    public void windowFocusLost() {
        if(GameState.state == GameState.PLAYING)
            playing.getPlayer().resetDirectionBoolean();
    }

    public Menu getMenu(){
        return this.menu;
    }
    public Playing getPlaying(){
        return this.playing;
    }

}
