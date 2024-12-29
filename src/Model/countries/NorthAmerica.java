package Model.countries;

public class NorthAmerica
        extends Country{

    private static NorthAmerica instance;

    private NorthAmerica(String name, int population) {
        super(name, population);
    }

    public static NorthAmerica getInstance(){
        if (instance == null) {
            instance = new NorthAmerica("North America", 592_000_000);
        }
        return instance;
    }
}
