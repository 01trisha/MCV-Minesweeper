package minesweeper.Parser;

import minesweeper.model.GameDifficult;
import minesweeper.observ.ContextDifficult;

public class DifficultParser {
    public GameDifficult parseDifficult(ContextDifficult difficult){
        return switch (difficult){
            case EASY -> GameDifficult.EASY;
            case MEDIUM -> GameDifficult.MEDIUM;
            case HARD -> GameDifficult.HARD;
            case CUSTOM -> GameDifficult.CUSTOM;
            default -> throw new IllegalArgumentException("Неизвестная сложность " + difficult);
        };
    }
}
