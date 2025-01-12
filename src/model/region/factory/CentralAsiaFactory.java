package model.region.factory;

import model.region.common.RegionPoint;
import model.region.regions.CentralAsia;
import model.region.regions.Region;
import model.transport.TransportType;

import java.awt.*;
import java.util.Set;
import java.util.function.Consumer;

public class CentralAsiaFactory
        implements RegionFactory {

    @Override
    public Region createRegion(String name, Color color, int population, RegionPoint regionPoint, Consumer<Region> callback) {
        return new CentralAsia(name, color, population, regionPoint, callback);
    }

    @Override
    public Region createRegion(String name, Color color, int population, RegionPoint regionPoint, Consumer<Region> callback, Set<TransportType> acceptedTransportTypes, Set<TransportType> supportedTransportTypes) {
        return new CentralAsia(name, color, population, regionPoint, callback, acceptedTransportTypes, supportedTransportTypes);
    }
}
