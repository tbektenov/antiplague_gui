package model.region.regions;

import model.difficulty.Difficulty;
import model.region.common.RegionPoint;
import model.region.common.Virus;
import model.transport.TransportType;

import java.awt.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

public abstract class Region {

    private static final Map<Color, Region> regionExtent = new HashMap<>();

    private final String name;
    private int population;
    private final Virus virus;
    private final RegionPoint regionPoint;
    private Set<TransportType> supportedTransportTypes;
    private final Map<TransportType, Set<String>> acceptedTransport = new HashMap<>();

    private int infectedPopulation;
    private int curedPopulation;

    public Region(String name,
                  Color color,
                  int population,
                  RegionPoint regionPoint,
                  Consumer<Region> callback,
                  Difficulty difficulty,
                  Set<TransportType> supportedTransportTypes) {
        this.name = name;
        this.population = population;
        this.regionPoint = regionPoint;
        this.virus = new Virus(infectionLevel -> callback.accept(this), difficulty, this);


        this.supportedTransportTypes = new HashSet<>(Objects.requireNonNullElseGet(supportedTransportTypes, () -> EnumSet.allOf(TransportType.class)));
        initializeTransportRules();

        this.infectedPopulation = calculateInfectedPopulation();

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
                  Difficulty difficulty) {
        this(name, color, population, regionPoint, callback, difficulty, null);
    }

    public static Map<Color, Region> getRegionExtent() {
        return Collections.unmodifiableMap(regionExtent);
    }

    public static void resetRegions() {
        regionExtent.clear();
    }

    protected abstract void initializeTransportRules();

    public static synchronized long getGlobalPopulation() {
        return regionExtent.values().stream().mapToLong(Region::getPopulation).sum();
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
        supportedTransportTypes.add(type);
    }

    public synchronized void removeSupportedTransportType(TransportType type) {
        supportedTransportTypes.remove(type);
    }

    public boolean supportsTransport(TransportType transportType) {
        return supportedTransportTypes.contains(transportType);
    }

    public synchronized void decreasePopulation() {
        int subtrahend = calculateSubtrahend();

        if (this.population - subtrahend >= 0) {
            this.population -= subtrahend;
        } else {
            this.population = 0;
        }
    }

    public int calculateSubtrahend() {
        int infectedPopulation = calculateInfectedPopulation();
        int subtrahend = (int) (infectedPopulation * ThreadLocalRandom.current().nextFloat(.003f));
        return subtrahend;
    }

    private int calculateInfectedPopulation() {
        return (int) (this.population * this.virus.getInfectionLevel());
    }

    public synchronized void updateInfectedPopulation() {
        this.infectedPopulation = calculateInfectedPopulation();
    }

    public int getInfectedPopulation() {
        return this.infectedPopulation;
    }

    public int getCuredPopulation() {
        return (int) (this.population * this.virus.getInfectionLevel());
    }

    public String getName() {
        return name;
    }

    public int getPopulation() {
        return population;
    }

    public Virus getVirus() {
        return virus;
    }

    public float getInfectionLevel() {
        return 100 * virus.getInfectionLevel();
    }

    public void startInfection() {
        new Thread(virus).start();
    }

    public void stopInfection() {
        virus.stop();
    }

    public RegionPoint getRegionPoint() {
        return regionPoint;
    }

    @Override
    public String toString() {
        return name;
    }
}
