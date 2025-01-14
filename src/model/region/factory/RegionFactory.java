package model.region.factory;

import model.region.common.RegionPoint;
import model.region.regions.Region;
import model.transport.TransportType;

import java.awt.*;
import java.util.Set;
import java.util.function.Consumer;

public interface RegionFactory {

    Region createRegion(String name, Color color, int population, RegionPoint regionPoint, Consumer<Region> callback);

    Region createRegion(String name, Color color, int population, RegionPoint regionPoint, Consumer<Region> callback,
                        Set<TransportType> supportedTransportTypes);
}
