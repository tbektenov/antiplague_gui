package model.country;

import model.Virus;
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
    private CountryPoint countryPoint;
    private Set<TransportType> supportedTransportTypes = new HashSet<TransportType>();

    private static double tempX = 0;
    private static double tempY = 0;

    public Country(String name,
                   Color color,
                   int population) {
        this.name = name;
        this.population = population;
        this.virus = new Virus(name);
        this.countryPoint = new CountryPoint(tempX += 0.07, tempY += 0.07);

        if (countryExtent.containsKey(color)) {
            throw new IllegalArgumentException("Country associated with this color already exists");
        }

        countryExtent.put(color, this);
    }

    public Country(String name,
                   Color color,
                   int population,
                   CountryPoint countryPoint) {
        this.name = name;
        this.population = population;
        this.virus = new Virus(name);
        this.countryPoint = countryPoint;

        if (countryExtent.containsKey(color)) {
            throw new IllegalArgumentException("Country associated with this color already exists");
        }

        countryExtent.put(color, this);
    }

    public Country(String name,
                   Color color,
                   int population,
                   Set<TransportType> supportedTransportTypes) {
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

    public CountryPoint getCountryPoint() {
        return countryPoint;
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
