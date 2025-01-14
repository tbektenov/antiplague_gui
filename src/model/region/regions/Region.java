package model.region.regions;

import model.region.common.RegionPoint;
import model.region.common.Virus;
import model.transport.TransportType;

import java.awt.*;
import java.util.*;
import java.util.function.Consumer;

public abstract
    class Region {

    private static final Map<Color, Region> regionExtent = new HashMap<>();

    private final String name;
    private int population;
    private final Virus virus;
    private final RegionPoint regionPoint;

    // transport
    private Set<TransportType> supportedTransportTypes;
    private final Map<TransportType, Set<String>> acceptedTransport = new HashMap<>();

    public Region(String name,
                  Color color,
                  int population,
                  RegionPoint regionPoint,
                  Consumer<Region> callback) {
        this.name = name;
        this.population = population;
        this.regionPoint = regionPoint;
        this.virus = new Virus( _ -> callback.accept(this));

        initializeTransportRules();
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
                  Set<TransportType> supportedTransportTypes) {
        this.name = name;
        this.population = population;
        this.regionPoint = regionPoint;
        this.virus = new Virus( _ -> callback.accept(this));

        initializeTransportRules();
        this.supportedTransportTypes = new HashSet<>(Objects.requireNonNullElseGet(supportedTransportTypes, () -> EnumSet.allOf(TransportType.class)));

        if (regionExtent.containsKey(color)) {
            throw new IllegalArgumentException("Region associated with this color already exists");
        }

        regionExtent.put(color, this);
    }

    public static Map<Color, Region> getRegionExtent() {
        return Collections.unmodifiableMap(regionExtent);
    }

    public static void resetRegions() {
        regionExtent.clear();
    }

    protected abstract void initializeTransportRules();

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

    public synchronized Map<TransportType, Set<String>> getAcceptedTransport() {
        return Collections.unmodifiableMap(acceptedTransport);
    }

    public synchronized void clearAcceptedTransport() {
        acceptedTransport.clear();
    }

    public synchronized void removeAcceptedTransportType(TransportType transportType) {
        acceptedTransport.remove(transportType);
    }

    public void addAcceptedTransport(TransportType type, Set<String> regions) {
        Set<String> filteredRegions = new HashSet<>(regions);
        filteredRegions.remove(name);
        acceptedTransport.put(type, filteredRegions);
    }

    public boolean acceptsTransport(TransportType type, String regionName) {
        return acceptedTransport.getOrDefault(type, Set.of()).contains(regionName);
    }

    public void addSupportedTransport(TransportType type) {
        supportedTransportTypes.add(type);
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

    @Override
    public String toString() {
        return this.getName();
    }
}