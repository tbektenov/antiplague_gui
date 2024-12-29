package model.countries;

import model.Country;

public class SouthAmerica
        extends Country {

    private static SouthAmerica instance;

    private SouthAmerica(String name, int population) {
        super(name, population);
    }

    public static SouthAmerica getInstance() {
        if (instance == null) {
            instance = new SouthAmerica("South America", 442_000_000);
        }
        return instance;
    }
}
