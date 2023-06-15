package org.example.gamestates;

import org.example.main.Game;
import org.example.ui.MenuButton;

import java.awt.event.MouseEvent;

public class State {
    protected Game game;

    public State(Game game){
        this.game = game;
    }
    protected boolean isIn(MouseEvent e, MenuButton mb){
        return mb.getBounds().contains(e.getX(),e.getY());
    }

    public Game getGame() {
        return game;
    }
}
