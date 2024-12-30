package shared;

import controller.CountryController;
import model.country.Country;
import model.country.CountryPoint;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class MapPanel extends JPanel {
    private BufferedImage worldMap;
    private final CountryController countryController;

    public MapPanel(StatsPanel statsPanel) {
        this.countryController = CountryController.getInstance();
        setBackground(Color.LIGHT_GRAY);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        try {
            worldMap = ImageIO.read(Objects.requireNonNull(getClass().getResource("/resources/World_map.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        countryController.startInfections();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Robot robot = new Robot();
                    Point screenPoint = e.getLocationOnScreen();
                    Color pixelColor = robot.getPixelColor(screenPoint.x, screenPoint.y);

                    if (countryController.containsColor(pixelColor)) {
                        Country country = countryController.getCountryByColor(pixelColor);
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

        for (Map.Entry<Color, Country> entry : Country.getCountryExtent().entrySet()) {
            Color countryColor = entry.getKey();
            Country country = entry.getValue();
            CountryPoint point = country.getCountryPoint();

            int x = point.getAbsoluteX(getWidth());
            int y = point.getAbsoluteY(getHeight());

            g.setColor(Color.BLACK);
            g.fillOval(x - 5, y - 5, 10, 10);
        }
    }

    public void stopThreads() {
        countryController.stopInfections();
    }
}
