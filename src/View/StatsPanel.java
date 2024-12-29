package View;

import Model.Country;

import javax.swing.*;
import java.awt.*;

public class StatsPanel extends JPanel {
    private JLabel header;
    private JLabel statsLabel;
    private JPanel contentPanel;

    public StatsPanel() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        header = new JLabel("Statistics", SwingConstants.CENTER);
        header.setForeground(Color.BLACK);
        header.setFont(new Font("Arial", Font.BOLD, 32));
        header.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(header, BorderLayout.NORTH);

        contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setLayout(new GridBagLayout());

        statsLabel = new JLabel("Choose region", SwingConstants.CENTER);
        statsLabel.setForeground(Color.BLACK);
        statsLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        contentPanel.add(statsLabel);
        contentPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        add(contentPanel, BorderLayout.CENTER);

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent e) {
                resizeFont();
            }
        });
    }

    public void updateStats(Country country) {
        statsLabel.setText(String.format("<html>Region: %s" +
                "<br>Population: %d " +
                "<br>Infection spread: %.2f </html>",
                country.getName(), country.getPopulation(), country.getInfectionLevel()));

        resizeFont();
    }

    private void resizeFont() {
        int panelWidth = getWidth();
        int panelHeight = getHeight();

        if (panelWidth > 0 && panelHeight > 0) {
            int headerSize = Math.min(panelWidth / 12, 48);
            int statsSize = Math.min(panelWidth / 10, panelHeight / 8);

            header.setFont(new Font("Arial", Font.BOLD, headerSize));
            statsLabel.setFont(new Font("Arial", Font.BOLD, statsSize));
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(300, getParent() != null ? getParent().getHeight() : 600);
    }
}
