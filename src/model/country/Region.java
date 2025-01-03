package model.country;

import model.transport.TransportType;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class Region {
    private static Map<Color, Region> regionExtent = new HashMap<Color, Region>();

    private final String name;
    private int population;
    private final Virus virus;
    private RegionPoint regionPoint;
    private Set<TransportType> supportedTransportTypes = new HashSet<TransportType>();

    public Region(String name,
                  Color color,
                  int population,
                  RegionPoint regionPoint,
                  Consumer<Region> callback) {
        this.name = name;
        this.population = population;
        this.regionPoint = regionPoint;
        this.virus = new Virus(name, infectionLevel -> callback.accept(this));

        if (regionExtent.containsKey(color)) {
            throw new IllegalArgumentException("Region associated with this color already exists");
        }

        regionExtent.put(color, this);
    }

    public Region(String name,
                  Color color,
                  int population,
                  RegionPoint regionPoint,
                  Consumer<Region> callback,
                  Set<TransportType> supportedTransportTypes) {
        this.name = name;
        this.population = population;
        this.regionPoint = regionPoint;
        this.virus = new Virus(name, _ -> callback.accept(this));
        this.supportedTransportTypes = supportedTransportTypes;

        if (regionExtent.containsKey(color)) {
            throw new IllegalArgumentException("Region associated with this color already exists");
        }

        regionExtent.put(color, this);
    }

    public static Map<Color, Region> getRegionExtent() {
        return regionExtent;
    }

    public int getGlobalPopulation() {
        int globalPopulation = regionExtent.values().stream().mapToInt(Region::getPopulation).sum();
        return globalPopulation;
    }

    public static boolean containsColor(Color color) {
        return regionExtent.containsKey(color);
    }

    public static Region getCountryByColor(Color color) {
        return regionExtent.get(color);
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

    public RegionPoint getCountryPoint() {
        return regionPoint;
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