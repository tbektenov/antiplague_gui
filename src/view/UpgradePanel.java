package view;

import model.shop.Upgrade;

import javax.swing.*;
import java.awt.*;

public class UpgradePanel extends JPanel {
    public UpgradePanel() {
        setBackground(Color.WHITE);
        setLayout(new GridLayout(3, 3, 10, 10));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    public void addUpgrade(Upgrade upgrade) {
        UpgradeView upgradeView = new UpgradeView(upgrade);
        add(upgradeView);
        revalidate();
        repaint();
    }
}
