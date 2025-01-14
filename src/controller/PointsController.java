package controller;

import model.shop.Points;
import shared.ShopPanel;
import view.PointsView;

import java.util.Timer;
import java.util.TimerTask;

public class PointsController {
    private final Points model;
    private final PointsView view;
    private final Timer controllerTimer;

    public PointsController(ShopPanel shopPanel) {
        this.model = shopPanel.getPointsModel();
        this.view = shopPanel.getPointsView();
        this.controllerTimer = new Timer(true);

        model.reset();
        model.start();
        startControllerTimer();
    }

    private void startControllerTimer() {
        controllerTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updatePointsView();
            }
        }, 3000, 1000);
    }

    public void updatePointsView() {
        int points = model.getAmount();
        view.updatePoints(points);
    }
}
