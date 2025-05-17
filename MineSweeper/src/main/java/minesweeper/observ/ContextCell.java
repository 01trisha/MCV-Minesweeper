package minesweeper.observ;

import minesweeper.model.Cell;

public class ContextCell {
    private ContextCellState state;
    private Character sym;

    public ContextCell(Cell cell){
        this.sym = cell.getSym();
        if(cell.isOpen()){
            this.state = ContextCellState.OPEN;
        }else if(cell.isFlag()){
            this.state = ContextCellState.FLAG;
        }else{
            this.state = ContextCellState.CLOSE;
        }
    }

    public ContextCellState getState(){
        return state;
    }

    public Character getSym(){
        return sym;
    }
}
