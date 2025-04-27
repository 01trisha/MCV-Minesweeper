package minesweeper.controller;

import minesweeper.CommandParser.CommandParser;
import minesweeper.model.GameState;
import minesweeper.model.MinesweeperModel;
import minesweeper.view.MinesweeperView;
import minesweeper.view.gui.GuiView;
import minesweeper.view.text.ConsoleView;

import java.util.Scanner;

public class MinesweeperController {
    private final MinesweeperModel model;
    private final MinesweeperView view;
    private final Scanner scanner = new Scanner(System.in);
    private final CommandParser parser;

    public MinesweeperController(String[] args){
        this.parser = new CommandParser();
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
        String input = scanner.nextLine();

        switch (input.trim()) {
            case "1":
                newGame();
                break;
            case "0":
                model.endGame();
                break;
        }

    }

    public void newGame(){
        int[] param = getParameters();
        model.newGame(param[0], param[1], param[2]);
        scanner.nextLine();

        while (true){
            switch (model.getGameState()){
                case PLAYING:
                    handlePlayingState();
                    break;
                case WON:
                    handleWonState();
                    break;
                case LOST:
                    handleLostState();
                    break;
                case EXIT:
                    model.endGame();
                    return;
            }
        }

    }

    private void handlePlayingState(){
        view.showMessage("""
                            Введите действие:
                            open x y - открыть клетку x y
                            flag x y - поставить флаг на клетку x y
                            exit - выйти из игры
                            """);
        String input = scanner.nextLine();
        String[] command = parser.parse(input);

        if (command[0].equals("open") && command.length == 3) {
            model.openCell(Integer.parseInt(command[1]), Integer.parseInt(command[2]));
        } else if(command[0].equals("exit")) {
            model.setGameState(GameState.EXIT);
        }else if (command[0].equals("flag") && command.length == 3) {
            model.toggleFlag(Integer.parseInt(command[1]), Integer.parseInt(command[2]));
        } else if (command[0].equals("")) {
        } else {
            System.out.println("Unknown command");
        }
//        model.setGameState(GameState.LOST);
    }

    private void handleWonState(){
        int command = scanner.nextInt();

        if (command == 1) {
           newGame();
        }else if (command == 0) {
            model.setGameState(GameState.EXIT);
        }else if (command == 2){

        }else{
            System.out.println("Unknown command");
        }
    }

    private void handleLostState(){
        int command = scanner.nextInt();

        if (command == 1) {
            newGame();
        }else if (command == 0){
            model.setGameState(GameState.EXIT);
        }else{
            System.out.println("Unknown command");
        }
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
