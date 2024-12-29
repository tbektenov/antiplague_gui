package Model.countries;

import Model.Country;

public class EastAsia
        extends Country{

    private static EastAsia instance;

    protected EastAsia(String name, int population) {
        super(name, population);
    }

    public static EastAsia getInstance() {
        if (instance == null) {
            instance = new EastAsia("East Asia", 1_654_185_000);
        }
        return instance;
    }
}
