package uet.ltnc.arkanoidgame.entities.powerup;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Powerup {
    private static final int POWERUP_WIDTH = 40;
    private static final int POWERUP_HEIGHT = 20;
    private static final double FALL_SPEED = 3;

    private double x, y;
    private double vy;
    private final PowerupType type;

    public enum PowerupType {
        ENLARGE(Color.rgb(0, 150, 255)),    // Blue
        LASER(Color.rgb(255, 50, 50)),       // Red
        CATCH(Color.rgb(50, 255, 50)),       // Green
        SLOW(Color.rgb(255, 200, 0)),        // Yellow
        DUPLICATE(Color.rgb(255, 100, 255)), // Pink
        BREAK(Color.rgb(150, 75, 0)),        // Brown
        PLAYER(Color.rgb(0, 255, 255));      // Cyan (extra life)

        private final Color color;
        PowerupType(Color color) { this.color = color; }
        public Color getColor() { return color; }
    }

    // Constructor with random type
    public Powerup(double x, double y) {
        this.x = x; this.y = y;
        PowerupType[] types = PowerupType.values();
        this.type = types[(int)(Math.random() * types.length)];
        this.vy = FALL_SPEED;
    }

    // Constructor with specific type
    public Powerup(double x, double y, PowerupType type) {
        this.x = x; this.y = y;
        this.type = type;
        this.vy = FALL_SPEED;
    }

    public void update() { y += vy; }

    public void render(GraphicsContext gc) {
        // Draw colored rounded rectangle
        gc.setFill(type.getColor());
        gc.fillRoundRect(x, y, POWERUP_WIDTH, POWERUP_HEIGHT, 5, 5);
        gc.setStroke(Color.WHITE);
        gc.strokeRoundRect(x, y, POWERUP_WIDTH, POWERUP_HEIGHT, 5, 5);
        // Draw letter indicator
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        String letter = type.name().substring(0, 1);
        gc.fillText(letter, x + POWERUP_WIDTH / 2 - 4, y + POWERUP_HEIGHT / 2 + 5);
    }

    // Collision check: does this powerup intersect with rectangle (paddle bounds)
    public boolean intersects(double rx, double ry, double rw, double rh) {
        return x < rx + rw && x + POWERUP_WIDTH > rx && y < ry + rh && y + POWERUP_HEIGHT > ry;
    }

    // Getters
    public PowerupType getType() { return type; }
    public double getX() { return x; }
    public double getY() { return y; }
    public int getWidth() { return POWERUP_WIDTH; }
    public int getHeight() { return POWERUP_HEIGHT; }
    public void restoreVelocity(double vy) { this.vy = vy; }
}
