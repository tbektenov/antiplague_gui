package model.shop.upgrades;

import model.region.regions.Region;

import javax.swing.*;

public class DestroyVirusUpgrade
        extends Upgrade {

    public DestroyVirusUpgrade() {
        super("Create Antidote", "Erases the virus from the world.", 50000);
    }

    @Override
    public void apply() {
        if (UpgradeUtils.spendPoints(getCost())) {
            Region.getRegionExtent().values().forEach(region -> {
                region.getVirus().setInfectionLevel(0f);
                region.getVirus().stop();
                region.getCure().setCureEfficiency(1f);
            });
            JOptionPane.showMessageDialog(null,
                    "Virus has been destroyed!",
                    "Upgrade Acquired",
                    JOptionPane.INFORMATION_MESSAGE);
            incrementUpgradeCounter();
        }
    }
}
