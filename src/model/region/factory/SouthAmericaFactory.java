package model.region.factory;

import model.region.common.RegionPoint;
import model.region.regions.SouthAmerica;
import model.region.regions.Region;
import model.transport.TransportType;

import java.awt.*;
import java.util.Set;
import java.util.function.Consumer;

public class SouthAmericaFactory
        implements RegionFactory {

    @Override
    public Region createRegion(String name, Color color, int population, RegionPoint regionPoint, Consumer<Region> callback) {
        return new SouthAmerica(name, color, population, regionPoint, callback);
    }

    @Override
    public Region createRegion(String name, Color color, int population, RegionPoint regionPoint, Consumer<Region> callback, Set<TransportType> acceptedTransportTypes, Set<TransportType> supportedTransportTypes) {
        return new SouthAmerica(name, color, population, regionPoint, callback, acceptedTransportTypes, supportedTransportTypes);
    }
}
