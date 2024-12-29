package view;

import model.countries.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MapPanel extends JPanel {
    private BufferedImage worldMap;
    private StatsPanel statsPanel;
    private Map<Color, Country> countryMap;
    private ExecutorService executorService;

    public MapPanel(StatsPanel statsPanel) {
        this.statsPanel = statsPanel;
        setBackground(Color.LIGHT_GRAY);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        try {
            worldMap = ImageIO.read(Objects.requireNonNull(getClass().getResource("/resources/World_map.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        initializeCountries();
        executorService = Executors.newFixedThreadPool(countryMap.size());
        for (Country country : countryMap.values()) {
            executorService.submit(country);
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Robot robot = new Robot();
                    Point screenPoint = e.getLocationOnScreen();
                    Color pixelColor = robot.getPixelColor(screenPoint.x, screenPoint.y);

                    if (countryMap.containsKey(pixelColor)) {
                        Country country = countryMap.get(pixelColor);
                        if (country != null) {
                            statsPanel.updateStats(country);
                        } else {
                            statsPanel.updateStats(null);
                        }
                    }

                } catch (AWTException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());

        if (worldMap != null) {
            g.drawImage(worldMap, 0, 0, getWidth(), getHeight(), null);
        }
    }

    private void initializeCountries() {
        countryMap = new HashMap<>();

        countryMap.put(new Color(0, 255, 155), Africa.getInstance());
        countryMap.put(new Color(0, 133, 255), Oceania.getInstance());
        countryMap.put(new Color(244, 255, 0), CentralAsia.getInstance());
        countryMap.put(new Color(255, 122, 213), EastAsia.getInstance());
        countryMap.put(new Color(200, 0, 255), Europe.getInstance());
        countryMap.put(new Color(255, 133, 0), MiddleEast.getInstance());
        countryMap.put(new Color(0, 0, 255), NorthAmerica.getInstance());
        countryMap.put(new Color(255, 0, 0), NorthAsia.getInstance());
        countryMap.put(new Color(0, 255, 0), SouthAmerica.getInstance());
        countryMap.put(new Color(0, 244, 255), SouthAsia.getInstance());
        countryMap.put(new Color(200, 255, 0), SouthEastAsia.getInstance());
    }

    public void stopThreads() {
        executorService.shutdownNow();
        countryMap.values().forEach(Country::stop);
    }
}
