package model.shop.upgrades;

import model.region.regions.Region;
import shared.StatsPanel;
import javax.swing.*;
import java.util.Optional;

public class StartCureProgramUpgrade
        extends Upgrade {

    private final StatsPanel statsPanel;

    public StartCureProgramUpgrade(StatsPanel statsPanel) {
        super("Start Cure Program", "Selected region launches curing program.", 100);
        this.statsPanel = statsPanel;
    }

    @Override
    public void apply() {
        Optional<Region> selectedRegion = UpgradeUtils.showFilteredRegionSelectionDialog(
                region -> region.getCureEfficiency() == 0
        );

        selectedRegion.ifPresent(region -> {
            if (UpgradeUtils.spendPoints(getCost())) {
                region.getCure().startDevelopingCure();
                statsPanel.setSelectedRegion(region);
                JOptionPane.showMessageDialog(null,
                        region.getName() + " has started the cure program.",
                        "Upgrade Acquired",
                        JOptionPane.INFORMATION_MESSAGE);
                incrementUpgradeCounter();
            }
        });
    }
}
