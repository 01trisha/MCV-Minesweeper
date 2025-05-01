package minesweeper.observ;

import minesweeper.model.Field;
import minesweeper.model.GameState;

public class Context {
    private final Field field;
    private final GameState gameState;
    private final int time;

    public Context(Field field, GameState gameState, int time) {
        this.field = field;
        this.gameState = gameState;
        this.time = time;
    }

    public Field getField(){
        return field;
    }

    public GameState getGameState(){
        return gameState;
    }

    public int getTime(){
        return time;
    }
}
