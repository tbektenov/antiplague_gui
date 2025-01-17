package view;

import model.shop.upgrades.Upgrade;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class UpgradePanel
        extends JPanel {

    private BufferedImage image;

    public UpgradePanel() {
        setLayout(new GridLayout(0, 9, 10, 10));
        setBackground(new Color(73, 46, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        loadBackground();
    }

    public void addUpgrade(Upgrade upgrade) {
        UpgradeView upgradeView = new UpgradeView(upgrade);
        add(upgradeView);
        revalidate();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        }
    }

    private void loadBackground() {
        try {
            image = ImageIO.read(new File("./src/resources/Game_ui.png"));
        } catch (IOException | NullPointerException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error loading background: " + e,
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
