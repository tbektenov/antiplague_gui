package shared;

import controller.RegionController;
import controller.TransportController;
import model.difficulty.Difficulty;
import model.region.regions.Region;
import model.region.common.RegionPoint;
import model.transport.TransportManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class MapPanel
        extends JPanel {

    private BufferedImage worldMap;
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
                    ex.printStackTrace();
                }
            }
        });
    }

    private void loadWorldMap() {
        try {
            worldMap = ImageIO.read(Objects.requireNonNull(getClass().getResource("/resources/World_map.png")));
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
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
}
