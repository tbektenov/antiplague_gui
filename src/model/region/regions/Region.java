package model.region.regions;

import model.difficulty.Difficulty;
import model.region.common.Cure;
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
    private long merePopulation;
    private final RegionPoint regionPoint;
    private Set<TransportType> supportedTransportTypes;
    private final Map<TransportType, Set<String>> acceptedTransport = new HashMap<>();

    private final Virus virus;
    private final Cure cure;

    private long infectedPopulation;
    private long curedPopulation;

    public Region(String name,
                  Color color,
                  int merePopulation,
                  RegionPoint regionPoint,
                  Consumer<Region> callback,
                  Difficulty difficulty,
                  Set<TransportType> supportedTransportTypes) {
        this.name = name;
        this.merePopulation = merePopulation;
        this.regionPoint = regionPoint;

        this.virus = new Virus(infectionLevel -> callback.accept(this), difficulty, this);
        this.cure = new Cure(this);

        this.supportedTransportTypes = new HashSet<>(Objects.requireNonNullElseGet(supportedTransportTypes, () -> EnumSet.allOf(TransportType.class)));
        initializeTransportRules();

        this.infectedPopulation = 0;
        this.curedPopulation = 0;

        if (regionExtent.containsKey(color)) {
            throw new IllegalArgumentException("Region associated with this color already exists");
        }

        regionExtent.put(color, this);
    }

    public Region(String name,
                  Color color,
                  int merePopulation,
                  RegionPoint regionPoint,
                  Consumer<Region> callback,
                  Difficulty difficulty) {
        this(name, color, merePopulation, regionPoint, callback, difficulty, null);
    }

    public static Map<Color, Region> getRegionExtent() {
        return Collections.unmodifiableMap(regionExtent);
    }

    public static void resetRegions() {
        regionExtent.clear();
    }

    public synchronized static boolean isGameRunning() {
        double totalPopulation = 0;
        double totalInfected = 0;
        double totalCured = 0;

        for (Region region : regionExtent.values()) {
            totalPopulation += region.merePopulation;
            totalInfected += region.infectedPopulation;
            totalCured += region.curedPopulation;
        }

        if (totalInfected == totalPopulation || totalCured == totalPopulation) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean containsColor(Color color) {
        return regionExtent.containsKey(color);
    }

    public static Region getRegionByColor(Color color) {
        return regionExtent.get(color);
    }

    public static long getGlobalCuredPopulation() {
        return regionExtent.values().stream().mapToLong(Region::getCuredPopulation).sum();
    }

    protected abstract void initializeTransportRules();

    public static synchronized long getGlobalPopulation() {
        return regionExtent.values().stream().mapToLong(Region::getMerePopulation).sum();
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

    public synchronized void addSupportedTransportType(TransportType type) {
        supportedTransportTypes.add(type);
    }

    public synchronized void removeSupportedTransportType(TransportType type) {
        supportedTransportTypes.remove(type);
    }

    public Set<TransportType> getSupportedTransportTypes() {
        return supportedTransportTypes;
    }

    public void setSupportedTransportTypes(Set<TransportType> supportedTransportTypes) {
        this.supportedTransportTypes = supportedTransportTypes;
    }

    public boolean supportsTransport(TransportType transportType) {
        return supportedTransportTypes.contains(transportType);
    }

    public synchronized long getInfectedPopulation() {
        return this.infectedPopulation;
    }

    public float getCureEfficiency() {
        return 100 * this.cure.getCureEfficiency();
    }

    public synchronized void increaseInfectedPopulation(long addend) {
        long maxInfectable = merePopulation - (infectedPopulation + curedPopulation);
        this.infectedPopulation += Math.min(addend, maxInfectable);
        assert infectedPopulation <= merePopulation;
    }

    public synchronized void increaseCuredPopulation(int addend) {
        long maxCurable = infectedPopulation;
        this.curedPopulation += Math.min(addend, maxCurable);
    }

    public synchronized void decreasePopulation(int subtrahend) {
        this.merePopulation = Math.max(this.merePopulation - subtrahend, 0);
    }

    public synchronized void decreaseInfectedPopulation(int subtrahend) {
        this.infectedPopulation = Math.max(this.infectedPopulation - subtrahend, 0);
    }

    public synchronized long getCuredPopulation() {
        return this.curedPopulation;
    }

    public String getName() {
        return name;
    }

    public long getMerePopulation() {
        return merePopulation;
    }

    public Virus getVirus() {
        return virus;
    }

    public Cure getCure() {
        return cure;
    }

    public float getInfectionSpeed() {
        return 100 * virus.getInfectionSpeed();
    }

    public void start() {
        new Thread(virus).start();
        new Thread(cure).start();
    }

    public void stop() {
        virus.stop();
        cure.stop();
    }

    public RegionPoint getRegionPoint() {
        return regionPoint;
    }

    @Override
    public String toString() {
        return name;
    }
}
