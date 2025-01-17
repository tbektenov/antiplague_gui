package model.shop.upgrades;

import model.region.regions.Region;
import shared.StatsPanel;

import javax.swing.*;
import java.util.Optional;

public class IncreaseCureEfficiencyUpgrade
        extends Upgrade {

    private final StatsPanel statsPanel;

    public IncreaseCureEfficiencyUpgrade(StatsPanel statsPanel) {
        super("Improve Cure", "Increases cure's efficiency by 5% in a selected region.", 200);
        this.statsPanel = statsPanel;
    }

    @Override
    public void apply() {
        Optional<Region> selectedRegion = UpgradeUtils.showFilteredRegionSelectionDialog(
                region -> region.getCureEfficiency() != 0
        );

        selectedRegion.ifPresent(region -> {
            if (UpgradeUtils.spendPoints(getCost())) {
                region.getCure().increaseCureEfficiency(.05f);
                statsPanel.setSelectedRegion(region);
                JOptionPane.showMessageDialog(null,
                        "Cure efficiency in " + region.getName() + " was increased by 5%.",
                        "Upgrade Acquired",
                        JOptionPane.INFORMATION_MESSAGE);
                incrementUpgradeCounter();
            }
        });
    }
}
