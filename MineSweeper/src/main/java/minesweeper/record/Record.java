package minesweeper.record;

import java.io.Serializable;

public class Record implements Serializable {
    private final String name;
    private final int time;

    public Record(String name, int time) {
        this.name = name;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public int getTime() {
        return time;
    }
}
