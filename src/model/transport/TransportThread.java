package model.transport;

import model.region.common.RegionPoint;

import javax.swing.*;
import java.awt.*;

public class TransportThread
        implements Runnable {
    private final Transport transport;
    private final JPanel panel;
    private final TransportManager manager;
    private boolean running = true;

    public TransportThread(Transport transport,
                           JPanel panel,
                           TransportManager manager) {
        this.transport = transport;
        this.panel = panel;
        this.manager = manager;
    }

    @Override
    public void run() {
        while (running && !transport.hasReachedDestination()) {
            transport.move(0.01);
            panel.repaint();

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        running = false;
        manager.removeTransport(this);
    }

    public Point getCurrentPosition(int panelWidth, int panelHeight) {
        RegionPoint start = transport.getRoute().getStartPoint();
        RegionPoint end = transport.getRoute().getEndPoint();

        int startX = start.getTrueX(panelWidth);
        int startY = start.getTrueY(panelHeight);
        int endX = end.getTrueX(panelWidth);
        int endY = end.getTrueY(panelHeight);

        int currentX = (int) (startX + (endX - startX) * transport.getProgress());
        int currentY = (int) (startY + (endY - startY) * transport.getProgress());

        return new Point(currentX, currentY);
    }

    public void drawTransport(Graphics g, int panelWidth, int panelHeight) {
        Point position = getCurrentPosition(panelWidth, panelHeight);

        int iconWidth = 30;
        int iconHeight = 30;

        Image icon = transport.getIcon();

        if (icon != null) {
            g.drawImage(icon, position.x - (iconWidth / 2), position.y - (iconHeight / 2), iconWidth, iconHeight, null);
        } else {
            g.setColor(new Color(0xFF0095));
            g.fillOval(position.x - 3, position.y - 3, 6, 6);
        }
    }

    private Color getColorByType() {
        return switch (transport.getTransportType()) {
            case PLANE -> new Color(0x00949AFF);
            case TRAIN -> new Color(0x6A4001);
            case CAR -> new Color(0x8C0000);
            case BOAT -> new Color(0x6C6C8E);
        };
    }

    public void stopTransport() {
        running = false;
    }
}
