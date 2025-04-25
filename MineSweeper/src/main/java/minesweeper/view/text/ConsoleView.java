package minesweeper.view.text;

import minesweeper.controller.MinesweeperController;
import minesweeper.model.Field;
import minesweeper.model.GameState;
import minesweeper.observ.Context;
import minesweeper.view.MinesweeperView;

import java.util.Scanner;

public class ConsoleView implements MinesweeperView {
    private Field field;
    private GameState gameState;


    @Override
    public void displayGame() {
        switch (gameState){
            case PLAYING:
                printField();
                break;
            case LOST:
                printLostMessage();
        }
    }

    @Override
    public void update(Context context) {
        field = context.getField();
        gameState = context.getGameState();
        displayGame();
    }

    @Override
    public void printField(){
        for(int i = 0; i < field.getHeight(); i++){
            for(int j = 0; j < field.getWidth(); j++){
//                if (field.isCellOpen(i, j)){
//                    System.out.println(field.getCell(i, j).getSym());
//                }else if(field.isCellFlag(i, j)){
//                    System.out.println("F");
//                }else{
//                    System.out.println("*");
//                }
                System.out.print("* ");

            }
            System.out.println();
        }
    }
    @Override
    public void printStartMessage(){
        System.out.println("""
                Minesweeper by trisha:
                Выберите пожалуйста команду:
                """);
    }
    @Override
    public void printEndMessage(){

    }
    @Override
    public void printLostMessage(){}
    private void printWonMessage(){}

    public void showMessage(String str){
        System.out.print(str);
    }

}
