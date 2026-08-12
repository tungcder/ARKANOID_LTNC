package uet.ltnc.arkanoidgame.entities.ball;

import javafx.animation.PauseTransition;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import uet.ltnc.arkanoidgame.entities.brick.Brick;
import uet.ltnc.arkanoidgame.entities.brick.BrickGrid;
import uet.ltnc.arkanoidgame.entities.paddle.Paddle;
import uet.ltnc.arkanoidgame.entities.item.Item;

public class Ball {

    private double x, y;
    private double radius;
    private double dx = 3, dy = -3;

    private final double baseRadius;
    private PauseTransition sizeEffectTimer;

    private final double baseSpeed;
    private PauseTransition speedEffectTimer;

    private boolean explosive = false;
    private double explosionRadius = 0.0;
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

        if (x <= 0 || x + radius * 2 >= 800) {
            dx *= -1;
        }

        if (y <= 0) {
            dy *= -1;
        }
    }

    public void checkCollision(Paddle paddle) {
        if (x + radius * 2 > paddle.getX()
                && x < paddle.getX() + paddle.getWidth()
                && y + radius * 2 >= paddle.getY()
                && y + radius * 2 <= paddle.getY() + paddle.getHeight()) {

            dy *= -1;
            y = paddle.getY() - radius * 2;
        }
    }

    public Item checkCollision(BrickGrid grid) {
        for (Brick brick : grid.getBricks()) {
            if (brick.isDestroyed()) {
                continue;
            }

            if (x + radius * 2 > brick.getX()
                    && x < brick.getX() + brick.getWidth()
                    && y + radius * 2 > brick.getY()
                    && y < brick.getY() + brick.getHeight()) {

                dy *= -1;

                double impactX = brick.getX() + brick.getWidth() / 2;
                double impactY = brick.getY() + brick.getHeight() / 2;

                Item directDrop = null;

                if (brick.hit()) {
                    directDrop = brick.getPowerup();
                }

                Item splashDrop = null;

                if (explosive && explosionRadius > 0.0) {
                    splashDrop = explodeAt(grid, impactX, impactY);
                }

                return directDrop != null ? directDrop : splashDrop;
            }
        }

        return null;
    }

    private Item explodeAt(BrickGrid grid, double ix, double iy) {
        Item firstDrop = null;
        if (!explosive || explosionRadius <= 0) return null;

        for (Brick nb : grid.getBricks()) {
            if (nb.isDestroyed()) continue;

            double bx = nb.getX() + nb.getWidth() / 2.0;
            double by = nb.getY() + nb.getHeight() / 2.0;
            double dist = Math.hypot(bx - ix, by - iy);

            if (dist <= explosionRadius) {
                if (nb.hit()) {
                    Item drop = nb.getPowerup();
                    if (firstDrop == null) firstDrop = drop;
                }
            }
        }

        return firstDrop;
    }

    public void applySizeBuff(double multiplier, double durationSeconds) {
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

    public void applySpeedBuff(double multiplier, double durationSeconds) {
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

    public void applyExplosiveBuff(double radius, double durationSeconds) {
        if (durationSeconds <= 0) {
            return;
        }

        explosive = true;
        explosionRadius = Math.max(0.0, radius);

        if (explosiveEffectTimer != null) {
            explosiveEffectTimer.stop();
        }

        explosiveEffectTimer = new PauseTransition(
                Duration.seconds(durationSeconds)
        );

        explosiveEffectTimer.setOnFinished(event -> {
            explosive = false;
            explosionRadius = 0.0;
        });

        explosiveEffectTimer.playFromStart();
    }
    public boolean isExplosive() {
        return explosive;
    }

    public double getExplosionRadius() {
        return explosionRadius;
    }

    public void render(GraphicsContext gc) {
        gc.setFill(Color.YELLOW);
        gc.fillOval(x, y, radius * 2, radius * 2);
    }
}