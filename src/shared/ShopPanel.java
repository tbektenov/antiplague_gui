package shared;

import model.shop.Points;
import view.PointsView;
import view.UpgradePanel;

import javax.swing.*;
import java.awt.*;

public class ShopPanel extends JPanel {
    private final PointsView pointsView;
    private final UpgradePanel upgradePanel;
    private final Points pointsModel;

    public ShopPanel() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        pointsView = new PointsView();
        upgradePanel = new UpgradePanel();
        pointsModel = new Points();

        add(pointsView, BorderLayout.NORTH);
        add(upgradePanel, BorderLayout.CENTER);
    }

    public PointsView getPointsView() {
        return this.pointsView;
    }

    public UpgradePanel getUpgradePanel() {
        return this.upgradePanel;
    }

    public Points getPointsModel() {
        return this.pointsModel;
    }
}
