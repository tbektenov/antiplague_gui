package Model.countries;

import Model.Country;

public class CentralAsia
        extends Country {

    private static CentralAsia instance;

    protected CentralAsia(String name, int population) {
        super(name, population);
    }

    public static CentralAsia getInstance() {
        if (instance == null) {
            instance = new CentralAsia("Central Asia", 82_893_000);
        }
        return instance;
    }
}
