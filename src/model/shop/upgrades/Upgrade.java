package model.shop.upgrades;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract
    class Upgrade {

    private final static Set<Upgrade> upgradeExtent = new HashSet<>();

    private final String name;
    private final String description;
    private final int cost;
    private boolean isAvailable = true;

    public Upgrade(String name, String description, int cost) {
        this.name = name;
        this.description = description;
        this.cost = cost;

        upgradeExtent.add(this);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getCost() {
        return cost;
    }

    public abstract void apply();

    public boolean isAvailable() {
        return this.isAvailable;
    }

    public static Set<Upgrade> getUpgradeExtent() {
        return Collections.unmodifiableSet(upgradeExtent);
    }
}
