package minesweeper.controller;

import minesweeper.model.GameDifficult;
import minesweeper.model.GameState;
import minesweeper.model.MinesweeperModel;
import minesweeper.record.RecordManager;
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
        model.newGame(difficult);
    }

    public void startGame(int[] param){
        model.newGame(param);
    }

    public void selectExitCommand(){
        model.endGame();
    }

    public void selecBackCommand(){
        model.setGameState(GameState.LOST);
    }

    public void selectOpenCellCommand(int x, int y){
        model.openCell(x, y);
    }

    public void selectToggleFlagCommand(int x, int y){
        model.toggleFlag(x, y);
    }

    public void selectSaveRecords(String name){
        model.saveResult(name);
    }

    public void selectClearAllRecords(){
        model.clearResults();
    }

    public RecordManager getRecordManager(){
        return model.getRecordManager();
    }

}
