package minesweeper.observ;

import minesweeper.model.Field;
import minesweeper.model.GameDifficult;
import minesweeper.model.GameState;
import minesweeper.record.RecordManager;

public class Context {
    private final ContextField field;
    private final ContextGameState contextState;
    private final int time;
    private final boolean isOnlyTimeUpdate;
    private final ContextDifficult difficult;
    private final int height;
    private final int width;

    public Context(Field field, GameState gameState, int time, boolean isRunning, RecordManager recordManager, GameDifficult difficult) {
        this.field = new ContextField(field);
        this.contextState = rebaseGameState(gameState);
        this.time = time;
        this.isOnlyTimeUpdate = isRunning;
        this.difficult = rebaseDifficult(difficult);
        this.height = field.getHeight();
        this.width = field.getWidth();
    }

    private static ContextGameState rebaseGameState(GameState gameState){
        return switch (gameState) {
//            case START -> ContextGameState.START;
            case PLAYING -> ContextGameState.PLAYING;
            case EXIT -> ContextGameState.EXIT;
            case CONFIGURING -> ContextGameState.CONFIGURING;
            case WON -> ContextGameState.WON;
            case LOST -> ContextGameState.LOST;
            default -> throw new IllegalArgumentException("Неизвестно состояние игры " + gameState);
        };
    }

    private static ContextDifficult rebaseDifficult(GameDifficult difficult){
        return switch (difficult){
            case EASY -> ContextDifficult.EASY;
            case MEDIUM -> ContextDifficult.MEDIUM;
            case HARD -> ContextDifficult.HARD;
            case CUSTOM -> ContextDifficult.CUSTOM;
            default -> throw new IllegalArgumentException("Неизвестная сложность игры " + difficult);
        };
    }

    public ContextField getField(){
        return field;
    }

    public ContextGameState getGameState(){
        return contextState;
    }

    public int getTime(){
        return time;
    }

    public boolean getOnlyTimeUpdate(){
        return isOnlyTimeUpdate;
    }

    public ContextDifficult getDifficult(){
        return difficult;
    }

    public int getHeight(){
        return height;
    }

    public int getWidth(){
        return width;
    }
}
