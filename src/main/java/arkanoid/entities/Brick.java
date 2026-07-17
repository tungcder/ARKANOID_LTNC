package arkanoid.entities;

import java.awt.Graphics2D;
import java.awt.Color;

public class Brick extends GameObject {
    private int hitsRemaining;

    public Brick(int x, int y, int w, int h, int hits) {
        super(x, y, w, h);
        this.hitsRemaining = hits;
    }

    @Override
    public void update() {
        // static
    }

    @Override
    public void render(Graphics2D g) {
        if (hitsRemaining <= 0) return;
        switch (hitsRemaining) {
            case 3: g.setColor(Color.MAGENTA); break;
            case 2: g.setColor(Color.ORANGE); break;
            default: g.setColor(Color.RED); break;
        }
        g.fillRect(x, y, width, height);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);
    }

    public void hit() {
        hitsRemaining--;
    }

    public boolean isDestroyed() {
        return hitsRemaining <= 0;
    }
}