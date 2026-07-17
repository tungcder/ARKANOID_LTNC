package arkanoid.ui;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.List;

import arkanoid.core.GameBounds;
import arkanoid.entities.Paddle;
import arkanoid.entities.Ball;
import arkanoid.factories.BrickFactory;
import arkanoid.utils.GameLogger;

public class GamePanel extends JPanel implements KeyListener {
    private static final int FPS = 60;
    private Paddle paddle;
    private Ball ball;
    private List<arkanoid.entities.Brick> bricks;
    private Timer timer;
    private boolean running = false;

    public GamePanel() {
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        initGameObjects();
    }

    private void initGameObjects() {
        paddle = new Paddle(GameBounds.WIDTH / 2 - 50, GameBounds.HEIGHT - 60, 100, 12);
        ball = new Ball(GameBounds.WIDTH / 2 - 8, GameBounds.HEIGHT - 80, 16, 16);
        bricks = BrickFactory.createSimpleLayout(6, 8, 60, 20, 50, 60);
    }

    public void startGame() {
        if (running) return;
        running = true;
        timer = new Timer(1000 / FPS, e -> {
            updateGame();
            repaint();
        });
        timer.start();
    }

    public void stopGame() {
        running = false;
        if (timer != null) timer.stop();
    }

    private void updateGame() {
        paddle.update();
        ball.update();

        // ball <-> paddle
        if (ball.getBounds().intersects(paddle.getBounds())) {
            ball.onPaddleHit(paddle);
        }

        // ball <-> bricks
        for (int i = bricks.size() - 1; i >= 0; i--) {
            arkanoid.entities.Brick b = bricks.get(i);
            if (b.getBounds().intersects(ball.getBounds())) {
                ball.onBrickHit(b);
                b.hit();
                if (b.isDestroyed()) {
                    bricks.remove(i);
                }
                break; // handle one collision per frame for simplicity
            }
        }

        // reset if ball falls
        if (ball.getY() > GameBounds.HEIGHT) {
            GameLogger.log("Ball lost. Resetting ball and paddle.");
            ball.resetPosition(GameBounds.WIDTH / 2 - 8, GameBounds.HEIGHT - 80);
            paddle.setX(GameBounds.WIDTH / 2 - paddle.getWidth() / 2);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        paddle.render(g2);
        ball.render(g2);
        for (arkanoid.entities.Brick b : bricks) b.render(g2);

        // HUD
        g2.setColor(Color.WHITE);
        g2.drawString("Bricks: " + bricks.size(), 10, 20);
    }

    // KeyListener
    @Override
    public void keyPressed(KeyEvent e) {
        int k = e.getKeyCode();
        if (k == KeyEvent.VK_LEFT) paddle.setMovingLeft(true);
        if (k == KeyEvent.VK_RIGHT) paddle.setMovingRight(true);
        if (k == KeyEvent.VK_SPACE) ball.launchIfStopped();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int k = e.getKeyCode();
        if (k == KeyEvent.VK_LEFT) paddle.setMovingLeft(false);
        if (k == KeyEvent.VK_RIGHT) paddle.setMovingRight(false);
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}