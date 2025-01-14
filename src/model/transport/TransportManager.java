package model.transport;

import model.region.common.RegionPoint;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TransportManager {
    private final List<TransportThread> activeTransports = new ArrayList<>();
    private final ExecutorService executor = Executors.newFixedThreadPool(100);
    private final JPanel panel;
    private float spawnRateMultiplier = 1.0f;

    public TransportManager(JPanel panel) {
        this.panel = panel;
    }

    public TransportManager(JPanel panel, float spawnRateMultiplier) {
        this.panel = panel;
        this.spawnRateMultiplier = spawnRateMultiplier;
    }

    public void spawnTransport(TransportType type, RegionPoint start, RegionPoint end) {
        Route route = new Route(start, end);
        Transport transport = new Transport(type, route);
        TransportThread thread = new TransportThread(transport, panel, this);

        synchronized (activeTransports) {
            activeTransports.add(thread);
        }
        executor.submit(thread);
    }

    public List<TransportThread> getActiveTransports() {
        synchronized (activeTransports) {
            return new ArrayList<>(activeTransports);
        }
    }

    public synchronized void removeTransport(TransportThread transportThread) {
        synchronized (activeTransports) {
            activeTransports.remove(transportThread);
        }
        panel.repaint();
    }

    public void stopAll() {
        synchronized (activeTransports) {
            for (TransportThread thread : activeTransports) {
                thread.stopTransport();
            }
        }
        executor.shutdown();
    }

    public void setSpawnRateMultiplier(float multiplier) {
        this.spawnRateMultiplier = multiplier;
    }
    public float getSpawnRateMultiplier() {
        return spawnRateMultiplier;
    }
}
