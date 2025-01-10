package view;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class HighScoreView
    extends JFrame {

    public HighScoreView() {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("highscores.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                listModel.addElement(line);
            }
        } catch (IOException e) {
            listModel.addElement("No high scores available.");
        }

        JList<String> highScoresList = new JList<>(listModel);
        highScoresList.setFont(new Font("Arial", Font.PLAIN, 18));
        highScoresList.setBackground(Color.WHITE);
        highScoresList.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JScrollPane scrollPane = new JScrollPane(highScoresList);
        add(scrollPane);

        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

}
