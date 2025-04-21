import minesweeper.controller.MinesweeperController;
import minesweeper.model.MinesweeperModel;
import minesweeper.view.MinesweeperView;
import minesweeper.view.gui.GuiView;
import minesweeper.view.text.ConsoleView;

import java.util.Objects;

public class Minesweeper {
    public static void main(String[] args) {
        MinesweeperView view;
        if (Objects.equals(args[0], "text")){
            view = new ConsoleView();
        }else{
            view = new GuiView();

        }
        MinesweeperModel model = new MinesweeperModel();


        MinesweeperController controller = new MinesweeperController(model, view);
    }
}