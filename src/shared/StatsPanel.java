package shared;

import model.region.regions.Region;
import view.TimerView;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.PanelUI;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;

public class StatsPanel
        extends JPanel {

    private final JLabel header;
    private final JLabel statsLabel;
    private final JPanel contentPanel;
    private final TimerView timerView;
    private Region selectedRegion;

    public StatsPanel() {
        setBackground(Color.BLACK);
        setLayout(new BorderLayout());

        header = new JLabel("Statistics", SwingConstants.CENTER);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 32));
        header.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(header, BorderLayout.NORTH);

        contentPanel = createContentPanel();

        statsLabel = new JLabel("Choose region", SwingConstants.CENTER);
        statsLabel.setForeground(Color.WHITE);
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

    public Region getSelectedRegion() {
        return this.selectedRegion;
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
        String formattedPopulation = numberFormat.format(selectedRegion.getMerePopulation());
        String formattedInfectedPopulation = numberFormat.format(selectedRegion.getInfectedPopulation());
        String formattedCuredPopulation = numberFormat.format(selectedRegion.getCuredPopulation());

        statsLabel.setText(String.format("<html>Region: %s" +
                        "<br>Population: %s " +
                        "<br>Infection spread: %.2f%%" +
                        "<br>Cure efficiency: %.2f%%" +
                        "<br>Infected population: %s" +
                        "<br>Cured population: %s</html>",
                selectedRegion.getName(),
                formattedPopulation,
                selectedRegion.getInfectionLevel(),
                selectedRegion.getCureEfficiency(),
                formattedInfectedPopulation,
                formattedCuredPopulation));

        revalidate();
        repaint();
    }

    private JPanel createContentPanel() {
        JPanel panel = new JPanel();

        panel.setBackground(Color.WHITE);
        panel.setLayout(new GridBagLayout());
        panel.setUI(new PanelUI() {

            private BufferedImage image;

            private void loadBackground() {
                try {
                    image = ImageIO.read(new File("./src/resources/Stats_ui.png"));
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Error loading button image: " + e,
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

        return panel;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(300, getParent() != null ? getParent().getHeight() : 600);
    }
}
