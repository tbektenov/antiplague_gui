package model.region.factory;

import model.region.common.RegionPoint;
import model.region.regions.SouthAsia;
import model.region.regions.Region;
import model.transport.TransportType;

import java.awt.*;
import java.util.Set;
import java.util.function.Consumer;

public class SouthAsiaFactory
        implements RegionFactory {

    @Override
    public Region createRegion(String name, Color color, int population, RegionPoint regionPoint, Consumer<Region> callback) {
        return new SouthAsia(name, color, population, regionPoint, callback);
    }

    @Override
    public Region createRegion(String name, Color color, int population, RegionPoint regionPoint, Consumer<Region> callback, Set<TransportType> acceptedTransportTypes, Set<TransportType> supportedTransportTypes) {
        return new SouthAsia(name, color, population, regionPoint, callback, acceptedTransportTypes, supportedTransportTypes);
    }
}
