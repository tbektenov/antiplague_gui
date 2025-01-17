package controller;

import model.shop.upgrades.*;
import shared.ShopPanel;
import shared.StatsPanel;
import view.UpgradePanel;

public class UpgradeController {
    private final UpgradePanel upgradePanel;
    private final StatsPanel statsPanel;

    public UpgradeController(ShopPanel shopPanel, StatsPanel statsPanel) {
        this.upgradePanel = shopPanel.getUpgradePanel();
        this.statsPanel = statsPanel;

        initializeUpgrades();
    }

    private void initializeUpgrades() {
        upgradePanel.addUpgrade(new StartCureProgramUpgrade(statsPanel));
        upgradePanel.addUpgrade(new ReduceRegionalInfectionUpgrade(statsPanel, 15f));
        upgradePanel.addUpgrade(new IncreaseCureEfficiencyUpgrade(statsPanel));
        upgradePanel.addUpgrade(new EradicateGlobalInfectionUpgrade());
        upgradePanel.addUpgrade(new CloseAllBordersUpgrade(statsPanel));
        upgradePanel.addUpgrade(new CloseSpecificTransportBordersUpgrade(statsPanel));
        upgradePanel.addUpgrade(new DropInfectionByHalfUpgrade(statsPanel));
        upgradePanel.addUpgrade(new StopInfectionSpreadUpgrade(statsPanel));
        upgradePanel.addUpgrade(new DestroyVirusUpgrade());
    }
}
