package shared.menu;

import model.difficulty.Difficulty;
import shared.GameFrame;
import view.HighScoreView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainMenuFrame
        extends JFrame {

    private BufferedImage buttonImage = null;

    public MainMenuFrame() {
        super("AntiPlague Game - Main Menu");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        JPanel mainPanel = new MainMenuBackgroundPanel();

        JLabel titleLabel = new JLabel("AntiPlague Game");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 60));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel buttonPanel = createButtonPanel();
        JButton newGameButton = createButton("New Game");
        JButton highScoresButton = createButton("High Scores");
        JButton exitButton = createButton("Exit");

        buttonPanel.add(wrapButton(newGameButton, BorderLayout.SOUTH));
        buttonPanel.add(wrapButton(highScoresButton, BorderLayout.CENTER));
        buttonPanel.add(wrapButton(exitButton, BorderLayout.NORTH));

        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        newGameButton.addActionListener(_ -> {
            Difficulty chosenDifficulty = showDifficultyDialog();
            if (chosenDifficulty != null) {
                dispose();
                new GameFrame(chosenDifficulty);
            }
        });

        highScoresButton.addActionListener(_ -> new HighScoreView());

        exitButton.addActionListener(_ -> System.exit(0));

        add(mainPanel);
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

    private void loadButtonImage() {
        if (buttonImage == null) {
            try {
                buttonImage = ImageIO.read(new File("./src/resources/Button_ui.png"));
            } catch (IOException e) {
                JOptionPane.showMessageDialog(
                        null,
                        "Error loading button image: " + e,
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        loadButtonImage();

        button.setPreferredSize(new Dimension(300, 100));
        button.setFont(new Font("Arial", Font.BOLD, 24));
        button.setForeground(Color.WHITE);
        button.setFocusable(false);
        button.setUI(new MainMenuButtonUI(buttonImage));
        return button;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 0, 10));
        panel.setOpaque(false);
        return panel;
    }

    private JPanel wrapButton(JButton button, String position) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        JPanel innerPanel = new JPanel(new GridBagLayout());
        innerPanel.setOpaque(false);
        innerPanel.add(button);

        panel.add(innerPanel, position);
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainMenuFrame::new);
    }
}
