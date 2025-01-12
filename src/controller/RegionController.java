package controller;

import model.region.*;
import model.transport.TransportType;
import shared.StatsPanel;

import java.awt.*;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegionController {
    private static RegionController instance;
    private final ExecutorService executorService;

    private final StatsPanel statsPanel;

    private RegionController(StatsPanel statsPanel) {
        this.statsPanel = statsPanel;
        initializeRegions();
        executorService = Executors.newFixedThreadPool(Region.getRegionExtent().size());
    }

    private void initializeRegions() {
        new Africa("Africa", new Color(0, 255, 155), 1_300_000_000, new RegionPoint(0.4, 0.7), statsPanel::updateInfection);
        new Oceania("Oceania", new Color(0, 133, 255), 46_344_000, new RegionPoint(0.9, 0.9), statsPanel::updateInfection);

        new CentralAsia(
                "Central Asia",
                new Color(244, 255, 0),
                82_893_000,
                new RegionPoint(0.67, 0.45),
                statsPanel::updateInfection,
                new HashSet<>() {{
                    add(TransportType.PLANE);
                    add(TransportType.CAR);
                    add(TransportType.TRAIN);
                }},
                new HashSet<>() {{
                    add(TransportType.PLANE);
                    add(TransportType.CAR);
                    add(TransportType.TRAIN);
                }}
        );

        new EastAsia("East Asia", new Color(255, 122, 213), 1_654_000_000, new RegionPoint(0.85, 0.45), statsPanel::updateInfection);
        new Europe("Europe", new Color(200, 0, 255), 742_300_000, new RegionPoint(0.43, 0.3), statsPanel::updateInfection);
        new MiddleEast("Middle East", new Color(255, 133, 0), 381_000_000, new RegionPoint(0.5, 0.4), statsPanel::updateInfection);
        new NorthAmerica("North America", new Color(0, 0, 255), 592_000_000, new RegionPoint(0.2, 0.27), statsPanel::updateInfection);
        new NorthAsia("North Asia", new Color(255, 0, 0), 36_800_000, new RegionPoint(0.6, 0.3), statsPanel::updateInfection);
        new SouthAmerica("South America", new Color(0, 255, 0), 442_000_000, new RegionPoint(0.2, 0.7), statsPanel::updateInfection);
        new SouthAsia("South Asia", new Color(0, 244, 255), 2_040_000_000, new RegionPoint(0.7, 0.6), statsPanel::updateInfection);
        new SouthEastAsia("South East Asia", new Color(200, 255, 0), 697_548_000, new RegionPoint(0.85, 0.6), statsPanel::updateInfection);
    }



    public void startInfections() {
        Region.getRegionExtent().values().forEach(Region::startInfection);
    }

    public void stopInfections() {
        executorService.shutdownNow();
        Region.getRegionExtent().values().forEach(Region::stopInfection);
    }

    public boolean containsColor(Color color) {
        return Region.containsColor(color);
    }

    public Region getRegionByColor(Color color) {
        return Region.getRegionByColor(color);
    }

    public void decreaseCountryPopulation(Region region, int subtrahend) {
        region.decreasePopulation(subtrahend);
    }

    public static RegionController getInstance(StatsPanel statsPanel) {
        if (instance == null) {
            instance = new RegionController(statsPanel);
        }
        return instance;
    }
}
