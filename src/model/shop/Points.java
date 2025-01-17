package model.shop;

public class Points {

    private static Points instance;

    private int amount;
    private double multiplier = 1;

    private Points() {
    }

    public static Points getInstance() {
        if (instance == null) instance = new Points();
        return instance;
    }

    public synchronized int getAmount() {
        return this.amount;
    }

    public synchronized void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }

    public synchronized void increasePoints(int addend) {
        this.amount += (addend * multiplier);
    }

    public synchronized void decreasePoints(int subtrahend) {
        if ((amount - subtrahend) >= 0) this.amount -= subtrahend;
    }

    public synchronized void reset() {
        this.amount = 100;
    }
}
