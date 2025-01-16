package view;

import model.shop.Upgrade;

import javax.swing.*;
import java.awt.*;

public class UpgradePanel extends JPanel {

    public UpgradePanel() {
        setLayout(new GridLayout(0, 9, 10, 10));
        setBackground(new Color(73, 46, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    public void addUpgrade(Upgrade upgrade) {
        UpgradeView upgradeView = new UpgradeView(upgrade);
        add(upgradeView);
        revalidate();
        repaint();
    }
}
