package model.region.regions;

import model.region.common.RegionPoint;
import model.region.common.Virus;
import model.transport.TransportType;

import java.awt.*;
import java.util.*;
import java.util.function.Consumer;

public abstract
class Region {
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
        this.virus = new Virus(infectionLevel -> {
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
        this.virus = new Virus(infectionLevel -> {
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
        return Collections.unmodifiableMap(regionExtent);
    }

    public synchronized int getGlobalPopulation() {
        int globalPopulation = regionExtent.values().stream().mapToInt(Region::getPopulation).sum();
        return globalPopulation;
    }

    public static boolean containsColor(Color color) {
        return regionExtent.containsKey(color);
    }

    public static Region getRegionByColor(Color color) {
        return regionExtent.get(color);
    }

    public Set<TransportType> getAcceptedTransportTypes() {
        return acceptedTransportTypes;
    }

    public void setAcceptedTransportTypes(Set<TransportType> acceptedTransportTypes) {
        this.acceptedTransportTypes = acceptedTransportTypes;
    }

    public synchronized void addAcceptedTransportType(TransportType type) {
        this.acceptedTransportTypes.add(type);
    }

    public synchronized void removeAcceptedTransportType(TransportType type) {
        if (this.acceptedTransportTypes.contains(type)) acceptedTransportTypes.remove(type);
    }

    public synchronized boolean acceptsTransport(TransportType transportType) {
        return this.acceptedTransportTypes.contains(transportType);
    }

    public Set<TransportType> getSupportedTransportTypes() {
        return supportedTransportTypes;
    }

    public void setSupportedTransportTypes(Set<TransportType> supportedTransportTypes) {
        this.supportedTransportTypes = supportedTransportTypes;
    }

    public synchronized void addSupportedTransportType(TransportType type) {
        this.supportedTransportTypes.add(type);
    }

    public synchronized void removeSupportedTransportType(TransportType type) {
        if (this.supportedTransportTypes.contains(type)) supportedTransportTypes.remove(type);
    }

    public boolean supportsTransport(TransportType transportType) {
        return this.supportedTransportTypes.contains(transportType);
    }

    public synchronized void decreasePopulation(int subtrahend) {
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

    public Virus getVirus() {
        return this.virus;
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

    private synchronized void updateTransportRestrictions(float infectionLevel) {
        if (infectionLevel >= 75) {
            removeAcceptedTransportType(TransportType.PLANE);
        } else if (infectionLevel >= 50) {
            removeAcceptedTransportType(TransportType.BOAT);
        } else if (infectionLevel >= 30) {
            removeAcceptedTransportType(TransportType.TRAIN);
        } else if (infectionLevel >= 25) {
            removeAcceptedTransportType(TransportType.CAR);
        }
    }

    @Override
    public String toString() {
        return this.getName();
    }
}