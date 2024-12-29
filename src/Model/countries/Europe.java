package Model.countries;

public class Europe
        extends Country {

    private static Europe instance;

    private Europe(String name, int population) {
        super(name, population);
    }

    public static Europe getInstance() {
        if (instance == null) {
            instance = new Europe("Europe", 742_300_000);
        }
        return instance;
    }
}
