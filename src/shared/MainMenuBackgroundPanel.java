package shared;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

class MainMenuBackgroundPanel
        extends JPanel {

    private Image backgroundImage;

    public MainMenuBackgroundPanel() {
        loadBackground();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackground(g);
    }

    private void loadBackground() {
        try {
            backgroundImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/resources/Main_menu_bg.png")));
        } catch (IOException | NullPointerException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error loading background: " + e,
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void drawBackground(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());

        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        }
    }
}

