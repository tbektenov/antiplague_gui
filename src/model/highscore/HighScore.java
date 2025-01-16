package model.highscore;

import model.difficulty.Difficulty;
import model.region.regions.Region;
import model.shop.upgrades.Upgrade;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

public class HighScore
        implements Serializable {

    private String playerName;
    private int score;

    public HighScore(String playerName, int score) {
        this.playerName = playerName;
        this.score = score;
    }

    public static int calculateScore(Difficulty difficulty, int timePassed) {
        double timeFactor = (1.0 / (1 + timePassed)) + 1;
        return (int) (Upgrade.getTotalUpgradesAcquired()
                * difficulty.calculateScoreMultiplier()
                * timeFactor);
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public static void saveHighScoreToFile(HighScore highScore, String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(highScore.toString());
            writer.newLine();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error saving high score: " + e,
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public String toString() {
        return playerName + " - " + score;
    }
}
