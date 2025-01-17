package view;

import model.shop.upgrades.Upgrade;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.PanelUI;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class UpgradePanel
        extends JPanel {

    public UpgradePanel() {
        setLayout(new GridLayout(0, 9, 10, 10));
        setBackground(new Color(73, 46, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        setUI(new PanelUI() {
            private BufferedImage image;

            private void loadBackground() {
                if (image == null) {
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

            @Override
            public void paint(Graphics g, JComponent c) {
                loadBackground();
                if (image != null) {
                    g.drawImage(image, 0, 0, c.getWidth(), c.getHeight(), null);
                }
                super.paint(g, c);
            }
        });
    }

    public void addUpgrade(Upgrade upgrade) {
        UpgradeView upgradeView = new UpgradeView(upgrade);
        add(upgradeView);
    }
}
