package minesweeper.model;

import minesweeper.observ.Context;
import minesweeper.observ.Observable;
import minesweeper.observ.Observer;

import java.util.ArrayList;
import java.util.List;

public class MinesweeperModel implements Observable {
    private final List<Observer> observers = new ArrayList<>();
    private final int[][] field;
    private boolean gameOver;
    private final int bomb;

    public MinesweeperModel(){
        field = new int[9][9];
        revealed = new boolean[9][9];
        bomb = 10;
    }
    public MinesweeperModel(int rows, int colm){
        field = new int[rows][colm];
        revealed = new boolean[rows][colm];
        bomb = 10;
    }

    public MinesweeperModel(int rows, int colm, int bomb_count){
        field = new int[rows][colm];
        bomb = bomb_count;
    }

    public void openCell(int x, int y){
        revealed[x][y] = true;
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
        Context context = new Context(field, revealed, flag, gameOver);
        for(Observer o : observers){
            o.update(context);
        }
    }

    public int getBomb() {
        return bomb;
    }

}
