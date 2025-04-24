package minesweeper.model;

public class Cell {
    private char sym;
    private boolean is_flag;
    private boolean is_open;

    public Cell() {
        this.sym = '0';
        this.is_flag = false;
        this.is_open = false;
    }

    public char getSym(){
        return sym;
    }
    public void setSym(char a){
        this.sym = a;
    }

    public boolean isFlag(){
        return is_flag;
    }
//    public boolean setFlag(){
//
//    }

    public boolean isOpen(){
        return is_open;
    }
}
