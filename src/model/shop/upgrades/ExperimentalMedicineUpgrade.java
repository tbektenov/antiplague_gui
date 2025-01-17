package model.shop.upgrades;

import model.region.regions.Region;
import shared.StatsPanel;

import javax.swing.*;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class ExperimentalMedicineUpgrade
        extends Upgrade {

    private final StatsPanel statsPanel;

    public ExperimentalMedicineUpgrade(StatsPanel statsPanel) {
        super(
                "Utilize experimental medicine.",
                "Experimental medicine decreases both: infection speed and cure efficiency.",
                800);

        this.statsPanel = statsPanel;
    }

    @Override
    public void apply() {
        Optional<Region> selectedRegion = UpgradeUtils.showAllRegionSelectionDialog();

        selectedRegion.ifPresent(region -> {
            if (UpgradeUtils.spendPoints(getCost())) {
                region.getVirus().decreaseInfection(ThreadLocalRandom.current().nextFloat(.11f));
                region.getCure().decreaseCureEfficiency(ThreadLocalRandom.current().nextFloat(.06f));
                statsPanel.setSelectedRegion(region);
                JOptionPane.showMessageDialog(null,
                        region.getName() + " has utilized experimental medicine.",
                        "Upgrade Acquired",
                        JOptionPane.INFORMATION_MESSAGE);
                incrementUpgradeCounter();
            }
        });
    }
}
