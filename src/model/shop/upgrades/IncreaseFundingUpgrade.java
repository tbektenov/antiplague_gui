package model.shop.upgrades;

import model.shop.Points;

import javax.swing.*;

public class IncreaseFundingUpgrade
        extends Upgrade {

    public IncreaseFundingUpgrade() {
        super("Increase funding", "World leaders approve funding increase.", 10_000);
    }

    @Override
    public void apply() {
        if (UpgradeUtils.spendPoints(getCost())) {
            Points.getInstance().setMultiplier(1.5);
            JOptionPane.showMessageDialog(null,
                    "Funding has been increased!",
                    "Upgrade Acquired",
                    JOptionPane.INFORMATION_MESSAGE);
            incrementUpgradeCounter();
        }
    }
}
