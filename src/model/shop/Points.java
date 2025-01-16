package model.shop;

public class Points {

    private static Points instance;

    private int amount;
    private boolean running = true;

    private Points() {
    }

    public static Points getInstance() {
        if (instance == null) instance = new Points();
        return instance;
    }

    public synchronized int getAmount() {
        return this.amount;
    }

    public synchronized void increasePointsByCured(int curedCount) {
        this.amount += curedCount;
    }

    public synchronized void decreasePoints(int subtrahend) {
        if ((amount - subtrahend) >= 0) this.amount -= subtrahend;
    }

    public synchronized void reset() {
        this.amount = 100;
        this.running = true;
    }

    public void stop() {
        running = false;
    }
}
