package controller;

import model.highscore.HighScore;
import model.timer.TimerModel;
import shared.MainMenuFrame;
import shared.StatsPanel;
import view.TimerView;

import javax.swing.*;
import java.io.IOException;
import java.util.Random;

public class TimerController {
    private final TimerModel timerModel;
    private final TimerView timerView;

    public TimerController(StatsPanel statsPanel) {
        this.timerView = statsPanel.getTimerView();
        this.timerModel = new TimerModel(this::updateTimerView, this::gameOver);

        startTimer();
    }

    private void startTimer() {
        new Thread(timerModel).start();
    }

    private void updateTimerView() {
        int timeRemaining = timerModel.getTimePassed();
        int minutes = timeRemaining / 60;
        int seconds = timeRemaining % 60;

        SwingUtilities.invokeLater(() -> timerView.updateTimer(minutes, seconds));
    }

    private void gameOver() {
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
                    HighScore.saveHighScoreToFile(highScore, "./src/data/highscores.txt");
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
                new MainMenuFrame();
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
    }
}
