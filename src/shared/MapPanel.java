package shared;

import controller.RegionController;
import controller.TransportController;
import model.difficulty.Difficulty;
import model.region.common.RegionPoint;
import model.region.regions.Region;
import model.transport.TransportManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class MapPanel
        extends JPanel {

    private Image worldMap;
    private final RegionController regionController;
    private final TransportManager transportManager;
    private final TransportController transportController;

    public MapPanel(StatsPanel statsPanel, Difficulty difficulty) {
        this.regionController = new RegionController(statsPanel, difficulty);
        this.transportManager = new TransportManager(this);
        this.transportController = new TransportController(transportManager, difficulty);

        setBackground(Color.LIGHT_GRAY);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        loadWorldMap();
        regionController.startInfections();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Robot robot = new Robot();
                    Point screenPoint = e.getLocationOnScreen();
                    Color pixelColor = robot.getPixelColor(screenPoint.x, screenPoint.y);

                    if (regionController.containsColor(pixelColor)) {
                        Region region = regionController.getRegionByColor(pixelColor);
                        statsPanel.setSelectedRegion(region);
                    } else {
                        statsPanel.setSelectedRegion(null);
                    }
                } catch (AWTException ex) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Unexpected error occurred: " + e,
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void loadWorldMap() {
        if (worldMap == null) {
            try {
                worldMap = ImageIO.read(new File(("./src/resources/World_map.png")));
            } catch (IOException | NullPointerException e) {
                JOptionPane.showMessageDialog(
                        null,
                        "Error loading background: " + e,
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackground(g);
        drawCountryPoints(g);
        drawActiveTransports(g);
    }

    private void drawBackground(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());

        if (worldMap != null) {
            g.drawImage(worldMap, 0, 0, getWidth(), getHeight(), null);
        }
    }

    private void drawCountryPoints(Graphics g) {
        for (Map.Entry<Color, Region> entry : Region.getRegionExtent().entrySet()) {
            Region region = entry.getValue();
            RegionPoint point = region.getRegionPoint();
            int x = point.getTrueX(getWidth());
            int y = point.getTrueY(getHeight());

            g.setColor(Color.BLACK);
            g.fillOval(x - 5, y - 5, 10, 10);
        }
    }

    private void drawActiveTransports(Graphics g) {
        for (var thread : transportManager.getActiveTransports()) {
            thread.drawTransport(g, getWidth(), getHeight());
        }
    }

    public void stopThreads() {
        regionController.stopInfections();
        transportManager.stopAll();
        transportController.stopTransportSpawning();
    }

    public TransportManager getTransportManager() {
        return transportManager;
    }

    public RegionController getRegionController() {
        return regionController;
    }
}
