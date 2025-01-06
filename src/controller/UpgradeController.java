package controller;

import model.shop.Points;
import model.shop.Upgrade;
import shared.ShopPanel;

import javax.swing.*;

public class UpgradeController {
    private static UpgradeController instance;
    private Points pointsModel;

    private UpgradeController() {
    }

    public static UpgradeController getInstance() {
        if (instance == null) {
            instance = new UpgradeController();
        }
        return instance;
    }

    public void initializeUpgrades(ShopPanel shopPanel) {
        this.pointsModel = shopPanel.getPointsModel();

        for (int i = 1; i <= 9; i++) {
            String upgradeName = "Upgrade " + i;
            int cost = 10 * i;

            Upgrade upgrade = new Upgrade(upgradeName,
                    "Description for " + upgradeName,
                    cost,
                    () -> acquireUpgrade(upgradeName, cost));

            shopPanel.getUpgradePanel().addUpgrade(upgrade);
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
