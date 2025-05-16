package minesweeper.view.text;

import minesweeper.CommandParser.CommandParser;
import minesweeper.controller.MinesweeperController;
import minesweeper.observ.Context;
import minesweeper.record.Record;
import minesweeper.record.RecordManager;
import minesweeper.view.MinesweeperView;

import java.util.Scanner;

public class ConsoleView implements MinesweeperView {
    private String[][] field;
    private String gameState;
    private int time;
    private MinesweeperController controller;
    private final Scanner scanner = new Scanner(System.in);
    private final CommandParser parser = new CommandParser();
    private String difficult;
    private RecordManager recordManager;

    @Override
    public void setController(MinesweeperController controller) {
        this.controller = controller;
        this.recordManager = controller.getRecordManager();
    }

    @Override
    public void start() {
        printStartMessage();

        while (true) {
            String input = scanner.nextLine();

            switch (input.trim()) {
                case "1":
                    printDifficult();
                    break;
                case "2":
                    printResult();
                    break;
                case "0":
                    controller.selectExitCommand();
                    return;
                default:
                    System.out.println("Неизвестная команда");
            }
        }
    }

    public void printStartMessage() {
        System.out.println("""
                Minesweeper by trisha:
                Выберите пожалуйста команду:
                1 - Новая игра
                2 - Таблица рекордов
                0 - Выйти
                """);
    }

    public void printDifficult() {
        System.out.println("""
                Пожалуйста, выберите сложность:
                1 - EASY(9x9, 10 mines)
                2 - MEDIUM(9x9, 30 mines)
                3 - HARD(9x9, 50 mines)
                4 - CUSTOM(your parameters)
                """);

        selectDifficult();
    }

    public void selectDifficult(){
        String choice = scanner.nextLine();

        switch (choice){
            case "1":
            case "2":
            case "3":
                controller.startGame(choice);
                break;
            case "4":
                while (true) {
                    try {
                        int[] param = getParameters();
                        controller.startGame(param);
                        break;
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            default:
                System.out.println("Нет такой сложности, выберите еще раз:");
                selectDifficult();
        }
    }

    @Override
    public void update(Context context) {
        if (!context.getOnlyTimeUpdate()){
            field = context.getField();
            gameState = context.getGameState();
            time = context.getTime();
            difficult = context.getDifficult();
            displayGame();
        }
    }

    public void displayGame() {
        switch (gameState) {
            case "PLAYING":
                printPlayingMessage();
                break;
            case "LOST":
                printLostMessage();
                break;
            case "WON":
                printWonMessage();
                break;
            case "EXIT":
                controller.selectExitCommand();
                return;
        }

        getNextCommand(gameState);
    }

    public void printPlayingMessage() {
        int maxCellWidth = 4;

        // Вывод заголовков столбцов
        System.out.print("   "); // отступ для строк с номерами
        for (int i = 0; i < field[0].length; i++) {
            System.out.printf("%-" + maxCellWidth + "d", i);
        }
        System.out.println();

        // Вывод поля
        for (int i = 0; i < field.length; i++) {
            System.out.printf("%-2d ", i); // номера строк
            for (int j = 0; j < field[i].length; j++) {
                String cell;
                if(field[i][j].equals("0")){
                    cell = " ";
                }else{
                    cell = field[i][j];
                }
                System.out.printf("%-" + maxCellWidth + "s", cell);
            }
            System.out.println();

        }
        System.out.println("""
                Введите действие:
                open x y - открыть клетку x y
                flag x y - поставить флаг на клетку x y
                exit - выйти из игры
                """);

    }

    public void printLostMessage() {
        printOpenedField();
        printTime();
        System.out.println("""
                Вы проиграли :(
                Выберите команду для продолжения:
                1 - Новая игра
                0 - Выйти
                """);

    }

    public void printWonMessage(){
        printOpenedField();
        printTime();
        System.out.println("""
                Вы выиграли!
                Выберите команду для продолжения:
                1 - Новая игра
                2 - Сохранить результат
                0 - Выйти
                """);

    }

    public void printTime(){
        System.out.printf("Ваше время игры: %d sec\n", time);
    }

    public void printOpenedField(){
        int maxCellWidth = 4;

        // Вывод заголовков столбцов
        System.out.print("   "); // отступ для строк с номерами
        for (int i = 0; i < field[0].length; i++) {
            System.out.printf("%-" + maxCellWidth + "d", i);
        }
        System.out.println();

        // Вывод поля
        for (int i = 0; i < field.length; i++) {
            System.out.printf("%-2d ", i); // номера строк
            for (int j = 0; j < field[i].length; j++) {
                String cell;
                cell = field[i][j];
                System.out.printf("%-" + maxCellWidth + "s", cell);
            }
            System.out.println();
        }
    }

    public void getNextCommand(String gameState) {
        switch (gameState) {
            case "PLAYING":
                handlePlayingState();
                break;
            case "WON":
                handleWonState();
                break;
            case "LOST":
                handleLostState();
                break;
            case "EXIT":
                controller.selectExitCommand();
                break;
        }
    }

    private void handlePlayingState(){
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                String[] command = parser.parse(input);

                if (command[0].equals("open") && command.length == 3) {
                    controller.selectOpenCellCommand(Integer.parseInt(command[1]), Integer.parseInt(command[2]));
                    return;
                } else if (command[0].equals("exit")) {
                    controller.selectExitCommand();
                    return;
                } else if (command[0].equals("flag") && command.length == 3) {
                    controller.selectToggleFlagCommand(Integer.parseInt(command[1]), Integer.parseInt(command[2]));
                    return;
                } else if (command[0].isEmpty()) {
                    continue;
                } else {
                    System.out.println("Неизвестная команда. Введите еще раз");
                }
            }catch (NumberFormatException e){
                System.out.println("Координаты должны быть числами. Введите еще раз");
            }catch (Exception e){
                System.out.println("Ошибки: " + e.getMessage());
            }
        }
    }

