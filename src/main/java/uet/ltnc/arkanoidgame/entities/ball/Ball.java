package uet.ltnc.arkanoidgame.entities.ball;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import uet.ltnc.arkanoidgame.entities.brick.Brick;
import uet.ltnc.arkanoidgame.entities.paddle.Paddle;
import uet.ltnc.arkanoidgame.utils.GameBounds;

public class Ball {
    private static final double INITIAL_SPEED = 5.0;
    private static final double MAX_SPEED = 10.0;

    private double x, y;
    private final int radius = 8;
    private double dx, dy;
    private boolean attached = false;
    private Paddle attachedPaddle;
    private double speedMultiplier = 1.0;
    private double levelSpeedBonus = 0.0;

    public Ball(double x, double y) {
        this.x = x;
        this.y = y;
        this.dx = 0;
        this.dy = 0;
    }

    public Ball(double x, double y, int level) {
        this(x, y);
        this.levelSpeedBonus = Math.min((level - 1) * 0.07, 0.7);
    }

    public void attachToPaddle(Paddle paddle) {
        this.attached = true;
        this.attachedPaddle = paddle;
        this.dx = 0;
        this.dy = 0;
    }

    public void launch() {
        if (attached) {
            attached = false;
            double angle = Math.toRadians(-90 + (Math.random() * 30 - 15));
            double totalSpeed = INITIAL_SPEED * (1.0 + levelSpeedBonus) * speedMultiplier;
            totalSpeed = Math.min(totalSpeed, MAX_SPEED);
            dx = totalSpeed * Math.cos(angle);
            dy = totalSpeed * Math.sin(angle);
            attachedPaddle = null;
        }
    }

    public void update() {
        if (attached && attachedPaddle != null) {
            x = attachedPaddle.getX() + attachedPaddle.getWidth() / 2.0;
            y = attachedPaddle.getY() - radius - 2;
        } else {
            x += dx;
            y += dy;
            if (x - radius < GameBounds.PLAY_LEFT) {
                x = GameBounds.PLAY_LEFT + radius;
                dx = Math.abs(dx);
            }
            if (x + radius > GameBounds.PLAY_RIGHT) {
                x = GameBounds.PLAY_RIGHT - radius;
                dx = -Math.abs(dx);
            }
            if (y - radius < GameBounds.PLAY_TOP) {
                y = GameBounds.PLAY_TOP + radius;
                dy = Math.abs(dy);
            }
        }
    }

    public void bounceOffPaddle(Paddle paddle) {
        if (dy > 0) {
            dy = -dy;
            double hitPos = (x - paddle.getX()) / paddle.getWidth();
            double angle = (hitPos - 0.5) * 60;
            double speed = Math.sqrt(dx * dx + dy * dy);
            dx = speed * Math.sin(Math.toRadians(angle));
            dy = -speed * Math.cos(Math.toRadians(angle));
            if (Math.abs(dy) < 2) {
                dy = dy < 0 ? -2 : 2;
            }
        }
    }

    public void bounceOffBrick(Brick brick) {
        double bx = brick.getX(), by = brick.getY();
        double bw = brick.getWidth(), bh = brick.getHeight();
        double overlapLeft = (x + radius) - bx;
        double overlapRight = (bx + bw) - (x - radius);
        double overlapTop = (y + radius) - by;
        double overlapBottom = (by + bh) - (y - radius);
        double minOverlap = Math.min(Math.min(overlapLeft, overlapRight),
                Math.min(overlapTop, overlapBottom));
        double pushDistance = 2.0;
        if (minOverlap == overlapLeft || minOverlap == overlapRight) {
            dx = -dx;
            if (minOverlap == overlapLeft) x = bx - radius - pushDistance;
            else x = bx + bw + radius + pushDistance;
        } else {
            dy = -dy;
            if (minOverlap == overlapTop) y = by - radius - pushDistance;
            else y = by + bh + radius + pushDistance;
        }
    }

    public boolean intersects(double rx, double ry, double rw, double rh) {
        double closestX = Math.max(rx, Math.min(x, rx + rw));
        double closestY = Math.max(ry, Math.min(y, ry + rh));
        double distX = x - closestX;
        double distY = y - closestY;
        double effectiveRadius = radius + 1.0;
        return (distX * distX + distY * distY) < (effectiveRadius * effectiveRadius);
    }

    public void slow() {
        if (speedMultiplier == 1.0) {
            speedMultiplier = 0.5;
            dx *= 0.5;
            dy *= 0.5;
        }
    }

    public void restoreNormalSpeed() {
        if (speedMultiplier != 1.0) {
            if (!attached) {
                dx /= speedMultiplier;
                dy /= speedMultiplier;
            }
            speedMultiplier = 1.0;
        }
    }

    public void render(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);
    }

    public void setVelocity(double dx, double dy) { this.dx = dx; this.dy = dy; }
    public double getX() { return x; }
    public double getY() { return y; }
    public double getDx() { return dx; }
    public double getDy() { return dy; }
    public int getRadius() { return radius; }
    public boolean isAttached() { return attached; }
    public double getSpeedMultiplier() { return speedMultiplier; }
    public double getLevelSpeedBonus() { return levelSpeedBonus; }

    public void setSpeedMultiplier(double multiplier) { this.speedMultiplier = multiplier; }
    public void setLevelSpeedBonus(double bonus) { this.levelSpeedBonus = bonus; }
    public void setAttached(boolean attached) {
        this.attached = attached;
        if (!attached) this.attachedPaddle = null;
    }
}