package minesweeper.controller;

import minesweeper.model.MinesweeperModel;
import minesweeper.view.MinesweeperView;
import minesweeper.view.gui.GuiView;
import minesweeper.view.text.ConsoleView;

public class MinesweeperController {
    private final MinesweeperModel model;
    private final MinesweeperView view;

    public MinesweeperController(MinesweeperModel model, MinesweeperView view){
        this.model = model;
        this.view = view;
        model.addObserver(view);
    }

}
