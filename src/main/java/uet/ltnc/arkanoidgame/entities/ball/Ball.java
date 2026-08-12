package uet.ltnc.arkanoidgame.entities.ball;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

import uet.ltnc.arkanoidgame.entities.paddle.Paddle;
import uet.ltnc.arkanoidgame.entities.brick.Brick;
import uet.ltnc.arkanoidgame.entities.brick.BrickGrid;

public class Ball {
    private double x, y;        // tọa độ góc trên quả bóng
    private double radius;      // bán kính bóng
    private double dx = 3, dy = -3; // tốc độ ban đầu

    private final double baseRadius;
    private PauseTransition sizeEffectTimer;

    private final double baseSpeed;
    private PauseTransition speedEffectTimer;

    private boolean explosive = false;
    private PauseTransition explosiveEffectTimer;

    public Ball(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;

        this.baseRadius = radius;
        this.sizeEffectTimer = null;

        this.baseSpeed = Math.hypot(dx, dy);
        this.speedEffectTimer = null;

        this.explosive = false;
        this.explosiveEffectTimer = null;
    }

    public void update() {
        x += dx;
        y += dy;

        // Va chạm tường
        if (x <= 0 || x + radius * 2 >= 800) dx *= -1;
        if (y <= 0) dy *= -1;
    }

    public void checkCollision(Paddle paddle) {
        if (x + radius * 2 > paddle.getX() &&
                x < paddle.getX() + paddle.getWidth() &&
                y + radius * 2 >= paddle.getY() &&
                y + radius * 2 <= paddle.getY() + paddle.getHeight()) {
            dy *= -1;
            y = paddle.getY() - radius * 2; // tránh dính paddle
        }
    }

    public void checkCollision(BrickGrid grid) {
        for (Brick brick : grid.getBricks()) {
            if (!brick.isDestroyed() &&
                    x + radius * 2 > brick.getX() &&
                    x < brick.getX() + brick.getWidth() &&
                    y + radius * 2 > brick.getY() &&
                    y < brick.getY() + brick.getHeight()) {

                dy *= -1;
                brick.setDestroyed(true);
                break;
            }
        }
    }

    public void applySizeBuff(double multiplier,
                              double durationSeconds) {
        if (multiplier <= 0 || durationSeconds <= 0) {
            return;
        }

        changeRadius(baseRadius * multiplier);

        if (sizeEffectTimer != null) {
            sizeEffectTimer.stop();
        }

        sizeEffectTimer = new PauseTransition(
                Duration.seconds(durationSeconds)
        );

        sizeEffectTimer.setOnFinished(
                event -> resetSize()
        );

        sizeEffectTimer.playFromStart();
    }

    private void changeRadius(double newRadius) {
        double centerX = x + radius;
        double centerY = y + radius;

        radius = newRadius;

        x = centerX - radius;
        y = centerY - radius;

        x = Math.max(
                0,
                Math.min(x, 800 - radius * 2)
        );

        y = Math.max(0, y);
    }

    private void resetSize() {
        changeRadius(baseRadius);
    }

    public void applySpeedBuff(double multiplier,
                               double durationSeconds) {
        if (multiplier <= 0 || durationSeconds <= 0) {
            return;
        }

        changeSpeed(baseSpeed * multiplier);

        if (speedEffectTimer != null) {
            speedEffectTimer.stop();
        }

        speedEffectTimer = new PauseTransition(
                Duration.seconds(durationSeconds)
        );

        speedEffectTimer.setOnFinished(
                event -> resetSpeed()
        );

        speedEffectTimer.playFromStart();
    }

    private void changeSpeed(double newSpeed) {
        double currentSpeed = Math.hypot(dx, dy);

        if (currentSpeed < 0.000001) {
            dx = 0;
            dy = -newSpeed;
            return;
        }

        double scale = newSpeed / currentSpeed;

        dx *= scale;
        dy *= scale;
    }

    private void resetSpeed() {
        changeSpeed(baseSpeed);
    }

    public void applyExplosiveBuff(double durationSeconds) {
        if (durationSeconds <= 0) {
            return;
        }

        explosive = true;

        if (explosiveEffectTimer != null) {
            explosiveEffectTimer.stop();
        }

        explosiveEffectTimer = new PauseTransition(
                Duration.seconds(durationSeconds)
        );

        explosiveEffectTimer.setOnFinished(
                event -> explosive = false
        );

        explosiveEffectTimer.playFromStart();
    }

    public boolean isExplosive() {
        return explosive;
    }

    public void render(GraphicsContext gc) {
        gc.setFill(Color.YELLOW);
        gc.fillOval(x, y, radius * 2, radius * 2);
    }
}