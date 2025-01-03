package model.shop;

public class Upgrade {
    private final String name;
    private final String description;
    private final int cost;
    private final Runnable effect;

    public Upgrade(String name, String description, int cost, Runnable effect) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.effect = effect;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getCost() { return cost; }
    public void apply() { effect.run(); }
}
