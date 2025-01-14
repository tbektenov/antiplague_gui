package model.region.common;

import model.difficulty.Difficulty;

import javax.swing.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

public class Virus
        implements Runnable {

    private float infectionLevel;
    private boolean running = true;
    private final Consumer<Float> infectionCallback;
    private final double spreadRateMultiplier;

    public Virus(Consumer<Float> infectionCallback, Difficulty difficulty) {
        this.infectionLevel = ThreadLocalRandom.current().nextFloat(0, 10);
        this.infectionCallback = infectionCallback;
        this.spreadRateMultiplier = difficulty.getInfectionMultiplier();
    }

    public synchronized void increaseInfection() {
        float increase = (float) (ThreadLocalRandom.current().nextFloat(1, 3) * spreadRateMultiplier);

        if ((infectionLevel + increase) <= 100) {
            infectionLevel += increase;
        } else {
            infectionLevel = 100;
        }

        infectionCallback.accept(infectionLevel);
    }

    public synchronized void decreaseInfection(float subtrahend) {
        if ((infectionLevel - subtrahend) >= 0) {
            infectionLevel -= subtrahend;
        } else {
            infectionLevel = 0;
        }
    }

    @Override
    public void run() {
        while (running) {
            increaseInfection();

            try {
                Thread.sleep((long) (infectionLevel + ThreadLocalRandom.current().nextInt(2000, 10_000)));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();

                JOptionPane.showMessageDialog(
                        null,
                        "Unexpected error occured: " + e,
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

                break;
            }
        }
    }

    public void stop() {
        running = false;
    }

    public synchronized float getInfectionLevel() {
        return infectionLevel;
    }

    public synchronized void setInfectionLevel(float infectionLevel) {
        this.infectionLevel = infectionLevel;
    }
}
