package arkanoid.entities;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Rectangle;

import arkanoid.core.GameBounds;

public class Ball extends MovableObject {
    private boolean launched = false;
    private int speed = 5;

    public Ball(int x, int y, int w, int h) {
        super(x, y, w, h);
        dx = 0;
        dy = 0;
    }

    @Override
    public void update() {
        if (!launched) return;
        super.update();

        // wall collisions
        if (x <= 0) {
            x = 0;
            dx = -dx;
        } else if (x + width >= GameBounds.WIDTH) {
            x = GameBounds.WIDTH - width;
            dx = -dx;
        }

        if (y <= 0) {
            y = 0;
            dy = -dy;
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillOval(x, y, width, height);
    }

    public void launchIfStopped() {
        if (!launched) {
            launched = true;
            dx = speed * (Math.random() > 0.5 ? 1 : -1);
            dy = -speed;
        }
    }

    public void onPaddleHit(Paddle p) {
        // reflect dy and modify dx based on hit position
        Rectangle pb = p.getBounds();
        double hitPos = ((x + width / 2.0) - (pb.getX() + pb.getWidth() / 2.0)) / (pb.getWidth() / 2.0);
        dx = hitPos * 6;
        dy = -Math.abs(dy != 0 ? dy : -speed);
        if (Math.abs(dx) < 1) dx = Math.signum(dx) * 1;
        launched = true;
    }

    public void onBrickHit(Brick b) {
        // naive reflection: flip vertical direction
        dy = -dy;
    }

    public void resetPosition(int nx, int ny) {
        x = nx; y = ny;
        dx = 0; dy = 0;
        launched = false;
    }
}