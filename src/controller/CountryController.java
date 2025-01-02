package controller;

import model.country.Region;
import model.country.RegionPoint;
import shared.StatsPanel;

import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountryController {
    private static CountryController instance;
    private final ExecutorService executorService;

    private final StatsPanel statsPanel;

    private CountryController(StatsPanel statsPanel) {
        this.statsPanel = statsPanel;
        initializeCountries();
        executorService = Executors.newFixedThreadPool(Region.getRegionExtent().size());
    }

    private void initializeCountries() {
        new Region("Africa", new Color(0, 255, 155), 1_300_000_000, new RegionPoint(0.4, 0.7), statsPanel::updateInfection);
        new Region("Oceania", new Color(0, 133, 255), 46_344_000, new RegionPoint(0.9, 0.9), statsPanel::updateInfection);
        new Region("Central Asia", new Color(244, 255, 0), 82_893_000, new RegionPoint(0.67, 0.45), statsPanel::updateInfection);
        new Region("East Asia", new Color(255, 122, 213), 1_654_000_000, new RegionPoint(0.85, 0.45), statsPanel::updateInfection);
        new Region("Europe", new Color(200, 0, 255), 742_300_000, new RegionPoint(0.43, 0.3), statsPanel::updateInfection);
        new Region("Middle East", new Color(255, 133, 0), 381_000_000, new RegionPoint(0.5, 0.4), statsPanel::updateInfection);
        new Region("North America", new Color(0, 0, 255), 592_000_000, new RegionPoint(0.2, 0.27), statsPanel::updateInfection);
        new Region("North Asia", new Color(255, 0, 0), 36_800_000, new RegionPoint(0.6, 0.3), statsPanel::updateInfection);
        new Region("South America", new Color(0, 255, 0), 442_000_000, new RegionPoint(0.2, 0.7), statsPanel::updateInfection);
        new Region("South Asia", new Color(0, 244, 255), 2_040_000_000, new RegionPoint(0.7, 0.6), statsPanel::updateInfection);
        new Region("South East Asia", new Color(200, 255, 0), 697_548_000, new RegionPoint(0.85, 0.6), statsPanel::updateInfection);
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

    public Region getCountryByColor(Color color) {
        return Region.getCountryByColor(color);
    }

    public void decreaseCountryPopulation(Region region, int subtrahend) {
        region.decreasePopulation(subtrahend);
    }

    public static CountryController getInstance(StatsPanel statsPanel) {
        if (instance == null) {
            instance = new CountryController(statsPanel);
        }
        return instance;
    }
}
