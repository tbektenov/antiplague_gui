package controller;

import model.region.regions.Region;
import model.transport.TransportManager;
import model.transport.TransportType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TransportController {
    private final TransportManager transportManager;
    private final ScheduledExecutorService scheduler;

    public TransportController(TransportManager transportManager) {
        this.transportManager = transportManager;
        this.scheduler = Executors.newScheduledThreadPool(1);
        startTransportSpawning();
    }

    private void startTransportSpawning() {
        scheduler.scheduleAtFixedRate(this::spawnRandomTransport, 0, 100, TimeUnit.MILLISECONDS);
    }

    public Region[] getRandomRegions() {
        List<Region> regions = new ArrayList<>(Region.getRegionExtent().values());
        if (regions.size() < 2) return null;
        Collections.shuffle(regions);
        Region startRegion = regions.get(0);
        Region endRegion = regions.get(1);

        return new Region[]{startRegion, endRegion};
    }

    public void spawnRandomTransport() {
        Region[] randomRegions = getRandomRegions();

        if (randomRegions == null) return;

        Region startRegion = randomRegions[0];
        Region endRegion = randomRegions[1];

        TransportType selectedType = getValidTransportType(startRegion, endRegion);
        if (selectedType == null) return;

        transportManager.spawnTransport(selectedType, startRegion.getRegionPoint(), endRegion.getRegionPoint());
    }

    private TransportType getValidTransportType(Region startRegion, Region endRegion) {
        List<TransportType> types = new ArrayList<>(List.of(TransportType.values()));
        Collections.shuffle(types);

        for (TransportType type : types) {
            if (startRegion.supportsTransport(type) && endRegion.acceptsTransport(type)) {
                return type;
            }
        }
        return null;
    }

    public void stopTransportSpawning() {
        scheduler.shutdown();
    }
}
