package controller;

import model.shop.Points;
import model.shop.Upgrade;
import shared.ShopPanel;
import view.UpgradePanel;

import javax.swing.*;

public class UpgradeController {
    private static UpgradeController instance;
    private final Points pointsModel;
    private final UpgradePanel upgradePanel;

    private UpgradeController(ShopPanel shopPanel) {
        this.pointsModel = shopPanel.getPointsModel();
        this.upgradePanel = shopPanel.getUpgradePanel();
    }

    public static UpgradeController getInstance(ShopPanel shopPanel) {
        if (instance == null) {
            instance = new UpgradeController(shopPanel);
        }
        return instance;
    }

    public void initializeUpgrades() {

        for (int i = 1; i <= 9; i++) {
            String upgradeName = "Upgrade " + i;
            int cost = 10 * i;

            Upgrade upgrade = new Upgrade(upgradeName,
                    "Description for " + upgradeName,
                    cost,
                    () -> acquireUpgrade(upgradeName, cost));

            upgradePanel.addUpgrade(upgrade);
        }
    }

    private void acquireUpgrade(String upgradeName, int cost) {
        int currentPoints = pointsModel.getAmount();
        if ((currentPoints - cost) >= 0) {
            pointsModel.decreasePoints(cost);

            JOptionPane.showMessageDialog(null,
                    upgradeName + " was acquired!",
                    "Upgrade Acquired",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null,
                    "Not enough points to acquire " + upgradeName,
                    "Insufficient Points",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

}
