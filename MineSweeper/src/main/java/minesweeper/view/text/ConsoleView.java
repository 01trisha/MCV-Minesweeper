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

//    @Override
//    public void printField(){
//        for(int i = 0; i < field.getWidth(); i++){
//            System.out.print("  " + i);
//        }
//        System.out.println();
//        for(int i = 0; i < field.getHeight(); i++){
//            System.out.print(i + " ");
//            for(int j = 0; j < field.getWidth(); j++){
////                System.out.print(j);
//                if (field.isCellOpen(i, j)){
//                    System.out.print(field.getCell(i, j).getSym() + "   ");
//                }else if(field.isCellFlag(i, j)){
//                    System.out.print("F   ");
//                }else{
//                    System.out.print("*   ");
//                }
////                System.out.print("* ");
//
//            }
//            System.out.println();
//        }
//    }

@Override
public void printField() {
    // Определяем максимальную ширину ячейки
    int maxCellWidth = 4; // минимальная ширина (для "*", "F" и однозначных чисел)



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
            if (field.isCellOpen(i, j)) {
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
