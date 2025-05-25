package minesweeper.view;

import minesweeper.controller.MinesweeperController;
import minesweeper.observ.Observer;
import minesweeper.record.RecordManager;

public interface MinesweeperView extends Observer {
    void setController(MinesweeperController controller);
    void start();
//    void displayGame();
//    void printStartMessage();
//    void printEndMessage();
//    void printLostMessage();
//    void printField();
//    void showMessage(String text);
//    void printDifficult();
}
