package uet.ltnc.arkanoidgame.entities.brick;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import uet.ltnc.arkanoidgame.utils.GameBounds;

public class Brick {
    private static final int BRICK_WIDTH = 60;
    private static final int BRICK_HEIGHT = 20;

    private final BrickType type;
    private int hits;
    private double x, y;
    private double dx;

    public enum BrickType {
        WHITE(1, 1, 50, Color.WHITE, true),
        ORANGE(2, 1, 60, Color.ORANGE, true),
        LIGHT_BLUE(3, 1, 70, Color.LIGHTBLUE, true),
        GREEN(4, 1, 80, Color.GREEN, true),
        RED(5, 1, 90, Color.RED, true),
        BLUE(6, 1, 100, Color.BLUE, true),
        PURPLE(7, 1, 110, Color.PURPLE, true),
        YELLOW(8, 1, 120, Color.YELLOW, true),
        SILVER(9, 3, 150, Color.SILVER, true),
        GOLD(10, 1, 0, Color.GOLDENROD, false),
        MOVING_UNBREAKABLE_RF(11, 1, 0, Color.DARKGRAY, false, 1.5),
        MOVING_UNBREAKABLE_LF(12, 1, 0, Color.DARKGRAY, false, 1.5),
        MOVING_RF(13, 1, 100, Color.SILVER, true, 1.5),
        MOVING_LF(14, 1, 100, Color.SILVER, true, 1.5);

        private final int id;
        private final int maxHits;
        private final int points;
        private final Color color;
        private final boolean breakable;
        private final double initialSpeed;

        BrickType(int id, int maxHits, int points, Color color, boolean breakable) {
            this(id, maxHits, points, color, breakable, 0.0);
        }

        BrickType(int id, int maxHits, int points, Color color, boolean breakable, double initialSpeed) {
            this.id = id;
            this.maxHits = maxHits;
            this.points = points;
            this.color = color;
            this.breakable = breakable;
            this.initialSpeed = initialSpeed;
        }

        public int getId() { return id; }
        public int getMaxHits() { return maxHits; }
        public int getPoints() { return points; }
        public Color getColor() { return color; }
        public boolean isBreakable() { return breakable; }
        public double getInitialSpeed() { return initialSpeed; }
    }

    public Brick(double x, double y, BrickType type) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.hits = type.getMaxHits();
        if (type == BrickType.MOVING_UNBREAKABLE_LF || type == BrickType.MOVING_LF) {
            this.dx = -type.getInitialSpeed();
        } else {
            this.dx = type.getInitialSpeed();
        }
    }

    public static BrickType byId(int id) {
        for (BrickType t : BrickType.values()) {
            if (t.getId() == id) return t;
        }
        return null;
    }

    public void hit() {
        if (type.isBreakable()) {
            hits--;
        }
    }

    public boolean isDestroyed() { return hits <= 0; }
    public boolean isSilver() { return type == BrickType.SILVER; }
    public boolean isBreakable() { return type.isBreakable(); }
    public int getPoints() { return type.getPoints(); }

    public void update() {
        if (type.getInitialSpeed() > 0) {
            x += dx;
            if (x <= GameBounds.PLAY_LEFT || x + BRICK_WIDTH >= GameBounds.PLAY_RIGHT) {
                dx *= -1;
                if (x <= GameBounds.PLAY_LEFT) x = GameBounds.PLAY_LEFT;
                if (x + BRICK_WIDTH >= GameBounds.PLAY_RIGHT) x = GameBounds.PLAY_RIGHT - BRICK_WIDTH;
            }
        }
    }

    public void render(GraphicsContext gc) {
        if (hits < type.getMaxHits() && type.isBreakable()) {
            gc.setGlobalAlpha(0.6);
        }
        gc.setFill(type.getColor());
        gc.fillRect(x, y, BRICK_WIDTH, BRICK_HEIGHT);
        gc.setStroke(Color.BLACK);
        gc.strokeRect(x, y, BRICK_WIDTH, BRICK_HEIGHT);
        gc.setGlobalAlpha(1.0);
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public int getWidth() { return BRICK_WIDTH; }
    public int getHeight() { return BRICK_HEIGHT; }
    public BrickType getType() { return type; }
    public int getHitsRemaining() { return hits; }
    public double getDx() { return dx; }
    public void setHitsRemaining(int hits) { this.hits = hits; }
    public void setDx(double dx) { this.dx = dx; }
}