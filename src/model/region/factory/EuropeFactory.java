package model.region.factory;

import model.difficulty.Difficulty;
import model.region.common.RegionPoint;
import model.region.regions.Europe;
import model.region.regions.Region;
import model.transport.TransportType;

import java.awt.*;
import java.util.Set;
import java.util.function.Consumer;

public class EuropeFactory
        implements RegionFactory {

    @Override
    public Region createRegion(String name, Color color, int population, RegionPoint regionPoint, Consumer<Region> callback, Difficulty difficulty) {
        return new Europe(name, color, population, regionPoint, callback, difficulty);
    }

    @Override
    public Region createRegion(String name, Color color, int population, RegionPoint regionPoint, Consumer<Region> callback,
                               Difficulty difficulty, Set<TransportType> supportedTransportTypes) {
        return new Europe(name, color, population, regionPoint, callback, difficulty, supportedTransportTypes);
    }
}
