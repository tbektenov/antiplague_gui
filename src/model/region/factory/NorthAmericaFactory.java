package model.region.factory;

import model.region.common.RegionPoint;
import model.region.regions.NorthAmerica;
import model.region.regions.Region;
import model.transport.TransportType;

import java.awt.*;
import java.util.Set;
import java.util.function.Consumer;

public class NorthAmericaFactory
        implements RegionFactory {

    @Override
    public Region createRegion(String name, Color color, int population, RegionPoint regionPoint, Consumer<Region> callback) {
        return new NorthAmerica(name, color, population, regionPoint, callback);
    }

    @Override
    public Region createRegion(String name, Color color, int population, RegionPoint regionPoint, Consumer<Region> callback, Set<TransportType> supportedTransportTypes) {
        return new NorthAmerica(name, color, population, regionPoint, callback, supportedTransportTypes);
    }
}
