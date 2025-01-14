package model.transport;

import model.region.common.RegionPoint;
import model.region.regions.Region;

public class Route {
    private final Region startRegion;
    private final Region endRegion;
    private final RegionPoint startPoint;
    private final RegionPoint endPoint;

    public Route(Region startRegion,
                 Region endRegion)
    {
        this.startRegion = startRegion;
        this.endRegion = endRegion;

        this.startPoint = startRegion.getRegionPoint();
        this.endPoint = endRegion.getRegionPoint();
    }

    public Region getStartRegion() {
        return startRegion;
    }

    public Region getEndRegion() {
        return endRegion;
    }

    public RegionPoint getStartPoint() {
        return this.startPoint;
    }

    public RegionPoint getEndPoint() {
        return this.endPoint;
    }
}
