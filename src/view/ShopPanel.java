package view;

import javax.swing.*;
import java.awt.*;

public class ShopPanel extends JPanel {

    public ShopPanel() {
        setBackground(Color.WHITE);
        JLabel label = new JLabel("Shop Panel");
        label.setFont(new Font("Arial", Font.BOLD, 48));
        setBorder(BorderFactory.createLineBorder(Color.black));

        add(label);
    }
}

