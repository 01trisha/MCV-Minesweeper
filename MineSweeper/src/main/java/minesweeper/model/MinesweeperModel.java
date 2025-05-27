package minesweeper.model;

import minesweeper.observ.Context;
import minesweeper.observ.Observable;
import minesweeper.observ.Observer;
import minesweeper.record.Record;
import minesweeper.record.RecordManager;
import minesweeper.record.RecordStorage;

import java.util.ArrayList;
import java.util.List;

public class MinesweeperModel implements Observable {
    private final List<Observer> observers = new ArrayList<>();
    private Field field;
    private GameState gameState;
    private final Timer timer;
    private int count_bomb;
    private int opened_cells;
    private GameDifficult difficult;
    private final RecordManager recordManager;


    public MinesweeperModel(){
        this.timer = new Timer(this::notifyTimeUpdater);
        this.gameState = GameState.CONFIGURING;
        this.recordManager = RecordStorage.load();
    }

    public void newGame(GameDifficult dif) {
        this.difficult = dif;
        int height = difficult.getHeight();
        int width = difficult.getWidth();
        int bomb = difficult.getBomb();

        if (height <= 0 || width <= 0) {
            throw new IllegalArgumentException("Размер поля должен быть положительным");
        }

        if (bomb <= 0 || bomb >= height * width) {
            throw new IllegalArgumentException("Количество мин должно быть от 1 до " + (height * width - 1));
        }
        this.gameState = GameState.PLAYING;
        this.field = new Field(height, width);
        this.count_bomb = bomb;
        timer.reset();
        timer.stop();
        this.opened_cells = 0;

        notifyObservers();
    }

    public void newGame(int[] param){
        GameDifficult.CUSTOM.setCustomParameters(param[0], param[1], param[2]);
        this.difficult = GameDifficult.CUSTOM;
        int height = difficult.getHeight();
        int width = difficult.getWidth();
        int bomb = difficult.getBomb();

        if (height <= 0 || width <= 0) {
            throw new IllegalArgumentException("Размер поля должен быть положительным");
        }

        if (bomb <= 0 || bomb >= height * width) {
            throw new IllegalArgumentException("Количество мин должно быть от 1 до " + (height * width - 1));
        }
        this.gameState = GameState.PLAYING;
        this.field = new Field(height, width);
        this.count_bomb = bomb;
        timer.reset();
        timer.stop();
        this.opened_cells = 0;

        notifyObservers();
    }

    public void openCell(int x, int y){
        if (field.isCellMine(x, y) && !field.isCellFlag(x, y)){
            field.openAllCells();
            gameState = GameState.LOST;
            timer.stop();
        }else {
            if (field.isCellOpen(x, y) || field.isCellFlag(x, y)) {
                return;
            }
            field.openCell(x, y);
            opened_cells++;

            if (opened_cells == 1) {
//                gameState = GameState.PLAYING;
                timer.start();
                field.setMines(x, y, count_bomb);
                field.updateCharCells();
            }

            field.openFreeCells(x, y);
            isWON();
        }
        notifyObservers();
    }

    public void toggleFlag(int x, int y){
        field.toggleFlag(x, y);
        notifyObservers();
    }

    public void setGameState(GameState newState){
        if (gameState != newState){
            gameState = newState;
        }
        if (gameState != GameState.PLAYING){
            timer.stop();
        }
        notifyObservers();
    }

    public GameState getGameState(){
        return gameState;
    }

    public void endGame(){
        gameState = GameState.EXIT;
        timer.stop();
        System.exit(0);
    }

    public void isWON(){
        int count = 0;
        for (int x = 0; x < field.getHeight(); x++){
            for (int y = 0; y < field.getWidth(); y++){
                if (field.getCell(x, y).isOpen()){
                    count++;
                }
            }
        }

        if (count == field.getHeight() * field.getWidth() - count_bomb){
            field.openAllCells();
            gameState = GameState.WON;
            timer.stop();
        }
    }

    public RecordManager getRecordManager() {
        return recordManager;
    }

    public void saveResult(String name){
        recordManager.addRecord(difficult, new Record(name, timer.getSeconds()));
        RecordStorage.save(recordManager);
    }

    public void clearResults(){
        recordManager.clearAllRecords();
        RecordStorage.save(recordManager);
    }
    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(0);
    }

    private void notifyTimeUpdater(){
        Context context = new Context(field, gameState, timer.getSeconds(), true, recordManager, difficult);
        for(Observer o : observers){
            o.update(context);
        }
    }

    @Override
    public void notifyObservers() {
        Context context = new Context(field, gameState, timer.getSeconds(), false, recordManager, difficult);
        for(Observer o : observers){
            o.update(context);
        }
    }


}
