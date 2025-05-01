package minesweeper.model;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Timer {
    private final AtomicInteger seconds;
    private final Runnable tick;
    private ScheduledExecutorService executor;
    private volatile boolean isRunning;

    public Timer(Runnable tick) {
        this.seconds = new AtomicInteger(0);
        this.tick = tick;
    }


    public void start(){
        if (executor != null && !executor.isShutdown()){
            return;
        }

        isRunning = true;
        seconds.set(0);
        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {seconds.incrementAndGet(); tick.run();}, 1, 1, TimeUnit.SECONDS);

    }

    public void stop(){
        isRunning = false;
        if (executor != null){
            executor.shutdownNow();
        }
    }

    public void reset(){
        seconds.set(0);
    }

    public int getSeconds() {
        return seconds.get();
    }
}
