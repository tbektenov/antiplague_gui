package Model.countries;

public class Africa
        extends Country {

    private static Africa instance;

    protected Africa(String name, int population) {
        super(name, population);
    }

    public static Africa getInstance() {
        if (instance == null) {
            instance = new Africa("Africa", 1_300_000_000);
        }
        return instance;
    }
}
