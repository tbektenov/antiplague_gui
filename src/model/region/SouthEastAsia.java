package model.region;

import model.transport.TransportType;

import java.awt.*;
import java.util.Set;
import java.util.function.Consumer;

public class SouthEastAsia
    extends Region {

    public SouthEastAsia(String name, Color color, int population, RegionPoint regionPoint, Consumer<Region> callback) {
        super(name, color, population, regionPoint, callback);
    }

    public SouthEastAsia(String name, Color color, int population, RegionPoint regionPoint, Consumer<Region> callback, Set<TransportType> acceptedTransportTypes, Set<TransportType> supportedTransportTypes) {
        super(name, color, population, regionPoint, callback, acceptedTransportTypes, supportedTransportTypes);
    }
}
