import shared.MainMenuFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainMenuFrame mainMenuView = new MainMenuFrame();
            mainMenuView.setVisible(true);
        });
    }
}