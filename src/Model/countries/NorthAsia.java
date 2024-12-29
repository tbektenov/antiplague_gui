package Model.countries;

public class NorthAsia
        extends Country {

    private static NorthAsia instance;

    private NorthAsia(String name, int population) {
        super(name, population);
    }

    public static NorthAsia getInstance() {
        if (instance == null) {
            instance = new NorthAsia("North Asia", 36_800_000);
        }
        return instance;
    }
}
