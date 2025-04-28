package minesweeper.model;

import minesweeper.controller.MinesweeperController;
import minesweeper.observ.Context;
import minesweeper.observ.Observable;
import minesweeper.observ.Observer;

import java.util.ArrayList;
import java.util.List;

public class MinesweeperModel implements Observable {
    private final List<Observer> observers = new ArrayList<>();
    private Field field;
    private GameState gameState;
    private int count_bomb;
    private int opened_cells;


    public MinesweeperModel(){
        this.gameState = GameState.CONFIGURING;
    }

    public void newGame(int height, int width, int bomb) {
        if (height <= 0 || width <= 0) {
            throw new IllegalArgumentException("Размер поля должен быть положительным");
        }

        if (bomb <= 0 || bomb >= height * width) {
            throw new IllegalArgumentException("Количество мин должно быть от 1 до " + (height * width - 1));
        }

        this.gameState = GameState.PLAYING;
        this.field = new Field(height, width);
        this.count_bomb = bomb;
        this.opened_cells = 0;

        notifyObservers();
    }

    public void openCell(int x, int y){
        if (field.isCellMine(x, y)){
            setGameState(GameState.LOST);
        }else {
            if (field.isCellOpen(x, y)) {
                return;
            }


            field.openCell(x, y);
            opened_cells++;

            if (opened_cells == 1) {
                field.setMines(x, y, count_bomb);
            }

            field.openFreeCells(x, y);
            isWON(opened_cells);
            notifyObservers();
        }
    }

    public void toggleFlag(int x, int y){
        field.toggleFlag(x, y);
        notifyObservers();
    }

    public void setGameState(GameState newState){
        if (gameState != newState){
            gameState = newState;
        }
        notifyObservers();
    }

    public GameState getGameState(){
        return gameState;
    }

    public void endGame(){
        setGameState(GameState.EXIT);
        System.exit(0);
    }

    public void isWON(int count){
        if (count == field.getHeight() * field.getWidth() - count_bomb){
            setGameState(GameState.WON);
        }
    }

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(0);
    }

    @Override
    public void notifyObservers() {
        Context context = new Context(field, gameState);
        for(Observer o : observers){
            o.update(context);
        }
    }


}
