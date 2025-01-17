package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.PanelUI;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;

public class PointsView
    extends JPanel {

    private final JLabel pointsLabel;

    public PointsView(int points) {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        setUI(new PanelUI() {
            private BufferedImage image;

            private void loadBackground() {
                try {
                    image = ImageIO.read(new File("./src/resources/Shop_header_bg.png"));
                } catch (IOException | NullPointerException e) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Error loading background: " + e,
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void paint(Graphics g, JComponent c) {
                loadBackground();
                if (image != null) {
                    g.drawImage(image, 0, 0, c.getWidth(), c.getHeight(), null);
                }
                super.paint(g, c);
            }
        });

        pointsLabel = new JLabel("Points: " + points, SwingConstants.CENTER);
        pointsLabel.setForeground(Color.WHITE);
        pointsLabel.setFont(new Font("Arial", Font.BOLD, 24));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(pointsLabel, BorderLayout.CENTER);
    }

    public synchronized void updatePoints(int points) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        String formattedPoints = numberFormat.format(points);

        pointsLabel.setText("Points: " + formattedPoints);
    }
}
