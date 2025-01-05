package controller;

import model.timer.TimerModel;
import shared.StatsPanel;
import view.TimerView;

import javax.swing.*;

public class TimerController {
    private final TimerModel timerModel;
    private final TimerView timerView;

    public TimerController(StatsPanel statsPanel) {
        this.timerView = statsPanel.getTimerView();

        this.timerModel = new TimerModel(120,
                this::updateTimerView,
                this::gameOver);

        timerModel.start();
    }

    private void updateTimerView(int timeRemaining) {
        int minutes = timeRemaining / 60;
        int seconds = timeRemaining % 60;

        SwingUtilities.invokeLater(() -> timerView.updateTimer(minutes, seconds));
    }

    private void gameOver() {
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
    }
}
