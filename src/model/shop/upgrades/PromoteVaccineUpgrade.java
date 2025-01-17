package model.shop.upgrades;

import model.region.regions.Region;
import shared.StatsPanel;

import javax.swing.*;
import java.util.Optional;

public class PromoteVaccineUpgrade
        extends Upgrade {

    private final StatsPanel statsPanel;

    public PromoteVaccineUpgrade(StatsPanel statsPanel) {
        super("Promote Vaccine", "Increases cure's efficiency drastically.", 5_000);
        this.statsPanel = statsPanel;
    }

    @Override
    public void apply() {
        Optional<Region> selectedRegion = UpgradeUtils.showFilteredRegionSelectionDialog(
                region -> region.getCureEfficiency() != 0
        );

        selectedRegion.ifPresent(region -> {
            if (UpgradeUtils.spendPoints(getCost())) {
                region.getCure().increaseCureEfficiency(.5f);
                statsPanel.setSelectedRegion(region);
                JOptionPane.showMessageDialog(null,
                        region.getName() + " has allocated a huge budget to study the virus and has promoted cure's efficiency.",
                        "Upgrade Acquired",
                        JOptionPane.INFORMATION_MESSAGE);
                incrementUpgradeCounter();
            }
        });
    }
}
