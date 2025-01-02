package model.transport;

import model.country.CountryPoint;

public class Route {
    private final CountryPoint startCountry;
    private final CountryPoint endCountry;

    public Route(CountryPoint startCountry, CountryPoint endCountry) {
        this.startCountry = startCountry;
        this.endCountry = endCountry;
    }

    public CountryPoint getStartCountry() {
        return startCountry;
    }

    public CountryPoint getEndCountry() {
        return endCountry;
    }
}
