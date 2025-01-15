package model.region.common;

import model.region.regions.Region;

import java.util.concurrent.ThreadLocalRandom;

public class Cure
    implements Runnable{

    private final Region region;
    private float cureEfficiency;

    public Cure(Region region) {
        this.cureEfficiency = generateCureEfficiency();
        this.region = region;
    }

    public synchronized float getCureEfficiency() {
        return this.cureEfficiency;
    }

    public synchronized void increaseCureEfficiency(float addend) {
        if ((this.cureEfficiency + addend) <= 100) this.cureEfficiency += addend;
        else this.cureEfficiency = 100;
    }

    private float generateCureEfficiency() {
        return ThreadLocalRandom.current().nextFloat(3f);
    }

    @Override
    public void run() {

    }
}
