package minesweeper.model;

public class Field {
    private final int height;
    private final int width;
    private final Cell[][] cells;

    public Field(int height, int width) {
        this.height = height;
        this.width = width;
        this.cells = new Cell[height][width];
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
            throw new IllegalArgumentException("Недопустимые координаты");
        }
    }

    public boolean isCellOpen(int x, int y){
        return getCell(x, y).isOpen();
    }

    public boolean isCellFlag(int x, int y){
        return getCell(x, y).isFlag();
    }

    public void openCell(int x, int y){
        getCell(x, y).setOpen(true);
    }

    public void toggleFlag(int x, int y){
        Cell cell = getCell(x, y);
        cell.setFlag(!cell.isFlag());
    }

    public boolean isCellMine(int x, int y){
        if (getCell(x, y).isMine()){
            return true;
        }
        return false;
    }


}
