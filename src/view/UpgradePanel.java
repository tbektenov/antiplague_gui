package view;

import javax.swing.*;
import java.awt.*;

public class UpgradePanel
    extends JPanel {

    private final JLabel label;

    public UpgradePanel() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        label = new JLabel("Shop panel", SwingConstants.LEADING);
        label.setForeground(Color.BLACK);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(label, BorderLayout.CENTER);
    }

}
