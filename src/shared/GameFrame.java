package shared;

import controller.PointsController;
import controller.TimerController;
import controller.TransportController;
import controller.UpgradeController;
import model.difficulty.Difficulty;
import shared.menu.MainMenuFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class GameFrame
        extends JFrame {

    private TimerController timerController;
    private PointsController pointsController;
    private UpgradeController upgradeController;
    private TransportController transportController;

    public GameFrame(Difficulty difficulty) {
        super("AntiPlague Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);

        StatsPanel statsPanel = new StatsPanel();
        MapPanel mapPanel = new MapPanel(statsPanel, difficulty);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(mapPanel, BorderLayout.CENTER);
        topPanel.add(statsPanel, BorderLayout.EAST);

        ShopPanel shopPanel = new ShopPanel();

        timerController = new TimerController(statsPanel, difficulty);
        pointsController = new PointsController(shopPanel);
        upgradeController = new UpgradeController(shopPanel, statsPanel);
        transportController = new TransportController(mapPanel.getTransportManager(), difficulty);

        add(topPanel, BorderLayout.CENTER);
        add(shopPanel, BorderLayout.SOUTH);

        KeyStroke keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK);
        InputMap inputMap = this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = this.getRootPane().getActionMap();

        inputMap.put(keyStroke, "quitToMainMenu");
        actionMap.put("quitToMainMenu", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mapPanel.stopThreads();
                dispose();
                new MainMenuFrame();
            }
        });
    }
}
