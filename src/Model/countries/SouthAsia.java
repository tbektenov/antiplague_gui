package Model.countries;

public class SouthAsia
        extends Country {

    private static SouthAsia instance;

    private SouthAsia(String name, int population) {
        super(name, population);
    }

    public static SouthAsia getInstance() {
        if (instance == null) {
            instance = new SouthAsia("South Asia", 2_040_000_000);
        }
        return instance;
    }
}
