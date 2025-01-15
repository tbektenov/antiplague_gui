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

    public synchronized void increaseInfection(float addend) {
        if ((infectionLevel + addend) <= 1f) infectionLevel += addend;
        else infectionLevel = 1f;


        infectionCallback.accept(infectionLevel);
    }

    public synchronized void increaseInfection() {
        float addend = (float) (ThreadLocalRandom.current().nextFloat(.03f) * spreadRateMultiplier);

        if ((infectionLevel + addend) <= 1f) infectionLevel += addend;
        else infectionLevel = 1f;

        infectionCallback.accept(infectionLevel);

        int newlyInfected = calculateInfectedIncrease();
        region.increaseInfectedPopulation(newlyInfected);
    }

    public synchronized void killPopulation() {
        int infectedPopulation = region.getInfectedPopulation();
        if (infectedPopulation > 0) {
            int deaths = ThreadLocalRandom.current().nextInt(0, (int) (infectedPopulation * 0.01)); // 1% chance
            region.decreaseInfectedPopulation(deaths);
            region.decreasePopulation(deaths);
        }
    }

    private int calculateInfectedIncrease() {
        int healthyPopulation = region.getMerePopulation() - region.getInfectedPopulation() - region.getCuredPopulation();
        int newInfections = (int) (healthyPopulation * infectionLevel * 0.01);
        return Math.min(newInfections, healthyPopulation);
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
            region.updateInfectedPopulation();

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
