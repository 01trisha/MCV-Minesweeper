//import minesweeper.controller.MinesweeperController;
import minesweeper.controller.MinesweeperController;
import minesweeper.model.MinesweeperModel;
import minesweeper.view.MinesweeperView;
//import minesweeper.view.gui.GuiView;
//import minesweeper.view.text.ConsoleView;
import minesweeper.view.text.ConsoleView;

public class Minesweeper {
    public static void main(String[] args) {
        MinesweeperModel model = new MinesweeperModel();
        MinesweeperView view;
//        if (args[0].equals("text")){
            view = new ConsoleView();
//        }else{
//            view = new GuiView();
//        }
        MinesweeperController controller = new MinesweeperController(model, view);
        try {
            view.setController(controller);
            view.start();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}