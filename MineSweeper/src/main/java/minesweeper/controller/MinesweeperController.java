package minesweeper.controller;

import minesweeper.model.GameDifficult;
import minesweeper.model.MinesweeperModel;
import minesweeper.view.MinesweeperView;

public class MinesweeperController {
    private final MinesweeperModel model;
    private final MinesweeperView view;

    public MinesweeperController(MinesweeperModel model, MinesweeperView view) {
        this.model = model;
        this.view = view;
        model.addObserver(view);
    }

    public void startGame(GameDifficult difficult){
        model.newGame(difficult.getHeight(), difficult.getWidth(), difficult.getBomb(), difficult);
    }

    public void selectExitCommand(){
        model.endGame();
    }

    public void selectOpenCellCommand(int x, int y){
        model.openCell(x, y);
    }

    public void selectToggleFlagCommand(int x, int y){
        model.toggleFlag(x, y);
    }


}
