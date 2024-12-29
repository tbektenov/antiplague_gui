package controller;

import model.Country;
import model.countries.*;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountryController {
    private static CountryController instance;
    private final Map<Color, Country> countryMap = new HashMap<>();
    private final ExecutorService executorService;

    private CountryController() {
        initializeCountries();
        executorService = Executors.newFixedThreadPool(countryMap.size());
    }

    private void initializeCountries() {
        countryMap.put(new Color(0, 255, 155), Africa.getInstance());
        countryMap.put(new Color(0, 133, 255), Oceania.getInstance());
        countryMap.put(new Color(244, 255, 0), CentralAsia.getInstance());
        countryMap.put(new Color(255, 122, 213), EastAsia.getInstance());
        countryMap.put(new Color(200, 0, 255), Europe.getInstance());
        countryMap.put(new Color(255, 133, 0), MiddleEast.getInstance());
        countryMap.put(new Color(0, 0, 255), NorthAmerica.getInstance());
        countryMap.put(new Color(255, 0, 0), NorthAsia.getInstance());
        countryMap.put(new Color(0, 255, 0), SouthAmerica.getInstance());
        countryMap.put(new Color(0, 244, 255), SouthAsia.getInstance());
        countryMap.put(new Color(200, 255, 0), SouthEastAsia.getInstance());
    }

    public void startInfections() {
        for (Country country : countryMap.values()) {
            executorService.submit(country::startInfection);
        }
    }

    public void stopInfections() {
        executorService.shutdownNow();
        countryMap.values().forEach(Country::stopInfection);
    }

    public boolean containsColor(Color color) {
        return countryMap.containsKey(color);
    }

    public Country getCountryByColor(Color color) {
        return countryMap.get(color);
    }

    public static CountryController getInstance() {
        if (instance == null) {
            instance = new CountryController();
        }
        return instance;
    }
}
