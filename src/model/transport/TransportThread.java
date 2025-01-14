package model.transport;

import model.region.common.RegionPoint;

import javax.swing.*;
import java.awt.*;

public class TransportThread
        implements Runnable {

    private final Transport transport;
    private final JPanel panel;
    private final TransportManager manager;
    private volatile boolean running = true;

    public TransportThread(Transport transport, JPanel panel, TransportManager manager) {
        this.transport = transport;
        this.panel = panel;
        this.manager = manager;
    }

    @Override
    public void run() {
        while (running && !transport.hasReachedDestination()) {
            transport.move(.01f);
            panel.repaint();

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                JOptionPane.showMessageDialog(null,
                        "Unexpected error has occurred while transport was traveling: " + e,
                        "Error",
                        JOptionPane.ERROR_MESSAGE);

                Thread.currentThread().interrupt();
            }
        }

        if (transport.hasReachedDestination()) {
            synchronized (manager) {
                manager.removeTransport(this);
            }
        }
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

        try {
            g.drawImage(icon, position.x - (iconWidth / 2), position.y - (iconHeight / 2), iconWidth, iconHeight, null);
        } catch (Exception e){
            g.setColor(new Color(0xFF0095));
            g.fillOval(position.x - 3, position.y - 3, 6, 6);

            JOptionPane.showMessageDialog(
                    null,
                    "Error drawing icon: " + e,
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void stopTransport() {
        running = false;
    }
}
