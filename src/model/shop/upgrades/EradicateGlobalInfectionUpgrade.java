package model.shop.upgrades;

import model.region.regions.Region;

import javax.swing.*;

public class EradicateGlobalInfectionUpgrade
        extends Upgrade {

    public EradicateGlobalInfectionUpgrade() {
        super("Eradicate Global Infection", "Sets global infection levels to 0.", 1000);
    }

    @Override
    public void apply() {
        if (UpgradeUtils.spendPoints(getCost())) {
            Region.getRegionExtent().values()
                    .forEach(region -> region.getVirus().decreaseInfection(region.getVirus().getInfectionLevel()));
            JOptionPane.showMessageDialog(null,
                    "Global infection level reduced to 0!",
                    "Upgrade Acquired",
                    JOptionPane.INFORMATION_MESSAGE);
            incrementUpgradeCounter();
        }
    }
}
