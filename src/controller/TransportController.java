package controller;

import model.country.Country;
import model.country.CountryPoint;
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

    private void spawnRandomTransport() {
        List<Country> countries = new ArrayList<>(Country.getCountryExtent().values());

        if (countries.size() < 2) {
            System.out.println("Not enough countries to spawn transport.");
            return;
        }

        Collections.shuffle(countries);
        Country startCountry = countries.get(0);
        Country endCountry = countries.get(1);

        spawnTransportBetween(startCountry, endCountry);
    }

    private void spawnTransportBetween(Country start, Country end) {
        CountryPoint startPoint = start.getCountryPoint();
        CountryPoint endPoint = end.getCountryPoint();

        TransportType randomType = getRandomTransportType();
        transportManager.spawnTransport(randomType, startPoint, endPoint);

        System.out.printf("Transport (%s) spawned between %s and %s%n",
                randomType, start.getName(), end.getName());
    }

    private TransportType getRandomTransportType() {
        TransportType[] types = TransportType.values();
        return types[(int) (Math.random() * types.length)];
    }

    public void stopTransportSpawning() {
        scheduler.shutdown();
    }
}
