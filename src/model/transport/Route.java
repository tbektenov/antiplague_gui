package model.transport;

import model.region.RegionPoint;

public class Route {
    private final RegionPoint startCountry;
    private final RegionPoint endCountry;

    public Route(RegionPoint startCountry,
                 RegionPoint endCountry)
    {
        this.startCountry = startCountry;
        this.endCountry = endCountry;
    }

    public RegionPoint getStartPoint() {
        return startCountry;
    }

    public RegionPoint getEndPoint() {
        return endCountry;
    }
}
