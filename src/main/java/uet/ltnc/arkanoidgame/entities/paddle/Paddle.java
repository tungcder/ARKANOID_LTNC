package uet.ltnc.arkanoidgame.entities.paddle;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import java.util.HashSet;
import java.util.Set;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import uet.ltnc.arkanoidgame.utils.GameConstants;

public class Paddle {
    private double x, y, width, height;
    private double speed = 6;
    private Set<KeyCode> keys = new HashSet<>();

    private final double baseWidth;
    private PauseTransition sizeEffectTimer;

    private boolean reverseDirection = false;
    private PauseTransition reverseEffectTimer;

    public Paddle(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.baseWidth = width;
        this.sizeEffectTimer = null;
    }

    public void update() {
        if (keys.contains(KeyCode.LEFT)) {
            if (reverseDirection) {
                if (x + width < GameConstants.WIDTH) x += speed;
            } else {
                if (x > 0) x -= speed;
            }
        }

        if (keys.contains(KeyCode.RIGHT)) {
            if (reverseDirection) {
                if (x > 0) x -= speed;
            } else {
                if (x + width < GameConstants.WIDTH) x += speed;
            }
        }
    }

    public void applyReverseDirection(double seconds) {
        reverseDirection = true;

        if (reverseEffectTimer != null) {
            reverseEffectTimer.stop();
        }

        reverseEffectTimer =
                new PauseTransition(Duration.seconds(seconds));

        reverseEffectTimer.setOnFinished(
                event -> reverseDirection = false
        );

        reverseEffectTimer.playFromStart();
    }

    public void render(GraphicsContext gc) {
        gc.setFill(Color.ORANGE);
        gc.fillRect(x, y, width, height);
    }

    public void addKey(KeyCode code) {
        keys.add(code);
    }

    public void removeKey(KeyCode code) {
        keys.remove(code);
    }

    public void applySizeBuff(double multiplier, double durationSeconds) {
        if (multiplier <= 0 || durationSeconds <= 0) {
            return;
        }
        double centerX = x + width / 2;
        width = baseWidth * multiplier;
        x = centerX - width / 2;

        keepInsideScreen();

        if (sizeEffectTimer != null) {
            sizeEffectTimer.stop();
        }

        sizeEffectTimer =
                new PauseTransition(
                        Duration.seconds(durationSeconds)
                );

        sizeEffectTimer.setOnFinished(event -> resetSize());
        sizeEffectTimer.playFromStart();
    }

    private void resetSize() {
        double centerX = x + width / 2;

        width = baseWidth;
        x = centerX - width / 2;

        keepInsideScreen();
    }

    private void keepInsideScreen() {
        double maxX =
                Math.max(0, GameConstants.WIDTH - width);

        x = Math.max(0, Math.min(x, maxX));
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }
}