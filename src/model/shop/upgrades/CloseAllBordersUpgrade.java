package model.shop.upgrades;

import model.region.regions.Region;
import shared.StatsPanel;

import javax.swing.*;
import java.util.Optional;

public class CloseAllBordersUpgrade
        extends Upgrade {

    private final StatsPanel statsPanel;

    public CloseAllBordersUpgrade(StatsPanel statsPanel) {
        super("Close All Borders", "Closes all borders for the selected region.", 300);
        this.statsPanel = statsPanel;
    }

    @Override
    public void apply() {
        Optional<Region> selectedRegion = UpgradeUtils.showAllRegionSelectionDialog();

        selectedRegion.ifPresent(region -> {
            if (UpgradeUtils.spendPoints(getCost())) {
                region.clearAcceptedTransport();
                statsPanel.setSelectedRegion(region);
                JOptionPane.showMessageDialog(null,
                        region.getName() + " closed all borders.",
                        "Upgrade Acquired",
                        JOptionPane.INFORMATION_MESSAGE);
                incrementUpgradeCounter();
            }
        });
    }
}
