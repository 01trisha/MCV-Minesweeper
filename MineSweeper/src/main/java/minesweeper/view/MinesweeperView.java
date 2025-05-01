package minesweeper.view;

import minesweeper.controller.MinesweeperController;
import minesweeper.model.MinesweeperModel;
import minesweeper.observ.Observer;

public interface MinesweeperView extends Observer {
    void displayGame();
    void printStartMessage();
    void printEndMessage();
    void printLostMessage();
    void printField();
    void showMessage(String text);
    void printDifficult();
}
