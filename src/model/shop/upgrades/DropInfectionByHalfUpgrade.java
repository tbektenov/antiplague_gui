package model.shop.upgrades;

import model.region.regions.Region;
import shared.StatsPanel;

import javax.swing.*;
import java.util.Optional;

public class DropInfectionByHalfUpgrade
        extends Upgrade {

    private final StatsPanel statsPanel;

    public DropInfectionByHalfUpgrade(StatsPanel statsPanel) {
        super("Reduce Infection by 50%", "Drops infection by 50% in a selected region.", 500);
        this.statsPanel = statsPanel;
    }

    @Override
    public void apply() {
        Optional<Region> selectedRegion = UpgradeUtils.showAllRegionSelectionDialog();

        selectedRegion.ifPresent(region -> {
            if (UpgradeUtils.spendPoints(getCost())) {
                region.getVirus().decreaseInfection(region.getVirus().getInfectionLevel() / 2);
                statsPanel.setSelectedRegion(region);
                JOptionPane.showMessageDialog(null,
                        region.getName() + "'s infection reduced by 50%.",
                        "Upgrade Acquired",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
}
