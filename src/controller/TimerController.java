package controller;

import model.timer.TimerModel;
import shared.StatsPanel;
import view.TimerView;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class TimerController {
    private final TimerModel timerModel;
    private final TimerView timerView;
    private final Timer controllerTimer;

    public TimerController(StatsPanel statsPanel) {
        this.timerView = statsPanel.getTimerView();
        this.timerModel = new TimerModel(120);
        this.controllerTimer = new Timer(true);

        timerModel.start(this::gameOver);
        startControllerTimer();
    }

    private void startControllerTimer() {
        controllerTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateTimerView();
            }
        }, 0, 1000);
    }

    private void updateTimerView() {
        int timeRemaining = timerModel.getTimeRemaining();
        int minutes = timeRemaining / 60;
        int seconds = timeRemaining % 60;

        SwingUtilities.invokeLater(() -> timerView.updateTimer(minutes, seconds));
    }

    private void gameOver() {
        controllerTimer.cancel();
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(null,
                    "Game Over! Time's up!",
                    "Game Over",
                    JOptionPane.INFORMATION_MESSAGE);

            System.exit(0);
        });
    }

    public void stopTimer() {
        timerModel.stop();
        controllerTimer.cancel();
    }
}
