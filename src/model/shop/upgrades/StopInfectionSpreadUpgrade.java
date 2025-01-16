package model.shop.upgrades;

import model.region.regions.Region;
import shared.StatsPanel;

import javax.swing.*;
import java.util.Optional;

public class StopInfectionSpreadUpgrade
        extends Upgrade {

    private final StatsPanel statsPanel;

    public StopInfectionSpreadUpgrade(StatsPanel statsPanel) {
        super("Stop Infection Spread", "Stops infection spread and sets infection to 0 in a selected region.", 800);
        this.statsPanel = statsPanel;
    }

    @Override
    public void apply() {
        Optional<Region> selectedRegion = UpgradeUtils.showAllRegionSelectionDialog();

        selectedRegion.ifPresent(region -> {
            if (UpgradeUtils.spendPoints(getCost())) {
                region.getVirus().stop();
                region.getVirus().decreaseInfection(region.getVirus().getInfectionLevel());
                statsPanel.setSelectedRegion(region);
                JOptionPane.showMessageDialog(null,
                        region.getName() + "'s infection stopped and set to 0.",
                        "Upgrade Acquired",
                        JOptionPane.INFORMATION_MESSAGE);
                incrementUpgradeCounter();
            }
        });
    }
}
