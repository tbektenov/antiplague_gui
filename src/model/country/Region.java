package model.country;

import model.transport.TransportType;

import java.awt.*;
import java.util.*;
import java.util.function.Consumer;

public class Region {
    private static final Map<Color, Region> regionExtent = new HashMap<Color, Region>();

    private final String name;
    private int population;
    private final Virus virus;
    private final RegionPoint regionPoint;
    private Set<TransportType> acceptedTransportTypes;
    private Set<TransportType> supportedTransportTypes;

    public Region(String name,
                  Color color,
                  int population,
                  RegionPoint regionPoint,
                  Consumer<Region> callback) {
        this.name = name;
        this.population = population;
        this.regionPoint = regionPoint;
        this.virus = new Virus(name, infectionLevel -> {
            callback.accept(this);
            updateTransportRestrictions(infectionLevel);
        });

        this.acceptedTransportTypes = new HashSet<>(EnumSet.allOf(TransportType.class));
        this.supportedTransportTypes = new HashSet<>(EnumSet.allOf(TransportType.class));

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
                  Set<TransportType> acceptedTransportTypes,
                  Set<TransportType> supportedTransportTypes) {
        this.name = name;
        this.population = population;
        this.regionPoint = regionPoint;
        this.virus = new Virus(name, infectionLevel -> {
            callback.accept(this);
            updateTransportRestrictions(infectionLevel);
        });

        if (acceptedTransportTypes == null) this.acceptedTransportTypes = new HashSet<>(EnumSet.allOf(TransportType.class));
        else {
            this.acceptedTransportTypes = new HashSet<>(acceptedTransportTypes);
        }

        if (supportedTransportTypes == null) this.supportedTransportTypes = new HashSet<>(EnumSet.allOf(TransportType.class));
        else {
            this.supportedTransportTypes = new HashSet<>(supportedTransportTypes);
        }

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

    public static Region getRegionByColor(Color color) {
        return regionExtent.get(color);
    }

    public void addAcceptedTransportType(TransportType type) {
        this.acceptedTransportTypes.add(type);
    }

    public void removeAcceptedTransportType(TransportType type) {
        this.acceptedTransportTypes.remove(type);
    }

    public boolean acceptsTransport(TransportType transportType) {
        return this.acceptedTransportTypes.contains(transportType);
    }

    public void addSupportedTransportType(TransportType type) {
        this.supportedTransportTypes.add(type);
    }

    public void removeSupportedTransportType(TransportType type) {
        this.supportedTransportTypes.remove(type);
    }

    public boolean supportsTransport(TransportType transportType) {
        return this.supportedTransportTypes.contains(transportType);
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

    public RegionPoint getRegionPoint() {
        return this.regionPoint;
    }

    private void updateTransportRestrictions(float infectionLevel) {
        if (infectionLevel >= 75) {
            acceptedTransportTypes.remove(TransportType.PLANE);
        } else if (infectionLevel >= 50) {
            acceptedTransportTypes.remove(TransportType.BOAT);
        } else if (infectionLevel >= 30) {
            acceptedTransportTypes.remove(TransportType.TRAIN);
        } else if (infectionLevel >= 25) {
            acceptedTransportTypes.remove(TransportType.CAR);
        }
    }
}