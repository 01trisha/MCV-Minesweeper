package minesweeper.observ;

import minesweeper.model.Field;
import minesweeper.model.GameDifficult;
import minesweeper.model.GameState;
import minesweeper.record.RecordManager;

public class Context {
    private final String[][] field;
    private final String contextState;
    private final int time;
    private final boolean isOnlyTimeUpdate;
    private final String difficult;

    public Context(Field field, GameState gameState, int time, boolean isRunning, RecordManager recordManager, GameDifficult difficult) {
        this.field = ContextField.rebase(field);
        this.contextState = gameState.name();
        this.time = time;
        this.isOnlyTimeUpdate = isRunning;
        this.difficult = difficult.name();
    }

    public String[][] getField(){
        return field;
    }

    public String getGameState(){
        return contextState;
    }

    public int getTime(){
        return time;
    }

    public boolean getOnlyTimeUpdate(){
        return isOnlyTimeUpdate;
    }

    public String getDifficult(){
        return difficult;
    }
}
