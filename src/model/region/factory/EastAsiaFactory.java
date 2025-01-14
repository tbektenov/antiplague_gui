package model.region.factory;

import model.difficulty.Difficulty;
import model.region.common.RegionPoint;
import model.region.regions.EastAsia;
import model.region.regions.Region;
import model.transport.TransportType;

import java.awt.*;
import java.util.Set;
import java.util.function.Consumer;

public class EastAsiaFactory
        implements RegionFactory {

    @Override
    public Region createRegion(String name, Color color, int population, RegionPoint regionPoint, Consumer<Region> callback, Difficulty difficulty) {
        return new EastAsia(name, color, population, regionPoint, callback, difficulty);
    }

    @Override
    public Region createRegion(String name, Color color, int population, RegionPoint regionPoint, Consumer<Region> callback,
                               Difficulty difficulty, Set<TransportType> supportedTransportTypes) {
        return new EastAsia(name, color, population, regionPoint, callback, difficulty, supportedTransportTypes);
    }
}
