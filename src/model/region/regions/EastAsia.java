package model.region.regions;

import model.difficulty.Difficulty;
import model.region.common.RegionPoint;
import model.transport.TransportType;

import java.awt.*;
import java.util.Set;
import java.util.function.Consumer;

public class EastAsia
    extends Region {

    public EastAsia(String name, Color color, int population, RegionPoint regionPoint, Consumer<Region> callback, Difficulty difficulty) {
        super(name, color, population, regionPoint, callback, difficulty);
    }

    public EastAsia(String name, Color color, int population, RegionPoint regionPoint, Consumer<Region> callback, Difficulty difficulty, Set<TransportType> supportedTransportTypes) {
        super(name, color, population, regionPoint, callback, difficulty, supportedTransportTypes);
    }

    @Override
    protected void initializeTransportRules() {
        addAcceptedTransport(TransportType.CAR, Set.of("Africa", "Central Asia", "East Asia", "Europe", "Middle East", "North Asia", "South Asia", "South East Asia"));
        addAcceptedTransport(TransportType.TRAIN, Set.of("Africa", "Central Asia", "East Asia", "Europe", "Middle East", "North Asia", "South Asia", "South East Asia"));
        addAcceptedTransport(TransportType.BOAT, Set.of("Africa", "East Asia", "Europe", "Middle East", "North America", "North Asia", "Oceania", "South America", "South Asia", "South East Asia"));
        addAcceptedTransport(TransportType.PLANE, Set.of("Africa", "Central Asia", "East Asia", "Europe", "Middle East", "North America", "North Asia", "Oceania", "South America", "South Asia", "South East Asia"));
    }
}
