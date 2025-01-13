package shared;

import controller.PointsController;
import controller.TimerController;
import controller.UpgradeController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameFrame extends JFrame {

    private TimerController timerController;
    private PointsController pointsController;
    private UpgradeController upgradeController;
    private KeyEventDispatcher keyEventDispatcher;

    public GameFrame() {
        super("AntiPlague Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        StatsPanel statsPanel = new StatsPanel();
        MapPanel mapPanel = new MapPanel(statsPanel);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(mapPanel, BorderLayout.CENTER);
        topPanel.add(statsPanel, BorderLayout.EAST);

        ShopPanel shopPanel = new ShopPanel();

        timerController = new TimerController(statsPanel);
        pointsController = new PointsController(shopPanel);
        upgradeController = new UpgradeController(shopPanel, statsPanel);

        add(topPanel, BorderLayout.CENTER);
        add(shopPanel, BorderLayout.SOUTH);

        setSize(1200, 800);
        setLocationRelativeTo(null);
        setResizable(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                cleanUpResources(mapPanel);
                System.exit(0);
            }
        });

        keyEventDispatcher = e -> {
            if (e.getID() == KeyEvent.KEY_PRESSED) {
                if (e.isControlDown() && e.isShiftDown() && e.getKeyCode() == KeyEvent.VK_Q) {
                    cleanUpResources(mapPanel);
                    dispose();
                    new MainMenuFrame();
                    return true;
                }
            }
            return false;
        };

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keyEventDispatcher);

        setFocusable(true);
        requestFocusInWindow();
        setVisible(true);
    }

    private void cleanUpResources(MapPanel mapPanel) {
        mapPanel.stopThreads();
        timerController.stopTimer();
        KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(keyEventDispatcher);
    }
}
