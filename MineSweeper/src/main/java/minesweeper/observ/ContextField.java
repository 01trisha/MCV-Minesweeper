package minesweeper.observ;

import minesweeper.model.Field;

public class ContextField {
    private final ContextCell[][] cells;

    public ContextField(Field field) {
        this.cells = new ContextCell[field.getHeight()][field.getHeight()];
        rebase(field);
    }

    public void rebase(Field field){

        for(int i = 0; i < field.getHeight(); i++) {
            for (int j = 0; j < field.getWidth(); j++) {
                cells[i][j] = new ContextCell(field.getCell(i, j));
            }
        }
    }

    public ContextCell getCell(int x, int y){
        return cells[x][y];
    }
}
