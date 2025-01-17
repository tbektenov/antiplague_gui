package controller;

import model.shop.upgrades.*;
import shared.ShopPanel;
import shared.StatsPanel;
import view.UpgradePanel;

public class UpgradeController {

    private final UpgradePanel upgradePanel;
    private final StatsPanel statsPanel;
    private final TimerController timerController;

    public UpgradeController(ShopPanel shopPanel, StatsPanel statsPanel, TimerController timerController) {
        this.upgradePanel = shopPanel.getUpgradePanel();
        this.statsPanel = statsPanel;
        this.timerController = timerController;

        initializeUpgrades();
    }

    private void initializeUpgrades() {
        upgradePanel.addUpgrade(new StartCureProgramUpgrade(statsPanel));
        upgradePanel.addUpgrade(new ReduceRegionalInfectionUpgrade(statsPanel, 3f));
        upgradePanel.addUpgrade(new IncreaseCureEfficiencyUpgrade(statsPanel, .05f));
        upgradePanel.addUpgrade(new IncreaseFundingUpgrade());
        upgradePanel.addUpgrade(new CloseAllBordersUpgrade(statsPanel));
        upgradePanel.addUpgrade(new CloseSpecificTransportBordersUpgrade(statsPanel));
        upgradePanel.addUpgrade(new PromoteVaccineUpgrade(statsPanel));
        upgradePanel.addUpgrade(new UtilizeTestingMedicineUpgrade(statsPanel));
        upgradePanel.addUpgrade(new DestroyVirusUpgrade(timerController));
    }
}
