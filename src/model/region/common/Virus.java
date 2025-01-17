package model.region.common;

import model.difficulty.Difficulty;
import model.region.regions.Region;

import javax.swing.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

public class Virus
        implements Runnable {

    private boolean running = true;

    private float infectionSpeed;
    private final Consumer<Float> infectionCallback;
    private final double spreadRateMultiplier;
    private final Region region;

    public Virus(Consumer<Float> infectionCallback, Difficulty difficulty, Region region) {
        this.infectionSpeed = ThreadLocalRandom.current().nextFloat(.1f);
        this.infectionCallback = infectionCallback;
        this.spreadRateMultiplier = difficulty.getInfectionMultiplier();
        this.region = region;
    }

    public synchronized void increaseInfection(long addend) {
        region.increaseInfectedPopulation(addend);
    }

    public synchronized void increaseInfection() {
        float addend = (float) (ThreadLocalRandom.current().nextFloat(.3f) * spreadRateMultiplier);

        if ((infectionSpeed + addend) <= 1f) infectionSpeed += addend;
        else infectionSpeed = 1f;

        infectionCallback.accept(infectionSpeed);

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

        long newInfections = (long) Math.ceil((alreadyInfectedPopulation * infectionSpeed * 2));
        return newInfections;
    }

    public synchronized void decreaseInfection(float subtrahend) {
        if ((infectionSpeed - subtrahend) >= .0f) infectionSpeed -= subtrahend;
        else infectionSpeed = .0f;
    }

    @Override
    public void run() {
        while (running) {
            increaseInfection();
            killPopulation();

            try {
                Thread.sleep((ThreadLocalRandom.current().nextInt(2000, 5000)));
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

    public synchronized float getInfectionSpeed() {
        return infectionSpeed;
    }

    public synchronized void setInfectionSpeed(float infectionSpeed) {
        this.infectionSpeed = infectionSpeed;
    }
}
