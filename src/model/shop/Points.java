package model.shop;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

public class Points {

    private static Points instance;

    private int amount;
    private boolean running = true;
    private Timer timer;

    private Points() {
        this.amount = 0;
        this.timer = new Timer(true);
    }

    public static Points getInstance() {
        if (instance == null) {
            instance = new Points();
        }
        return instance;
    }

    public synchronized int getAmount() {
        return this.amount;
    }

    public synchronized void increasePoints() {
        this.amount += ThreadLocalRandom.current().nextInt(1, 15);
    }

    public synchronized void decreasePoints(int subtrahend) {
        if ((amount - subtrahend) >= 0) {
            this.amount -= subtrahend;
        }
    }

    public synchronized void reset() {
        this.amount = 0;
        this.running = true;
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer(true);
        start();
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
