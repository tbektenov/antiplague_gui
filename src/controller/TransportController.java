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
        scheduler.scheduleAtFixedRate(this::spawnTransportBasedOnRules, 0, 100, TimeUnit.MILLISECONDS);
    }

    public void spawnTransportBasedOnRules() {
        Region[] validRegions = getValidRegionPair();
        if (validRegions == null) return;

        Region startRegion = validRegions[0];
        Region endRegion = validRegions[1];

        TransportType validType = getRandomValidTransportType(startRegion, endRegion);
        if (validType != null) {
            transportManager.spawnTransport(validType, startRegion.getRegionPoint(), endRegion.getRegionPoint());
        }
    }

    private Region[] getValidRegionPair() {
        List<Region> regions = new ArrayList<>(Region.getRegionExtent().values());
        Collections.shuffle(regions);

        for (Region startRegion : regions) {
            for (Region endRegion : regions) {
                if (!startRegion.equals(endRegion) && hasValidTransport(startRegion, endRegion)) {
                    return new Region[]{startRegion, endRegion};
                }
            }
        }
        return null;
    }

    private boolean hasValidTransport(Region startRegion, Region endRegion) {
        for (TransportType type : TransportType.values()) {
            if (startRegion.supportsTransport(type) && endRegion.acceptsTransport(type, startRegion.getName())) {
                return true;
            }
        }
        return false;
    }

    private TransportType getRandomValidTransportType(Region startRegion, Region endRegion) {
        List<TransportType> validTransportTypes = new ArrayList<>();

        for (TransportType type : TransportType.values()) {
            if (startRegion.supportsTransport(type) && endRegion.acceptsTransport(type, startRegion.getName())) {
                validTransportTypes.add(type);
            }
        }

        if (!validTransportTypes.isEmpty()) {
            Collections.shuffle(validTransportTypes);
            return validTransportTypes.getFirst();
        }

        return null;
    }

    public void stopTransportSpawning() {
        scheduler.shutdown();
    }
}
