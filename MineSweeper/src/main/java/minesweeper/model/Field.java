package minesweeper.model;

import java.util.Random;

public class Field {
    private final int height;
    private final int width;
    private final Cell[][] cells;

    public Field(int height, int width) {
        this.height = height;
        this.width = width;
        this.cells = new Cell[height][width];
        creatingCells();
    }

    private void creatingCells(){
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                cells[i][j] = new Cell();
            }
        }
    }

    public int getHeight(){
        return height;
    }

    public int getWidth(){
        return width;
    }

    public Cell getCell(int x, int y){
        if (0 <= x && x < height && 0 <= y && y < width){
            return cells[x][y];
        }
        else{
            throw new IllegalArgumentException("Недопустимые координаты x y");
        }
    }

     public void openCell(int x, int y){
        //сделать переменную для хранения открытых клеток и если == клеткам - мины то выйграл
        getCell(x, y).setOpen(true);
    }

    public void toggleFlag(int x, int y){
        Cell cell = getCell(x, y);
        cell.setFlag(!cell.isFlag());
    }

    public void setMines(int x, int y, int mines){
        Random random = new Random();
        int placed_mines = 0;

        while (placed_mines < mines){
            int randX = random.nextInt(height);
            int randY = random.nextInt(width);

            if ((randX == x && randY == y) || cells[randX][randY].isMine()){
                continue;
            }

            cells[randX][randY].setSym('M');
            placed_mines++;
        }
    }

    public void openFreeCells(int x, int y){

    }

    public boolean isCellOpen(int x, int y){
        return getCell(x, y).isOpen();
    }

    public boolean isCellFlag(int x, int y){
        return getCell(x, y).isFlag();
    }

    public boolean isCellMine(int x, int y){
        if (getCell(x, y).isMine()){
            return true;
        }
        return false;
    }


}
