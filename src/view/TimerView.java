package view;

import javax.swing.*;
import java.awt.*;

public class TimerView extends JPanel {
    private final JLabel timerLabel;

    public TimerView() {
        setBackground(Color.BLACK);
        setLayout(new BorderLayout());

        timerLabel = new JLabel("00:00", SwingConstants.CENTER);
        timerLabel.setForeground(Color.WHITE);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        timerLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(timerLabel, BorderLayout.CENTER);
    }

    public void updateTimer(int minutes, int seconds) {
        timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
        revalidate();
        repaint();
    }
}
