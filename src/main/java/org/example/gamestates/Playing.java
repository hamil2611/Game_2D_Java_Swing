package org.example.gamestates;

import org.example.entities.Player;
import org.example.levels.LevelManager;
import org.example.main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Playing extends State implements StateMethods{
    private Player player;
    private LevelManager levelManager;
    private PauseOverlay pauseOverlay;
    private boolean paused=false;

    public Playing(Game game) {
        super(game);
        initClasses();
    }

    private void initClasses() {
        levelManager = new LevelManager(game);
        player = new Player(150*Game.SCALE, 150*Game.SCALE, (int) (150 * Game.SCALE), (int) (150 * Game.SCALE));
        player.loadLevelMap(levelManager.getCurrentLevel().getLevelMap());
        pauseOverlay = new PauseOverlay(this);

    }

    public void resumeGame(){
        this.paused=false;
    }
    public void pauseGame(){
        this.paused=true;
    }
    public void windowFocusLost() {
        player.resetDirectionBoolean();
    }

    public Player getPlayer() {
        return this.player;
    }

    @Override
    public void update() {
        if(!paused){
            levelManager.update();
            player.update();
        }
        pauseOverlay.update();
    }

    @Override
    public void draw(Graphics g) {
        levelManager.draw(g);
        player.render(g);
        if(paused)
            pauseOverlay.draw(g);

    }

    public void mouseDragged(MouseEvent e){
        pauseOverlay.mouseDragged(e);
    }
    @Override
    public void mouseClicked(MouseEvent e) {
//        if(e.getButton()==MouseEvent.BUTTON1)
//            player.setAttacking(true);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("PRESS");
        if (paused)
            pauseOverlay.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (paused)
            pauseOverlay.mouseReleased(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (paused)
            pauseOverlay.mouseMoved(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(!paused){
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    player.setLeft(true);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(true);
                    break;
                case KeyEvent.VK_SPACE:
                    player.setJump(true);
                    break;
                case KeyEvent.VK_ESCAPE:
                    pauseGame();
                    break;
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                player.setLeft(false);
                break;
            case KeyEvent.VK_D:
                player.setRight(false);
                break;
            case KeyEvent.VK_SPACE:
                player.setJump(false);
                break;
        }
    }
}
