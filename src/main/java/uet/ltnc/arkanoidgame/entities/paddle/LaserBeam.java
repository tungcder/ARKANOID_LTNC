package uet.ltnc.arkanoidgame.entities.paddle;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class LaserBeam {
    private static final int LASER_WIDTH = 4;
    private static final int LASER_HEIGHT = 15;
    private static final double LASER_SPEED = 10;

    private double x, y;
    private double vy;
    private boolean active = true;

    public LaserBeam(double x, double y) {
        this.x = x; this.y = y;
        this.vy = -LASER_SPEED; // moves upward
    }

    public void update() { y += vy; }

    public void render(GraphicsContext gc) {
        if (!active) return;
        gc.setFill(Color.RED);
        gc.fillRect(x, y, LASER_WIDTH, LASER_HEIGHT);
    }

    // Check collision with brick bounds
    public boolean intersects(double rx, double ry, double rw, double rh) {
        return x < rx + rw && x + LASER_WIDTH > rx && y < ry + rh && y + LASER_HEIGHT > ry;
    }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public double getX() { return x; }
    public double getY() { return y; }
    public void restoreVelocity(double vy) { this.vy = vy; }
}
