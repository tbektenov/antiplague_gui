package shared;

import model.difficulty.Difficulty;
import view.HighScoreView;

import javax.swing.*;
import java.awt.*;

public class MainMenuFrame extends JFrame {

    public MainMenuFrame() {
        super("AntiPlague Game - Main Menu");

        JPanel mainPanel = new MainMenuBackgroundPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("AntiPlague Game");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 60));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 0, 5));
        buttonPanel.setOpaque(false);

        JButton newGameButton = new JButton("New Game");
        JButton highScoresButton = new JButton("High Scores");
        JButton exitButton = new JButton("Exit");

        Dimension buttonSize = new Dimension(200, 100);
        newGameButton.setPreferredSize(buttonSize);
        highScoresButton.setPreferredSize(buttonSize);
        exitButton.setPreferredSize(buttonSize);

        buttonPanel.add(wrapButton(newGameButton));
        buttonPanel.add(wrapButton(highScoresButton));
        buttonPanel.add(wrapButton(exitButton));

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

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
        JComboBox<Difficulty> comboBox = new JComboBox<>(Difficulty.values());

        int choice = JOptionPane.showConfirmDialog(
                this,
                comboBox,
                "Select Difficulty",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (choice == JOptionPane.OK_OPTION) return (Difficulty) comboBox.getSelectedItem();
        else return null;
    }

    private JPanel wrapButton(JButton button) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        panel.add(button);
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainMenuFrame::new);
    }
}
