package model.shop.upgrades;

import model.region.regions.Region;
import shared.StatsPanel;
import javax.swing.*;
import java.util.Optional;

public class ReduceRegionalInfectionUpgrade
        extends Upgrade {

    private final StatsPanel statsPanel;
    private final float subtrahend;

    public ReduceRegionalInfectionUpgrade(StatsPanel statsPanel, float subtrahend) {
        super("Reduce Regional Infection Speed",
                "Reduces infection speed by " + subtrahend + "% in a selected region.",
                150);
        this.statsPanel = statsPanel;
        this.subtrahend = subtrahend;
    }

    @Override
    public void apply() {
        Optional<Region> selectedRegion = UpgradeUtils.showAllRegionSelectionDialog();

        selectedRegion.ifPresent(region -> {
            if (UpgradeUtils.spendPoints(getCost())) {
                region.getVirus().decreaseInfection(subtrahend);
                statsPanel.setSelectedRegion(region);
                JOptionPane.showMessageDialog(null,
                        region.getName() + "'s infection reduced by " + subtrahend + "%.",
                        "Upgrade Acquired",
                        JOptionPane.INFORMATION_MESSAGE);
                incrementUpgradeCounter();
            }
        });
    }
}
