package model.transport;

public class Transport {
    private final TransportType transportType;
    private final Route route;
    private double progress;

    public Transport(TransportType transportType, Route route) {
        this.transportType = transportType;
        this.route = route;
        this.progress = 0.0;
    }

    public Route getRoute() {
        return route;
    }

    public double getProgress() {
        return progress;
    }

    public TransportType getTransportType() {
        return transportType;
    }

    public void move(double delta) {
        double adjustedDelta = delta * getSpeedMultiplier();
        this.progress = Math.min(progress + adjustedDelta, 1.0);
    }

    public boolean hasReachedDestination() {
        return progress >= 1.0;
    }

    private double getSpeedMultiplier() {
        return switch (transportType) {
            case PLANE -> 1.0;
            case TRAIN -> 0.7;
            case CAR -> 0.4;
            case BOAT -> 0.5;
        };
    }
}
