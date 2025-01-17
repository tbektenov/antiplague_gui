package model.region.common;

import model.region.regions.Region;
import model.shop.Points;

import javax.swing.*;
import java.util.concurrent.ThreadLocalRandom;

public class Cure
        implements Runnable {

    private final Region region;
    private float cureEfficiency;

    private boolean running = true;

    public Cure(Region region) {
        this.cureEfficiency = 0f;
        this.region = region;
    }

    public void startDevelopingCure() {
        this.cureEfficiency = generateCureEfficiency();
    }

    public synchronized float getCureEfficiency() {
        return this.cureEfficiency;
    }

    public synchronized void setCureEfficiency(float cureEfficiency) {
        this.cureEfficiency = cureEfficiency;
    }

    public synchronized void increaseCureEfficiency(float addend) {
        this.cureEfficiency = Math.min(this.cureEfficiency + addend, 1f);
    }

    private float generateCureEfficiency() {
        return ThreadLocalRandom.current().nextFloat(.03f, .05f);
    }

    public synchronized void curePopulation() {
        long infectedPopulation = region.getInfectedPopulation();
        if (infectedPopulation > 0) {
            int chanceOfCure = ThreadLocalRandom.current().nextInt(2);
            int populationCured = (int) Math.ceil(infectedPopulation * cureEfficiency * chanceOfCure);

            region.increaseCuredPopulation(populationCured);
            region.decreaseInfectedPopulation(populationCured);

            if (cureEfficiency > 0f)
                Points.getInstance().increasePoints(
                    ThreadLocalRandom.current().nextInt(51)
                );
        }
    }

    @Override
    public void run() {
        while (running) {
            curePopulation();

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                JOptionPane.showMessageDialog(
                        null,
                        "Thread was interrupted: " + e,
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    public void stop() {
        this.running = false;
    }
}
