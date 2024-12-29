package Model.countries;

public class Oceania
    extends Country{

    private static Oceania instance;

    protected Oceania(String name, int population) {
        super(name, population);
    }

    public static Oceania getInstance() {
        if (instance == null) {
            instance = new Oceania("Oceania", 46_344_000);
        }
        return instance;
    }
}
