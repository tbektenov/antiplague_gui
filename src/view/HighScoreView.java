package view;

import model.highscore.HighScore;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HighScoreView
        extends JFrame {

    public HighScoreView() {
        setTitle("High Scores");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        JPanel panel = createMainPanel();
        add(panel, BorderLayout.CENTER);

        List<HighScore> highScores = loadHighScoresFromFile("./src/data/highscores.txt");
        sortHighScores(highScores);

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (HighScore highScore : highScores) {
            listModel.addElement(highScore.toString());
        }

        if (listModel.isEmpty()) listModel.addElement("No high scores available.");

        JList<String> highScoresList = new JList<>(listModel);
        highScoresList.setFont(new Font("Arial", Font.PLAIN, 18));
        highScoresList.setBackground(Color.WHITE);
        highScoresList.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JScrollPane scrollPane = new JScrollPane(highScoresList);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        panel.setLayout(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
    }

    private List<HighScore> loadHighScoresFromFile(String fileName) {
        List<HighScore> highScores = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" - ");
                if (parts.length == 2) {
                    String playerName = parts[0].trim();
                    int score = Integer.parseInt(parts[1].trim());
                    highScores.add(new HighScore(playerName, score));
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error reading high scores: " + e,
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        return highScores;
    }

    private JPanel createMainPanel() {
        JPanel panel = new JPanel();

        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setVisible(true);
        panel.setOpaque(true);

        return panel;
    }

    private void sortHighScores(List<HighScore> highScores) {
        highScores.sort(Comparator.comparingInt(HighScore::getScore).reversed());
    }
}
