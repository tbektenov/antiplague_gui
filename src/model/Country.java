package model;

import java.util.concurrent.ThreadLocalRandom;

public abstract class Country
    implements Runnable {
    private String name;
    private int population;
    private float infectionLevel;;
    private boolean running = true;

    protected Country(String name, int population) {
        this.name = name;
        this.population = population;
        this.infectionLevel = ThreadLocalRandom.current().nextFloat(0, 10);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public float getInfectionLevel() {
        return infectionLevel;
    }

    public synchronized void setInfectionLevel(int infectionLevel) {
        this.infectionLevel = infectionLevel;
    }

    @Override
    public void run() {
        while (running) {
            float newLevel = getInfectionLevel() + ThreadLocalRandom.current().nextInt(1, 3);
            setInfectionLevel((int) Math.min(newLevel, 100));

            System.out.println(getName() + " infection level: " + getInfectionLevel() + "%");

            try {
                Thread.sleep((long) (getInfectionLevel() + ThreadLocalRandom.current().nextInt(2000, 10_000)));
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
