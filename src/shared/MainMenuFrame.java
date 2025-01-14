package shared;

import view.HighScoreView;

import javax.swing.*;
import java.awt.*;

public class MainMenuFrame
        extends JFrame {

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

        newGameButton.addActionListener( _ -> {
            new GameFrame();
            dispose();
        });

        highScoresButton.addActionListener( _ -> {
            new HighScoreView();
        });

        exitButton.addActionListener( _ -> System.exit(0));

        add(mainPanel);

        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainMenuFrame::new);
    }
}
