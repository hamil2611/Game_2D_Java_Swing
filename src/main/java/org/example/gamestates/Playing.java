package org.example.gamestates;

import org.example.entities.EnemyManager;
import org.example.entities.Player;
import org.example.levels.LevelManager;
import org.example.main.Game;
import org.example.ui.GameOverOverlay;
import org.example.ui.LevelCompletedOverlay;
import org.example.utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Playing extends State implements StateMethods {
    private Player player;
    private LevelManager levelManager;
    private PauseOverlay pauseOverlay;
    private EnemyManager enemyManager;
    private GameOverOverlay gameOverOverlay;
    private LevelCompletedOverlay levelCompletedOverlay;
    private boolean paused = false;
    private int currentLevel=1 , maxLevel = 2;
    private int xLvlOffset;
    private int leftBorder = (int) (0.2 * Game.GAME_WIDTH);
    private int rightBorder = (int) (0.8 * Game.GAME_WIDTH);
    private int lvlTilesWide = LoadSave.getLevelMap(currentLevel)[0].length;
    private int maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
    private int maxLvlOffsetX = maxTilesOffset * Game.TILES_SIZE;
    private BufferedImage playingBackground;
    private boolean gameOver;
    private boolean completed = false;


    public Playing(Game game) {
        super(game);
        initClasses();
    }

    private void initClasses() {
        playingBackground = LoadSave.GetSpriteAtLas(LoadSave.PLAYING_BACKGROUND);
        levelManager = new LevelManager(game,currentLevel);
        enemyManager = new EnemyManager(this);
        player = new Player(150 * Game.SCALE, 150 * Game.SCALE, (int) (150 * Game.SCALE), (int) (150 * Game.SCALE), this);
        player.loadLevelMap(levelManager.getCurrentLevel().getLevelMap());
        pauseOverlay = new PauseOverlay(this);
        gameOverOverlay = new GameOverOverlay(this);
        levelCompletedOverlay = new LevelCompletedOverlay(this);
    }

    @Override
    public void update() {
        if (!paused && !gameOver && !completed) {
            levelManager.update();
            player.update();
            enemyManager.update(levelManager.getCurrentLevel().getLevelMap());
            checkCloseToBorder();
        }
        pauseOverlay.update();
        levelCompletedOverlay.update();
    }

    private void checkCloseToBorder() {
        int playerX = (int) player.getHitbox().x;
        int diff = playerX - xLvlOffset;
        if (diff > rightBorder)
            xLvlOffset += diff - rightBorder;
        else if (diff < leftBorder) {
            xLvlOffset += diff - leftBorder;
        }
        if (xLvlOffset > maxLvlOffsetX)
            xLvlOffset = maxLvlOffsetX;
        else if (xLvlOffset < 0)
            xLvlOffset = 0;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(playingBackground, 0 - xLvlOffset, 0, levelManager.getCurrentLevel().getLevelMap()[0].length * Game.TILES_SIZE, Game.GAME_HEIGHT, null);
        levelManager.draw(g, xLvlOffset);
        player.render(g, xLvlOffset);
        enemyManager.draw(g, xLvlOffset);

        if (paused) {
            g.setColor(new Color(0, 0, 0, 100));
            g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
            pauseOverlay.draw(g);
        } else if (gameOver) {
            gameOverOverlay.draw(g);
        }else if(completed){
            levelCompletedOverlay.draw(g);
        }

    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        enemyManager.checkEnemyHit(attackBox);
    }

    public void resetAll() {
        gameOver=false;
        paused=false;
        completed=false;
        player.resetAll();
        levelManager.loadLevelMap(currentLevel);
        enemyManager.addEnemies();
        player.loadLevelMap(levelManager.getCurrentLevel().getLevelMap());
        enemyManager.resetAllEnemies();

    }

    public void resumeGame() {
        this.paused = false;
    }

    public void pauseGame() {
        this.paused = true;
    }

    public void windowFocusLost() {
        player.resetDirectionBoolean();
    }

    public Player getPlayer() {
        return this.player;
    }

    public void mouseDragged(MouseEvent e) {
        pauseOverlay.mouseDragged(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!gameOver)
            if (e.getButton() == MouseEvent.BUTTON1)
                player.setAttacking(true);

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!gameOver)
            if (paused)
                pauseOverlay.mousePressed(e);
            else if(completed)
                levelCompletedOverlay.mousePressed(e);

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!gameOver)
            if (paused)
                pauseOverlay.mouseReleased(e);
            else if(completed)
                levelCompletedOverlay.mouseReleased(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (!gameOver)
            if (paused)
                pauseOverlay.mouseMoved(e);
            else if(completed)
                levelCompletedOverlay.mouseMoved(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gameOver)
            gameOverOverlay.KeyPressed(e);
        else if (!paused) {
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
        if (gameOver) {

        } else if (!paused) {
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

    public int getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public void setCompleted(boolean completed){
        this.completed = completed;
    }
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
}
