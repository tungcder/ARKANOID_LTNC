package arkanoid.main;

import javax.swing.JFrame;
import arkanoid.core.GameBounds;
import arkanoid.ui.GamePanel;
import arkanoid.utils.GameLogger;

public class ArkanoidGame {
    public static void main(String[] args) {
        GameLogger.log("Starting Arkanoid (minimal)");
        JFrame frame = new JFrame("Arkanoid - Minimal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(GameBounds.WIDTH, GameBounds.HEIGHT);
        frame.setResizable(false);

        GamePanel panel = new GamePanel();
        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        panel.startGame();
    }
}