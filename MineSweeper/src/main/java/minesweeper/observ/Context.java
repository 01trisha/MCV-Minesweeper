package minesweeper.observ;

import minesweeper.model.Field;
import minesweeper.model.GameDifficult;
import minesweeper.model.GameState;
import minesweeper.record.RecordManager;

public class Context {
    private final Field field;
    private final GameState gameState;
    private final int time;
    private final boolean isOnlyTimeUpdate;
    private final RecordManager recordManager;
    private final GameDifficult difficult;

    public Context(Field field, GameState gameState, int time, boolean isRunning, RecordManager recordManager, GameDifficult difficult) {
        this.field = field;
        this.gameState = gameState;
        this.time = time;
        this.isOnlyTimeUpdate = isRunning;
        this.recordManager = recordManager;
        this.difficult = difficult;
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

    public boolean getOnlyTimeUpdate(){
        return isOnlyTimeUpdate;
    }

    public RecordManager getRecordManager() {
        return recordManager;
    }

    public GameDifficult getDifficult(){
        return difficult;
    }
}
