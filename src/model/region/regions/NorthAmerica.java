package model.region.regions;

import model.region.common.RegionPoint;
import model.transport.TransportType;

import java.awt.*;
import java.util.Set;
import java.util.function.Consumer;

public class NorthAmerica
    extends Region {

    public NorthAmerica(String name, Color color, int population, RegionPoint regionPoint, Consumer<Region> callback) {
        super(name, color, population, regionPoint, callback);
    }

    public NorthAmerica(String name, Color color, int population, RegionPoint regionPoint, Consumer<Region> callback, Set<TransportType> supportedTransportTypes) {
        super(name, color, population, regionPoint, callback, supportedTransportTypes);
    }

    @Override
    protected void initializeTransportRules() {
        addAcceptedTransport(TransportType.CAR, Set.of("South America", "North America"));
        addAcceptedTransport(TransportType.TRAIN, Set.of("South America", "North America"));
        addAcceptedTransport(TransportType.BOAT, Set.of("Africa", "East Asia", "Europe", "Middle East", "North America", "North Asia", "Oceania", "South America", "South Asia", "South East Asia"));
        addAcceptedTransport(TransportType.PLANE, Set.of("Africa", "Central Asia", "East Asia", "Europe", "Middle East", "North America", "North Asia", "Oceania", "South America", "South Asia", "South East Asia"));
    }
}
