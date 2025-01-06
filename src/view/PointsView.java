package view;

import javax.swing.*;
import java.awt.*;

public class PointsView
    extends JPanel {

    private final JLabel pointsLabel;

    public PointsView() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        pointsLabel = new JLabel("Points: 0", SwingConstants.CENTER);
        pointsLabel.setForeground(Color.BLACK);
        pointsLabel.setFont(new Font("Arial", Font.BOLD, 24));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(pointsLabel, BorderLayout.CENTER);
    }

    public void updatePoints(int points) {
        pointsLabel.setText("Points: " + points);
        revalidate();
        repaint();
    }
}
