package model;

import java.util.concurrent.ThreadLocalRandom;

public class Virus
    implements Runnable{

    private float infectionLevel;
    private final String countryName;
    private boolean running = true;

    public Virus(String countryName) {
        this.countryName = countryName;
        this.infectionLevel = ThreadLocalRandom.current().nextFloat(0, 10);
    }

    public synchronized float getInfectionLevel() {
        return infectionLevel;
    }

    public synchronized void increaseInfection() {
        float increase = ThreadLocalRandom.current().nextFloat(1, 3);
        infectionLevel = Math.min(infectionLevel + increase, 100);
    }

    public synchronized void decreaseInfection(float subtrahend) {
        infectionLevel = Math.max(infectionLevel - subtrahend, 0);
    }

    @Override
    public void run() {
        while (running) {
            increaseInfection();
            System.out.printf("%s infection level: %.2f%%\n", countryName, infectionLevel);

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
