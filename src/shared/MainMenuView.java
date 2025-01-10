package shared;

import view.HighScoreView;

import javax.swing.*;
import java.awt.*;

public class MainMenuView extends JFrame {
    public MainMenuView() {
        super("AntiPlague Game - Main Menu");

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 1, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.LIGHT_GRAY);

        JButton newGameButton = new JButton("New Game");
        JButton highScoresButton = new JButton("High Scores");
        JButton exitButton = new JButton("Exit");

        mainPanel.add(newGameButton);
        mainPanel.add(highScoresButton);
        mainPanel.add(exitButton);

        newGameButton.addActionListener( _ -> {
            new MainFrame();
            dispose();
        });

        highScoresButton.addActionListener( _ -> {
            new HighScoreView();
        });

        exitButton.addActionListener( _ -> System.exit(0));

        add(mainPanel);

        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainMenuView::new);
    }
}
