package view;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;

public class PointsView
    extends JPanel {

    private final JLabel pointsLabel;

    public PointsView(int points) {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        pointsLabel = new JLabel("Points: " + points, SwingConstants.CENTER);
        pointsLabel.setForeground(Color.BLACK);
        pointsLabel.setFont(new Font("Arial", Font.BOLD, 24));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(pointsLabel, BorderLayout.CENTER);
    }

    public void updatePoints(int points) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        String formattedPoints = numberFormat.format(points);

        pointsLabel.setText("Points: " + formattedPoints);
        revalidate();
        repaint();
    }
}
