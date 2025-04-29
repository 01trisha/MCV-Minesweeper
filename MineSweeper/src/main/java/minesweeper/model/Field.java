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

            if (cells[randX][randY].isMine()){
                continue;
            }

            if (height > 3 && width > 3 && height * width - mines > 9){
                if (Math.abs(randX - x) <= 1 && Math.abs(randY - y) <= 1) {
                    continue;
                }
            } else {
                if (randX == x && randY == y){
                    continue;
                }
            }

            cells[randX][randY].setSym('M');
            placed_mines++;
        }
    }


    public void updateCharCells(){
        for (int x = 0; x < height; x++){
            for(int y = 0; y < width; y++){
                if (!cells[x][y].isMine()){
                    char neighbor_count = calcOfNeighborCells(x, y);
                    cells[x][y].setSym(neighbor_count);
                }
            }
        }
    }

    public char calcOfNeighborCells(int x, int y){
        char count = '0';
        for(int i = x - 1; i <= x + 1; i++){
            for (int j = y - 1; j <= y+1; j++){
                if (isValidPosition(i, j) && !(i == x && j == y)){
                    if (isCellMine(i, j)){
                        count++;
                    }
                }
            }
        }

        return count;
    }

    public boolean isValidPosition(int x, int y){
        if (x >= 0 && x < height && y >= 0 && y < height){
            return true;
        }
        return false;
    }

    public void openFreeCells(int x, int y) {
        if (!isValidPosition(x, y) || isCellFlag(x, y) || isCellMine(x, y)) {
            return;
        }

        openCell(x, y);

        if (cells[x][y].getSym() == '0') {
            for (int i = x - 1; i <= x + 1; i++) {
                for (int j = y - 1; j <= y + 1; j++) {

                    if ((i == x && j == y) || !isValidPosition(i, j)){
                        continue;
                    }

                    if (!isCellOpen(i, j) && !isCellFlag(i, j) && !isCellMine(i, j)) {
                        openCell(i, j);

                        if (cells[i][j].getSym() == '0') {
                            openFreeCells(i, j);
                        }
                    }
                }
            }
        }
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
