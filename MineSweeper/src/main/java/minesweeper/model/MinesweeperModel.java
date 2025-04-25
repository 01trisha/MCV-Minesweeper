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


    public MinesweeperModel(){
        this.gameState = GameState.CONFIGURING;
    }

    public void newGame(int height, int width, int bomb){
        this.gameState = GameState.PLAYING;
        this.field = new Field(height, bomb);
        this.count_bomb = bomb;

        notifyObservers();
    }

    public void openCell(int x, int y){

        if (field.getCell(x, y).isMine()){
            setGameState(GameState.LOST);
            notifyObservers();
        }

        field.openCell(x, y);
        notifyObservers();
    }


    public void setGameState(GameState newState){
        if (gameState != newState){
            gameState = newState;
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
