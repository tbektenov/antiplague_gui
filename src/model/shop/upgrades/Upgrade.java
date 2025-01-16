package model.shop.upgrades;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Upgrade {
    private final static Set<Upgrade> upgradeExtent = new HashSet<>();

    private final String name;
    private final String description;
    private final int cost;
    private final Runnable effect;

    public Upgrade(String name, String description, int cost, Runnable effect) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.effect = effect;

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

    public void apply() {
        effect.run();
    }

    public static Set<Upgrade> getUpgradeExtent() {
        return Collections.unmodifiableSet(upgradeExtent);
    }
}
