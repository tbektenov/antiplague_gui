package model.country;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

public class Virus
    implements Runnable{

    private float infectionLevel;
    private boolean running = true;
    private final Consumer<Float> infectionCallback;

    public Virus(Consumer<Float> infectionCallback) {
        this.infectionLevel = ThreadLocalRandom.current().nextFloat(0, 10);
        this.infectionCallback = infectionCallback;
    }

    public synchronized float getInfectionLevel() {
        return infectionLevel;
    }

    public synchronized void increaseInfection() {
        float increase = ThreadLocalRandom.current().nextFloat(1, 3);
        infectionLevel = Math.min(infectionLevel + increase, 100);

        infectionCallback.accept(infectionLevel);
    }

    public synchronized void decreaseInfection(float subtrahend) {
        infectionLevel = Math.max(infectionLevel - subtrahend, 0);
    }

    @Override
    public void run() {
        while (running) {
            increaseInfection();

            try {
                Thread.sleep((long) (infectionLevel + ThreadLocalRandom.current().nextInt(2000, 10_000)));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public void stop() {
        running = false;
    }
}
