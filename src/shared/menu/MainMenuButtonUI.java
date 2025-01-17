package shared.menu;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;

public class MainMenuButtonUI
    extends BasicButtonUI {

    private Image image;

    public MainMenuButtonUI(Image image) {
        this.image = image;
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        if (image != null) {
            g.drawImage(image, 0, 0, c.getWidth(), c.getHeight(), null);
        }
        super.paint(g, c);
    }

}
