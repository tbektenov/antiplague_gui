package shared;

import view.PointsView;
import view.UpgradePanel;

import javax.swing.*;
import java.awt.*;

public class ShopPanel extends JPanel {

    private PointsView pointsView;
    private UpgradePanel upgradePanel;

    public ShopPanel() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        pointsView = new PointsView();
        upgradePanel = new UpgradePanel();

        add(pointsView, BorderLayout.NORTH);
        add(upgradePanel, BorderLayout.SOUTH);
    }

    public PointsView getPointsView() {
        return this.pointsView;
    }

    public UpgradePanel getUpgradePanel() {
        return this.upgradePanel;
    }
}