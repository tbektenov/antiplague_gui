package model.shop.upgrades;

import model.region.regions.Region;
import model.transport.TransportType;
import shared.StatsPanel;

import javax.swing.*;
import java.util.Optional;

public class CloseSpecificTransportBordersUpgrade
        extends Upgrade {

    private final StatsPanel statsPanel;

    public CloseSpecificTransportBordersUpgrade(StatsPanel statsPanel) {
        super("Close Specific Transport Borders", "Closes borders for a specific transport type in a selected region.", 150);
        this.statsPanel = statsPanel;
    }

    @Override
    public void apply() {
        Optional<Region> selectedRegion = UpgradeUtils.showAllRegionSelectionDialog();
        Optional<TransportType> selectedTransportType = UpgradeUtils.showTransportTypeSelectionDialog();

        if (selectedRegion.isPresent() && selectedTransportType.isPresent()) {
            if (UpgradeUtils.spendPoints(getCost())) {
                selectedRegion.get().removeAcceptedTransportType(selectedTransportType.get());
                statsPanel.setSelectedRegion(selectedRegion.get());
                JOptionPane.showMessageDialog(null,
                        selectedRegion.get().getName() + " banned " + selectedTransportType.get() + " transport.",
                        "Upgrade Acquired",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
