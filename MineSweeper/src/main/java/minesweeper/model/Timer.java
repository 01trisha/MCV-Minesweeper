package minesweeper.model;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

public class Timer {
    private final AtomicInteger seconds;
    private final Runnable tick;
    private ScheduledExecutorService executor;

    public Timer(AtomicInteger seconds, Runnable tick) {
        this.seconds = new AtomicInteger(0);
        this.tick = tick;
    }

    public void start(){

    }

    public void stop(){

    }

    public void reset(){
        seconds.set(0);
    }

    public int getSeconds() {
        return seconds.get();
    }

    public String getFormatedTime(){

        return "";
    }
}
