package controller;

import model.country.Region;
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
        scheduler.scheduleAtFixedRate(this::spawnRandomTransport, 2, 5, TimeUnit.SECONDS);
    }

    public Region[] getRandomRegions() {
        List<Region> regions = new ArrayList<>(Region.getRegionExtent().values());
        Collections.shuffle(regions);
        Region startRegion = regions.get(0);
        Region endRegion = regions.get(1);

        return new Region[] { startRegion, endRegion };
    }

    public void spawnRandomTransport() {
        Region[] randomRegions = getRandomRegions();
        Region startRegion = randomRegions[0];
        Region endRegion = randomRegions[1];

        TransportType selectedType = getValidTransportType(startRegion, endRegion);

        if (selectedType != null) {
            transportManager.spawnTransport(selectedType, startRegion.getRegionPoint(), endRegion.getRegionPoint());
        }
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
