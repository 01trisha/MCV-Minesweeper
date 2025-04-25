package minesweeper.controller;

import minesweeper.model.MinesweeperModel;
import minesweeper.view.MinesweeperView;
import minesweeper.view.gui.GuiView;
import minesweeper.view.text.ConsoleView;

import java.util.Scanner;

public class MinesweeperController {
    private final MinesweeperModel model;
    private final MinesweeperView view;
    private Scanner scanner = new Scanner(System.in);

    public MinesweeperController(String[] args){
        this.model = new MinesweeperModel();
        if (args[0].equals("text")){
            this.view = new ConsoleView();
        }else{
            this.view = new GuiView();
        }
        model.addObserver(view);
    }


    public void startGame(){
        view.printStartMessage();
        int[] param = getParameters();
        model.newGame(param[0], param[1], param[2]);
    }

    public int[] getParameters(){
        int[] parameters = new int[3];
        view.showMessage("Введите высоту поля: ");
        parameters[0] = scanner.nextInt();
        view.showMessage("Введите ширину поля: ");
        parameters[1] = scanner.nextInt();
        view.showMessage("Введите количество бомб: ");
        parameters[2] = scanner.nextInt();
        return parameters;
    }
}
