package model.countries;

import model.Country;

public class MiddleEast
        extends Country {

    private static MiddleEast instance;

    private MiddleEast(String name, int population) {
        super(name, population);
    }

    public static MiddleEast getInstance() {
        if (instance == null) {
            instance = new MiddleEast("Middle East", 381_000_000);
        }
        return instance;
    }
}