    private void handleWonState() {
        while (true) {
            String command = scanner.nextLine().trim();

            switch (command) {
                case "1":
                    printDifficult();
                    break;
                case "2":
                    System.out.print("Введите ваше имя: ");
                    String name = scanner.nextLine();
                    controller.selectSaveRecords(name);
                    if (difficult != null){
                        handleResult(difficult);
                    }else {
                        printResult();
                    }
                    break;
                case "0":
                    controller.selectExitCommand();
                    return;
                default:
                    System.out.println("Неизвестная команда, выберите еще раз)");
            }
        }


    }

    private void handleLostState() {
        while (true){
            String command = scanner.nextLine().trim();

            switch (command){
                case "1":
                    printDifficult();
                    return;
                case "0":
                    controller.selectExitCommand();
                    return;
                default:
                    System.out.println("Неизвестная команда, выберите еще раз");
            }
        }
    }


    private void printResult(){
        System.out.println("""
                Выберите для какой сложности открыть таблицу рекордов:
                1 - EASY
                2 - MEDIUM
                3 - HARD
                4 - CUSTOM
                """);
        while(true){
            String command = scanner.nextLine();
            switch (command){
                case "1":
                    handleResult("EASY");
                    break;
                case "2":
                    handleResult("MEDIUM");
                    break;
                case "3":
                    handleResult("HARD");
                    break;
                case "4":
                    handleResult("CUSTOM");
                    break;
                default:
                    System.out.println("Неизвестная сложность, введите еще раз:");
            }
        }


    }

    private void handleResult(String difficult){
        System.out.println("Таблица рекордов для выбранной сложности:");
        for(Record i : recordManager.getRecords(difficult)){
            System.out.println(i.getName() + ": " + i.getTime() + " sec.");
        }
        System.out.println();

        System.out.println("""
                Выберите следующее действие:
                1 - Очистить таблицу рекордов
                2 - Новая игра
                0 - Выйти
                """);

        while (true){
            String command = scanner.nextLine();

            switch (command){
                case "1":
                    controller.selectClearAllRecords();
                    handleResult(difficult);
                case "2":
                    printDifficult();
                    break;
                case "0":
                    controller.selectExitCommand();
                    return;
                default:
                    System.out.println("Неизвестная команда, попробуйте еще раз");
            }
        }
    }



    public int[] getParameters(){
        try {
            int[] parameters = new int[3];
            System.out.println("Введите высоту поля: ");
            parameters[0] = Integer.parseInt(scanner.nextLine());
            System.out.println("Введите ширину поля: ");
            parameters[1] = Integer.parseInt(scanner.nextLine());
            System.out.println("Введите количество бомб: ");
            parameters[2] = Integer.parseInt(scanner.nextLine());
            for(int i : parameters){
                if (i < 1){
                    throw new IllegalArgumentException();
                }
            }
            return parameters;
        }catch (Exception e){
            throw new IllegalArgumentException("Параметры должны числами быть больше 0. Введите еще раз");
        }
    }

}
