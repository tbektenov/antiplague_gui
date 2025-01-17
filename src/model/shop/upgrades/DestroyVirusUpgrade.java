package model.shop.upgrades;

import controller.TimerController;
import model.region.regions.Region;

import javax.swing.*;

public class DestroyVirusUpgrade
        extends Upgrade {

    private final TimerController timerController;

    public DestroyVirusUpgrade(TimerController timerController) {
        super("Create Antidote", "Erases the virus from the world.", 50_000);
        this.timerController = timerController;
    }

    @Override
    public void apply() {
        if (UpgradeUtils.spendPoints(getCost())) {
            JOptionPane.showMessageDialog(null,
                    "Virus has been destroyed!",
                    "Upgrade Acquired",
                    JOptionPane.INFORMATION_MESSAGE);
            incrementUpgradeCounter();
            timerController.gameOver();
        }
    }
}
