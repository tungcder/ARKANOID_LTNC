package arkanoid.entities;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class GameObject {
    protected int x, y, width, height;

    public GameObject(int x, int y, int w, int h) {
        this.x = x; this.y = y; this.width = w; this.height = h;
    }

    public abstract void update();
    public abstract void render(Graphics2D g);

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    // getters/setters
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
}