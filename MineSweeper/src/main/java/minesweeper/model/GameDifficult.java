package minesweeper.model;

public enum GameDifficult {
    EASY(9, 9, 10),
    MEDIUM(9, 9, 30),
    HARD(9, 9, 59),
    CUSTOM(0, 0, 0){
        @Override
        public void setCustomParameters(int height, int width, int bomb){
            this.height = height;
            this.width = width;
            this.bomb = bomb;
        }
    };

    protected int height;
    protected int width;
    protected int bomb;

    GameDifficult(int height, int width, int bomb) {
        this.height = height;
        this.width = width;
        this.bomb = bomb;
    }

    public void setCustomParameters(int height, int width, int bomb){
        throw new UnsupportedOperationException("Метод только для CUSTOM сложности");
    }


    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getBomb() {
        return bomb;
    }
}
