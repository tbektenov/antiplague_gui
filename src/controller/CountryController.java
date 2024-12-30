package controller;

import model.Country;

import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountryController {
    private static CountryController instance;
    private final ExecutorService executorService;

    private CountryController() {
        initializeCountries();
        executorService = Executors.newFixedThreadPool(Country.getCountryExtent().size());
    }

    private void initializeCountries() {
        new Country("Africa", new Color(0, 255, 155), 1_300_000_000);
        new Country("Oceania", new Color(0, 133, 255), 43_000_000);
        new Country("Central Asia", new Color(244, 255, 0), 74_000_000);
        new Country("East Asia", new Color(255, 122, 213), 1_600_000_000);
        new Country("Europe", new Color(200, 0, 255), 750_000_000);
        new Country("Middle East", new Color(255, 133, 0), 460_000_000);
        new Country("North America", new Color(0, 0, 255), 590_000_000);
        new Country("North Asia", new Color(255, 0, 0), 230_000_000);
        new Country("South America", new Color(0, 255, 0), 430_000_000);
        new Country("South Asia", new Color(0, 244, 255), 1_800_000_000);
        new Country("South East Asia", new Color(200, 255, 0), 680_000_000);
    }

    public void startInfections() {
        Country.getCountryExtent().values().forEach(Country::startInfection);
    }

    public void stopInfections() {
        executorService.shutdownNow();
        Country.getCountryExtent().values().forEach(Country::stopInfection);
    }

    public boolean containsColor(Color color) {
        return Country.containsColor(color);
    }

    public Country getCountryByColor(Color color) {
        return Country.getCountryByColor(color);
    }

    public void decreaseCountryPopulation(Country country, int subtrahend) {
        country.decreasePopulation(subtrahend);
    }

    public static CountryController getInstance() {
        if (instance == null) {
            instance = new CountryController();
        }
        return instance;
    }
}
