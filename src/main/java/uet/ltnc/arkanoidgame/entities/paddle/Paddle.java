package uet.ltnc.arkanoidgame.entities.paddle;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Iterator;
import uet.ltnc.arkanoidgame.utils.GameBounds;

public class Paddle {
    private static final int SPEED = 8;
    private static final int NORMAL_WIDTH = 80;
    private static final int NORMAL_HEIGHT = 20;
    private static final int ENLARGED_WIDTH = 120;
    private static final long LASER_COOLDOWN = 300;

    private double x, y;
    private int width, height;
    private boolean enlarged;
    private boolean hasLaser;
    private boolean hasCatch;
    private ArrayList<LaserBeam> laserBeams;
    private long lastLaserTime;

    public Paddle(double x, double y, boolean enlarged) {
        this.x = x;
        this.y = y;
        this.width = enlarged ? ENLARGED_WIDTH : NORMAL_WIDTH;
        this.height = NORMAL_HEIGHT;
        this.enlarged = enlarged;
        this.hasLaser = false;
        this.hasCatch = false;
        this.laserBeams = new ArrayList<>();
        this.lastLaserTime = 0;
    }

    public void moveLeft() {
        x -= SPEED;
        if (x < GameBounds.PLAY_LEFT) x = GameBounds.PLAY_LEFT;
    }

    public void moveRight() {
        x += SPEED;
        if (x + width > GameBounds.PLAY_RIGHT) x = GameBounds.PLAY_RIGHT - width;
    }

    public void enlarge() {
        if (!enlarged) {
            int oldWidth = width;
            width = ENLARGED_WIDTH;
            x -= (width - oldWidth) / 2.0;
            if (x < GameBounds.PLAY_LEFT) x = GameBounds.PLAY_LEFT;
            if (x + width > GameBounds.PLAY_RIGHT) x = GameBounds.PLAY_RIGHT - width;
            enlarged = true;
        }
    }

    public void shrink() {
        if (enlarged) {
            int oldWidth = width;
            width = NORMAL_WIDTH;
            x += (oldWidth - width) / 2.0;
            enlarged = false;
        }
    }

    public void enableLaser() { hasLaser = true; }
    public void disableLaser() { hasLaser = false; }
    public void enableCatch() { hasCatch = true; }
    public void disableCatch() { hasCatch = false; }

    public void fireLaser() {
        if (!hasLaser) return;
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastLaserTime >= LASER_COOLDOWN) {
            laserBeams.add(new LaserBeam(x + 15, y));
            laserBeams.add(new LaserBeam(x + width - 15, y));
            lastLaserTime = currentTime;
        }
    }

    public void update() {
        Iterator<LaserBeam> it = laserBeams.iterator();
        while (it.hasNext()) {
            LaserBeam laser = it.next();
            laser.update();
            if (!laser.isActive() || laser.getY() < 0) it.remove();
        }
    }

    public void render(GraphicsContext gc) {
        if (hasLaser) {
            gc.setFill(Color.CYAN);
            gc.fillRoundRect(x, y, width, height, 10, 10);
            gc.setFill(Color.RED);
            gc.fillRect(x + 10, y, 5, 5);
            gc.fillRect(x + width - 15, y, 5, 5);
        } else {
            gc.setFill(enlarged ? Color.DODGERBLUE : Color.CYAN);
            gc.fillRoundRect(x, y, width, height, 10, 10);
        }
        for (LaserBeam laser : laserBeams) laser.render(gc);
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public boolean hasLaser() { return hasLaser; }
    public boolean hasCatch() { return hasCatch; }
    public boolean isEnlarged() { return enlarged; }
    public ArrayList<LaserBeam> getLasers() { return laserBeams; }
}