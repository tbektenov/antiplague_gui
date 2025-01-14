package model.region.factory;

import model.region.common.RegionPoint;
import model.region.regions.Oceania;
import model.region.regions.Region;
import model.transport.TransportType;

import java.awt.*;
import java.util.Set;
import java.util.function.Consumer;

public class OceaniaFactory
        implements RegionFactory {

    @Override
    public Region createRegion(String name, Color color, int population, RegionPoint regionPoint, Consumer<Region> callback) {
        return new Oceania(name, color, population, regionPoint, callback);
    }

    @Override
    public Region createRegion(String name, Color color, int population, RegionPoint regionPoint, Consumer<Region> callback, Set<TransportType> supportedTransportTypes) {
        return new Oceania(name, color, population, regionPoint, callback, supportedTransportTypes);
    }
}