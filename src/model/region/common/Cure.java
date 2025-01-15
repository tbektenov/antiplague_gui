package model.region.common;

import model.region.regions.Region;

import javax.swing.*;
import java.util.concurrent.ThreadLocalRandom;

public class Cure
    implements Runnable{

    private final Region region;
    private float cureEfficiency;

    private boolean running = true;

    public Cure(Region region) {
        this.cureEfficiency = generateCureEfficiency();
        this.region = region;
    }

    public synchronized float getCureEfficiency() {
        return this.cureEfficiency;
    }

    public synchronized void increaseCureEfficiency(float addend) {
        if ((this.cureEfficiency + addend) <= 1f) this.cureEfficiency += addend;
        else this.cureEfficiency = 1f;
    }

    private float generateCureEfficiency() {
        return ThreadLocalRandom.current().nextFloat(.03f);
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                JOptionPane.showMessageDialog(
                        null,
                        "Thread was interrupted: " + e,
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void stop() {
        this.running = false;
    }
}
