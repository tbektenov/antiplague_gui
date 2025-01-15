package controller;

import model.difficulty.Difficulty;
import model.region.factory.*;
import model.region.common.RegionPoint;
import model.region.regions.*;
import model.transport.TransportType;
import shared.StatsPanel;

import java.awt.*;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegionController {

    private final ExecutorService executorService;
    private final StatsPanel statsPanel;
    private final Difficulty difficulty;

    public RegionController(StatsPanel statsPanel, Difficulty difficulty) {
        this.statsPanel = statsPanel;
        this.difficulty = difficulty;

        Region.resetRegions();
        initializeRegions();

        executorService = Executors.newFixedThreadPool(Region.getRegionExtent().size());
    }

    private void initializeRegions() {
        new AfricaFactory().createRegion("Africa", new Color(0, 255, 155), 1_300_000_000,
                new RegionPoint(.4f, .7f), statsPanel::updateInfection, difficulty);

        new OceaniaFactory().createRegion("Oceania", new Color(0, 133, 255), 46_344_000,
                new RegionPoint(.9f, .9f), statsPanel::updateInfection, difficulty);

        new CentralAsiaFactory().createRegion("Central Asia", new Color(244, 255, 0), 82_893_000,
                new RegionPoint(.67f, .45f), statsPanel::updateInfection,
                difficulty,
                Set.of(TransportType.PLANE, TransportType.CAR, TransportType.TRAIN));

        new EastAsiaFactory().createRegion("East Asia", new Color(255, 122, 213), 1_654_000_000,
                new RegionPoint(.85f, .45f), statsPanel::updateInfection, difficulty);

        new EuropeFactory().createRegion("Europe", new Color(200, 0, 255), 742_300_000,
                new RegionPoint(.43f, .3f), statsPanel::updateInfection, difficulty);

        new MiddleEastFactory().createRegion("Middle East", new Color(255, 133, 0), 381_000_000,
                new RegionPoint(.5f, .4f), statsPanel::updateInfection, difficulty);

        new NorthAmericaFactory().createRegion("North America", new Color(0, 0, 255), 592_000_000,
                new RegionPoint(.2f, .27f), statsPanel::updateInfection, difficulty);

        new NorthAsiaFactory().createRegion("North Asia", new Color(255, 0, 0), 36_800_000,
                new RegionPoint(.6f, .3f), statsPanel::updateInfection, difficulty);

        new SouthAmericaFactory().createRegion("South America", new Color(0, 255, 0), 442_000_000,
                new RegionPoint(.2f, .7f), statsPanel::updateInfection, difficulty);

        new SouthAsiaFactory().createRegion("South Asia", new Color(0, 244, 255), 2_040_000_000,
                new RegionPoint(.7f, .6f), statsPanel::updateInfection, difficulty);

        new SouthEastAsiaFactory().createRegion("South East Asia", new Color(200, 255, 0), 697_548_000,
                new RegionPoint(.85f, .6f), statsPanel::updateInfection, difficulty);
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
}
