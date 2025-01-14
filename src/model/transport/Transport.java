package model.transport;

import model.region.regions.Region;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Transport {

    private final TransportType transportType;
    private final Route route;
    private float progress;
    private Image icon;

    public Transport(TransportType transportType, Route route) {
        this.transportType = transportType;
        this.route = route;
        this.progress = 0f;
        loadIcon();
    }

    public void move(float delta) {
        float adjustedDelta = delta * getSpeedMultiplier();

        if ((this.progress + adjustedDelta) <= 1f) {
            this.progress += adjustedDelta;
        } else {
            this.progress = 1f;
        }
    }

    private void loadIcon() {
        String iconPath;

        switch (transportType) {
            case PLANE -> iconPath = "/resources/plane.png";
            case TRAIN -> iconPath ="/resources/train.png";
            case CAR -> iconPath = "/resources/car.png";
            case BOAT -> iconPath = "/resources/boat.png";
            default -> iconPath = null;
        };

        try {
            this.icon = ImageIO.read(Objects.requireNonNull(getClass().getResource(iconPath)));
        } catch (IOException | NullPointerException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error loading icon: " + e,
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public boolean hasReachedDestination() {
        return progress == 1f;
    }

    private float getSpeedMultiplier() {
        return switch (transportType) {
            case PLANE -> 1f;
            case TRAIN -> .7f;
            case CAR -> .4f;
            case BOAT -> .5f;
        };
    }

    public Region getArrivalRegion() {
        return this.route.getStartRegion();
    }

    public Region getDestinationRegion() {
        return this.route.getEndRegion();
    }

    public Route getRoute() {
        return this.route;
    }

    public double getProgress() {
        return this.progress;
    }

    public TransportType getTransportType() {
        return this.transportType;
    }

    public Image getIcon() {
        return this.icon;
    }
}
