package minesweeper.observ;

import minesweeper.model.Field;
import minesweeper.model.GameState;

public class Context {
    private final Field field;
    private final GameState gameState;

    public Context(Field field, GameState gameState) {
        this.field = field;
        this.gameState = gameState;
    }

    public Field getField(){
        return field;
    }

    public GameState getGameState(){
        return gameState;
    }
}
