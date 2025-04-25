package minesweeper.model;

public class Cell {
    private char sym;
    private boolean isFlag;
    private boolean isOpen;

    public Cell() {
        this.sym = '0';
        this.isFlag = false;
        this.isOpen = false;
    }

    public char getSym(){
        return sym;
    }
    public void setSym(char a){
        this.sym = a;
    }

    public boolean isFlag(){
        return isFlag;
    }
    public void setFlag(boolean flag){
        this.isFlag = flag;
    }

    public boolean isOpen(){
        return isOpen;
    }

    public void setOpen(boolean open){
        this.isOpen = open;
    }

    public boolean isMine(){
        if (sym == 'M'){
            return true;
        }
        return false;
    }
}
