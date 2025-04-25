import minesweeper.controller.MinesweeperController;

public class Minesweeper {
    public static void main(String[] args) {
        MinesweeperController controller = new MinesweeperController(args);
        try {
            controller.startGame();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}