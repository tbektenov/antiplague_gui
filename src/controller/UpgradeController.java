package controller;

import model.shop.Upgrade;

public class UpgradeController {
    private static UpgradeController instance;

    private UpgradeController() {

    }

    public static UpgradeController getInstance() {
        if (instance == null) {
            instance = new UpgradeController();
        }
        return instance;
    }

    public void initializeUpgrades() {

    }
}
