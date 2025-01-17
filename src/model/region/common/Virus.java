package model.region.common;

import model.difficulty.Difficulty;
import model.region.regions.Region;

import javax.swing.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

public class Virus
        implements Runnable {

    private boolean running = true;

    private float infectionLevel;
    private final Consumer<Float> infectionCallback;
    private final double spreadRateMultiplier;
    private final Region region;

    public Virus(Consumer<Float> infectionCallback, Difficulty difficulty, Region region) {
        this.infectionLevel = ThreadLocalRandom.current().nextFloat(.1f);
        this.infectionCallback = infectionCallback;
        this.spreadRateMultiplier = difficulty.getInfectionMultiplier();
        this.region = region;
    }

    public synchronized void increaseInfection(long addend) {
        region.increaseInfectedPopulation(addend);
    }

    public synchronized void increaseInfection() {
        float addend = (float) (ThreadLocalRandom.current().nextFloat(.3f) * spreadRateMultiplier);

        if ((infectionLevel + addend) <= 1f) infectionLevel += addend;
        else infectionLevel = 1f;

        infectionCallback.accept(infectionLevel);

        long newlyInfected = calculateInfectedIncrease();
        region.increaseInfectedPopulation(newlyInfected);
    }

    public synchronized void killPopulation() {
        long infectedPopulation = region.getInfectedPopulation();
        int bound = (int) (infectedPopulation * 0.01);

        if (bound > 0) {
            int deaths = ThreadLocalRandom.current().nextInt(0, bound);
            region.decreaseInfectedPopulation(deaths);
            region.decreasePopulation(deaths);
        }
    }

    private long calculateInfectedIncrease() {
        long alreadyInfectedPopulation = region.getInfectedPopulation();

        if (alreadyInfectedPopulation <= 0) return 1;

        long newInfections = (long) Math.ceil((alreadyInfectedPopulation * infectionLevel * 2));
        return newInfections;
    }

    public synchronized void decreaseInfection(float subtrahend) {
        if ((infectionLevel - subtrahend) >= .0f) infectionLevel -= subtrahend;
        else infectionLevel = .0f;
    }

    @Override
    public void run() {
        while (running) {
            increaseInfection();
            killPopulation();

            try {
                Thread.sleep((long) (infectionLevel + ThreadLocalRandom.current().nextInt(2000, 5000)));
            } catch (InterruptedException e) {
                JOptionPane.showMessageDialog(
                        null,
                        "Thread was interrupted: " + e,
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
