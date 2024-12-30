package controller;

import model.country.Country;
import model.country.CountryPoint;

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
        new Country("Africa", new Color(0, 255, 155), 1_300_000_000, new CountryPoint(0.4, 0.7));
        new Country("Oceania", new Color(0, 133, 255), 46_344_000, new CountryPoint(0.9, 0.9));
        new Country("Central Asia", new Color(244, 255, 0), 82_893_000, new CountryPoint(0.67, 0.45));
        new Country("East Asia", new Color(255, 122, 213), 1_654_000_000, new CountryPoint(0.85, 0.45));
        new Country("Europe", new Color(200, 0, 255), 742_300_000, new CountryPoint(0.43, 0.3));
        new Country("Middle East", new Color(255, 133, 0), 381_000_000, new CountryPoint(0.5, 0.4));
        new Country("North America", new Color(0, 0, 255), 592_000_000, new CountryPoint(0.2, 0.27));
        new Country("North Asia", new Color(255, 0, 0), 36_800_000, new CountryPoint(0.6, 0.3));
        new Country("South America", new Color(0, 255, 0), 442_000_000, new CountryPoint(0.2, 0.7));
        new Country("South Asia", new Color(0, 244, 255), 2_040_000_000, new CountryPoint(0.7, 0.6));
        new Country("South East Asia", new Color(200, 255, 0), 697_548_000, new CountryPoint(0.85, 0.6));
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
