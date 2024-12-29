package model;

public abstract class Country {
    private String name;
    private int population;
    private Virus virus;

    protected Country(String name, int population) {
        this.name = name;
        this.population = population;
        this.virus = new Virus(name);
    }

    public String getName() {
        return name;
    }

    public int getPopulation() {
        return population;
    }

    public float getInfectionLevel() {
        return virus.getInfectionLevel();
    }

    public void startInfection() {
        Thread infectionThread = new Thread(virus);
        infectionThread.start();
    }

    public void stopInfection() {
        virus.stop();
    }
}
