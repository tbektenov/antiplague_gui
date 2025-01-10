package controller;

import model.HighScore;
import model.timer.TimerModel;
import shared.StatsPanel;
import shared.MainMenuView;
import view.TimerView;

import javax.swing.*;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class TimerController {
    private final TimerModel timerModel;
    private final TimerView timerView;
    private final Timer controllerTimer;

    public TimerController(StatsPanel statsPanel) {
        this.timerView = statsPanel.getTimerView();
        this.timerModel = new TimerModel(10);
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
            Random random = new Random();
            int randomScore = random.nextInt(1000) + 1;

            String playerName = JOptionPane.showInputDialog(null,
                    "Game Over! Your score: " + randomScore + "\nEnter your name:",
                    "Game Over",
                    JOptionPane.QUESTION_MESSAGE);

            if (playerName != null && !playerName.trim().isEmpty()) {
                HighScore highScore = new HighScore(playerName, randomScore);
                try {
                    HighScore.saveHighScoreToFile(highScore, "highscores.txt");
                    JOptionPane.showMessageDialog(null,
                            "High score saved successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null,
                            "Failed to save high score!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }

            SwingUtilities.invokeLater(() -> {
                new MainMenuView();
                closeGameFrame();
            });
        });
    }

    private void closeGameFrame() {
        JFrame gameFrame = (JFrame) SwingUtilities.getWindowAncestor(timerView);
        if (gameFrame != null) {
            gameFrame.dispose();
        }
    }

    public void stopTimer() {
        timerModel.stop();
        controllerTimer.cancel();
    }
}
