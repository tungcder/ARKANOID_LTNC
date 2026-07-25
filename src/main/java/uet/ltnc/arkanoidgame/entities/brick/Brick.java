package uet.ltnc.arkanoidgame.entities.brick;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Brick {
    private double x, y, width, height;
    private boolean destroyed;
    private int hitsRequired;   // số lần bóng cần đập để phá gạch
    private int currentHits;     // số lần gạch đã bị đập

    public Brick(double x, double y, double width, double height) {
        this(x, y, width, height, 1);
    }

    protected Brick(double x, double y, double width, double height, int hitsRequired){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.hitsRequired = Math.max(1, hitsRequired);
        this.currentHits = 0;
        this.destroyed = false;
    }
/*
ghi nhận số lầ bóng đập vào gạch
trả về true kho gạch vỡ trong lần đập này
 */
    public boolean hit() {
        if (destroyed || !isBreakable()) {
            return false;
        }

        currentHits++;

        if (currentHits >= hitsRequired) {
            destroyed = true;
            return true;
        }

        return false;
    }

    public void render(GraphicsContext gc) {
        if (!destroyed) {
            gc.setFill(getColor());
            gc.fillRect(x, y, width, height);
            gc.setStroke(Color.WHITE);
            gc.strokeRect(x, y, width, height);
        }
    }

    protected Color getColor() {
        return Color.DEEPSKYBLUE;
    }

    public boolean isBreakable() {
        return true;
    }

    public void reset() {
        currentHits = 0;
        destroyed = false;
    }
    public boolean isDestroyed() {
        return destroyed;
    }

    /*
    do ball.java vẫn còn gọi brick.setDestroyed(true) nên giữ lại hàm này
     */
    public void setDestroyed(boolean value) {
        if (value) {
            hit();
        } else {
            reset();
        }
    }

    public int getHitsRequired() { return hitsRequired; }
    public int getCurrentHits() { return currentHits; }
    public int getHealth() { return hitsRequired - currentHits; }
    public double getX() { return x; }
    public double getY() { return y; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }
}