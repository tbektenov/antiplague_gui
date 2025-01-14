package shared;

import model.difficulty.Difficulty;
import view.HighScoreView;

import javax.swing.*;
import java.awt.*;

public class MainMenuFrame extends JFrame {

    public MainMenuFrame() {
        super("AntiPlague Game - Main Menu");

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 1, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.LIGHT_GRAY);

        JButton newGameButton = new JButton("New Game");
        JButton highScoresButton = new JButton("High Scores");
        JButton exitButton = new JButton("Exit");

        Dimension buttonSize = new Dimension(200, 100);
        newGameButton.setPreferredSize(buttonSize);
        highScoresButton.setPreferredSize(buttonSize);
        exitButton.setPreferredSize(buttonSize);

        mainPanel.add(newGameButton);
        mainPanel.add(highScoresButton);
        mainPanel.add(exitButton);

        newGameButton.addActionListener(_ -> {
            Difficulty chosenDifficulty = showDifficultyDialog();
            if (chosenDifficulty != null) {
                new GameFrame(chosenDifficulty);
                dispose();
            }
        });

        highScoresButton.addActionListener(_ -> new HighScoreView());

        exitButton.addActionListener(_ -> System.exit(0));

        add(mainPanel);

        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private Difficulty showDifficultyDialog() {
        String[] options = {"Easy", "Medium", "Hard"};
        int choice = JOptionPane.showOptionDialog(
                this,
                "Select Difficulty",
                "Difficulty Selection",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        switch (choice) {
            case 0: return Difficulty.EASY;
            case 1: return Difficulty.MEDIUM;
            case 2: return Difficulty.HARD;
            default: return null;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainMenuFrame::new);
    }
}
