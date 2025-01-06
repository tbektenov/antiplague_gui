package view;

import model.shop.Upgrade;

import javax.swing.*;

public class UpgradeView extends JButton {

    public UpgradeView(Upgrade upgrade) {
        setText(upgrade.getName() + " - " + upgrade.getCost() + " points");
        addActionListener(e -> upgrade.apply());
    }
}
