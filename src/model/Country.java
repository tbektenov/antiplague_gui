package model;

import model.transport.TransportType;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Country {
    private static Map<Color, Country> countryExtent = new HashMap<Color, Country>();

    private final String name;
    private int population;
    private final Virus virus;
    private Set<TransportType> supportedTransportTypes = new HashSet<TransportType>();

    public Country(String name, Color color, int population) {
        this.name = name;
        this.population = population;
        this.virus = new Virus(name);

        if (countryExtent.containsKey(color)) {
            throw new IllegalArgumentException("Country associated with this color already exists");
        }

        countryExtent.put(color, this);
    }

    public Country(String name, Color color, int population, Set<TransportType> supportedTransportTypes) {
        this.name = name;
        this.population = population;
        this.virus = new Virus(name);
        this.supportedTransportTypes = supportedTransportTypes;

        if (countryExtent.containsKey(color)) {
            throw new IllegalArgumentException("Country associated with this color already exists");
        }

        countryExtent.put(color, this);
    }

    public static Map<Color, Country> getCountryExtent() {
        return countryExtent;
    }

    public int getGlobalPopulation() {
        int globalPopulation = countryExtent.values().stream().mapToInt(Country::getPopulation).sum();
        return globalPopulation;
    }

    public static boolean containsColor(Color color) {
        return countryExtent.containsKey(color);
    }

    public static Country getCountryByColor(Color color) {
        return countryExtent.get(color);
    }

    public void addSupportedTransportType(TransportType type) {
        this.supportedTransportTypes.add(type);
    }

    public void removeSupportedTransportType(TransportType type) {
        this.supportedTransportTypes.remove(type);
    }

    public void decreasePopulation(int subtrahend) {
        if (subtrahend > 0) {
            this.population -= subtrahend;
        }
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
