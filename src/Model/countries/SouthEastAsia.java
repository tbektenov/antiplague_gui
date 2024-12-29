package Model.countries;

public class SouthEastAsia
        extends Country {

    private static SouthEastAsia instance;

    private SouthEastAsia(String name, int population) {
        super(name, population);
    }

    public static SouthEastAsia getInstance() {
        if (instance == null) {
            instance = new SouthEastAsia("South-East Asia", 697_548_000);
        }
        return instance;
    }
}
