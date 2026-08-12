package uet.ltnc.arkanoidgame.entities.brick;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import uet.ltnc.arkanoidgame.entities.item.Item;
import uet.ltnc.arkanoidgame.ThemeManager;

import java.io.InputStream;

public abstract class Brick {

    protected double x, y;
    protected double width, height;

    protected int hitsRequired;
    protected int currentHits = 0;
    protected boolean destroyed = false;
    protected boolean breakable = true;

    protected Image[] damageImages;
    protected Image image;
    protected Item powerup;

    public Brick(double x, double y, double width, double height, int hitsRequired, String... imageFileNames) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.hitsRequired = hitsRequired;
        this.breakable = true;

        this.damageImages = new Image[imageFileNames.length];
        for (int i = 0; i < imageFileNames.length; i++) {
            String assetPath = "brick/" + imageFileNames[i];
            this.damageImages[i] = loadImage(ThemeManager.getImagePath(assetPath));
        }

        this.image = damageImages[0];
    }

    protected static Image loadImage(String fullPath) {
        try (InputStream inputStream = Brick.class.getResourceAsStream(fullPath)) {
            if (inputStream == null) {
                System.err.println("Không tìm thấy tài nguyên: " + fullPath);
                return null;
            }

            Image img = new Image(inputStream);
            if (img.isError()) {
                System.err.println("Lỗi khi tải ảnh: " + fullPath);
                return null;
            }

            return img;
        } catch (Exception e) {
            System.err.println("Lỗi I/O khi tải ảnh: " + fullPath);
            e.printStackTrace();
            return null;
        }
    }

    public boolean hit() {
        if (!breakable || destroyed) return false;
        currentHits++;

        if (damageImages != null && currentHits < hitsRequired) {
            image = damageImages[Math.min(currentHits, damageImages.length - 1)];
        }

        if (currentHits >= hitsRequired) {
            destroyed = true;
            onDestroyed();
            return true;
        }

        return false;
    }

    protected void onDestroyed() {
    }

    public void render(GraphicsContext gc) {
        if (destroyed) return;

        if (image != null) {
            gc.drawImage(image, x, y, width, height);
        } else {
            gc.setFill(getFallbackColor());
            gc.fillRect(x, y, width, height);
            gc.setStroke(Color.BLACK);
            gc.strokeRect(x, y, width, height);
        }
    }

    protected abstract Color getFallbackColor();

    public void update() {
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public int getHitsRequired() {
        return hitsRequired;
    }

    public boolean isBreakable() {
        return breakable;
    }

    public void setBreakable(boolean value) {
        this.breakable = value;
    }

    public Item getPowerup() {
        return powerup;
    }

    public void setPowerup(Item powerup) {
        this.powerup = powerup;
    }

    public int getHealth() {
        if (destroyed) {
            return 0;
        }
        return hitsRequired - currentHits;
    }

    public void setHealth(int health) {
        if (health <= 0) {
            this.currentHits = this.hitsRequired;
            this.destroyed = true;
        } else {
            this.currentHits = this.hitsRequired - health;
            this.destroyed = false;

            if (damageImages != null && currentHits > 0) {
                image = damageImages[Math.min(currentHits, damageImages.length - 1)];
            } else if (damageImages != null) {
                image = damageImages[0];
            }
        }
    }

    public void destroy() {
        this.destroyed = true;
        this.currentHits = this.hitsRequired;
    }

    public int getCurrentHits() {
        return currentHits;
    }

    public void setCurrentHits(int hits) {
        this.currentHits = Math.max(0, Math.min(hits, hitsRequired));

        if (damageImages != null && currentHits > 0 && currentHits < hitsRequired) {
            image = damageImages[Math.min(currentHits, damageImages.length - 1)];
        } else if (damageImages != null && currentHits == 0) {
            image = damageImages[0];
        }

        if (currentHits >= hitsRequired) {
            destroyed = true;
        }
    }
}