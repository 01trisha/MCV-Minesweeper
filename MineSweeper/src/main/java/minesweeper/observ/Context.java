package minesweeper.observ;

public class Context {
    private final int[][] field;
    private final boolean[][] revealed;
    private final boolean[][] flag;
    private final boolean gameOver;


    public Context(int[][] field, boolean[][] revealed, boolean[][] flag, boolean gameOver) {
        this.field = field;
        this.revealed = revealed;
        this.flag = flag;
        this.gameOver = gameOver;
    }

    public int[][] getField() {
        return field;
    }
    public boolean[][] getRevealed(){
        return revealed;
    }
    public boolean isGameOver(){
        return gameOver;
    }
    public boolean[][] getFlag(){
        return flag;
    }

}
