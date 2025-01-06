package shared;

import model.country.Region;
import view.TimerView;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;

public class StatsPanel
        extends JPanel {

    private final JLabel header;
    private final JLabel statsLabel;
    private final JPanel contentPanel;
    private final TimerView timerView;
    private Region selectedRegion;

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

        timerView = new TimerView();
        add(timerView, BorderLayout.SOUTH);
    }

    public TimerView getTimerView() {
        return timerView;
    }

    public void setSelectedRegion(Region region) {
        this.selectedRegion = region;
        refreshStats();
    }

    public void updateInfection(Region region) {
        if (selectedRegion == region) {
            refreshStats();
        }
    }

    private void refreshStats() {
        if (selectedRegion == null) {
            statsLabel.setText("Choose region");
            return;
        }

        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        String formattedPopulation = numberFormat.format(selectedRegion.getPopulation());

        statsLabel.setText(String.format("<html>Region: %s" +
                        "<br>Population: %s " +
                        "<br>Infection spread: %.2f%% </html>",
                selectedRegion.getName(), formattedPopulation, selectedRegion.getInfectionLevel()));

        revalidate();
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(300, getParent() != null ? getParent().getHeight() : 600);
    }
}
