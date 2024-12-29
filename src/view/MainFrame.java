package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame {
    public MainFrame() {
        super("AntiPlague Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        StatsPanel statsPanel = new StatsPanel();
        MapPanel mapPanel = new MapPanel(statsPanel);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(mapPanel, BorderLayout.CENTER);
        topPanel.add(statsPanel, BorderLayout.EAST);

        ShopPanel shopPanel = new ShopPanel();

        add(topPanel, BorderLayout.CENTER);
        add(shopPanel, BorderLayout.SOUTH);

        setSize(1200, 800);
        setLocationRelativeTo(null);
        setResizable(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mapPanel.stopThreads();
                System.exit(0);
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}
