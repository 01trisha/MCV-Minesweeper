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
                break;
            case WON:
                printWonMessage();
        }
    }

    @Override
    public void update(Context context) {
        field = context.getField();
        gameState = context.getGameState();
        displayGame();
    }

@Override
public void printField() {
    int maxCellWidth = 4;

    // Вывод заголовков столбцов
    System.out.print("   "); // отступ для строк с номерами
    for (int i = 0; i < field.getWidth(); i++) {
        System.out.printf("%-" + maxCellWidth + "d", i);
    }
    System.out.println();

    // Вывод поля
    for (int i = 0; i < field.getHeight(); i++) {
        System.out.printf("%-2d ", i); // номера строк
        for (int j = 0; j < field.getWidth(); j++) {
            String cell;
            if(field.isCellOpen(i, j) && field.getCell(i,j).getSym() == '0'){
                cell = " ";
            }else if (field.isCellOpen(i, j)) {
                cell = String.valueOf(field.getCell(i, j).getSym());
            } else if (field.isCellFlag(i, j)) {
                cell = "F";
            } else {
                cell = "*";
            }
            System.out.printf("%-" + maxCellWidth + "s", cell);
        }
        System.out.println();
    }
}
    @Override
    public void printStartMessage(){
        System.out.println("""
                Minesweeper by trisha:
                Выберите пожалуйста команду:
                1 - Новая игра
                2 - Таблица рекордов
                0 - Выйти
                """);
    }
    @Override
    public void printEndMessage(){

    }
    @Override
    public void printLostMessage(){
        printField();
        System.out.println("""
                Вы проиграли :(
                Выберите дальнейшее действие:
                1 - Новая игра
                0 - Выйти
                """);
    }
    private void printWonMessage(){
        System.out.println("""
                Вы выйграли!
                Выберите дальнейшее действие:
                1 - Новая игра
                2 - Сохранить результат
                0 - Выйти
                """);
    }

    public void showMessage(String str){
        System.out.print(str);
    }

}
