package view;

import model.shop.upgrades.Upgrade;

import javax.swing.*;
import java.awt.*;

public class UpgradeView extends JPanel {
    public UpgradeView(Upgrade upgrade) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        setBackground(Color.WHITE);

        JLabel nameLabel = new JLabel(upgrade.getName(), SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel descriptionLabel = new JLabel("<html><div style='text-align: center;'>" + upgrade.getDescription() + "</div></html>", SwingConstants.CENTER);
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        JButton actionButton = new JButton(upgrade.getCost() + " points");
        actionButton.setEnabled(upgrade.isAvailable());
        actionButton.addActionListener(_ -> upgrade.apply());

        add(nameLabel, BorderLayout.NORTH);
        add(descriptionLabel, BorderLayout.CENTER);
        add(actionButton, BorderLayout.PAGE_END);

        setPreferredSize(new Dimension(150, 100));
    }
}
