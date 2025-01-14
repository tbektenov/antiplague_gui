package model.transport;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Transport {
    private final TransportType transportType;
    private final Route route;
    private double progress;
    private Image icon;

    public Transport(TransportType transportType, Route route) {
        this.transportType = transportType;
        this.route = route;
        this.progress = 0.0;
        loadIcon();
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

    public Image getIcon() {
        return icon;
    }

    public void move(double delta) {
        double adjustedDelta = delta * getSpeedMultiplier();
        this.progress = Math.min(progress + adjustedDelta, 1.0);
    }

    public boolean hasReachedDestination() {
        return progress == 1.0;
    }

    private double getSpeedMultiplier() {
        return switch (transportType) {
            case PLANE -> 1.0;
            case TRAIN -> 0.7;
            case CAR -> 0.4;
            case BOAT -> 0.5;
        };
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
            JOptionPane.showMessageDialog(null,
                    "Error loading icon: " + e,
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
