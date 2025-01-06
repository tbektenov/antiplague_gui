package model.shop;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

public class Points {

    private int amount;
    private boolean running = true;
    private final Timer timer;

    public Points() {
        this.amount = 0;
        this.timer = new Timer(true);
    }

    public int getAmount() {
        return this.amount;
    }

    public void increasePoints() {
        this.amount += ThreadLocalRandom.current().nextInt(1, 15);
    }

    public void start() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (running) {
                    increasePoints();
                }
            }
        }, 3000, 1000);
    }

    public void stop() {
        running = false;
    }
}
