package arkanoid.entities;

import java.awt.Graphics2D;
import java.awt.Color;

import arkanoid.core.GameBounds;

public class Paddle extends GameObject {
    private boolean movingLeft = false;
    private boolean movingRight = false;
    private int speed = 6;

    public Paddle(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    @Override
    public void update() {
        if (movingLeft) x -= speed;
        if (movingRight) x += speed;
        if (x < 0) x = 0;
        if (x + width > GameBounds.WIDTH) x = GameBounds.WIDTH - width;
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.CYAN);
        g.fillRect(x, y, width, height);
    }

    public void setMovingLeft(boolean movingLeft) { this.movingLeft = movingLeft; }
    public void setMovingRight(boolean movingRight) { this.movingRight = movingRight; }
    public void setX(int x) { this.x = x; }
}