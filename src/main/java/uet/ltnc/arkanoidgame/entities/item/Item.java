package uet.ltnc.arkanoidgame.entities.item;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import uet.ltnc.arkanoidgame.entities.ball.Ball;
import uet.ltnc.arkanoidgame.entities.paddle.Paddle;
import uet.ltnc.arkanoidgame.utils.GameConstants;
import javafx.scene.image.Image;
import java.io.InputStream;

public abstract class Item {

    private double x;
    private double y;

    private final double width;
    private final double height;

    private double fallSpeed;
    private boolean collected;

    private Image image;

    protected Item(double x, double y) {
        this(x, y, null);
    }

    protected Item(double x, double y, String imagePath) {
        width = 30;
        height = 30;

        this.x = x - width / 2;
        this.y = y;

        fallSpeed = 2;
        collected = false;
        image = null;

        if (imagePath != null) {
            InputStream input =
                    getClass().getResourceAsStream(imagePath);

            if (input != null) {
                image = new Image(input);
            }
        }
    }

    public void update() {
        if (!collected) {
            y += fallSpeed;
        }
    }

    public void render(GraphicsContext gc) {
        if (collected) {
            return;
        }

        if (image != null) {
            gc.drawImage(image, x, y, width, height);
        } else {
            gc.setFill(getColor());
            gc.fillRect(x, y, width, height);

            gc.setStroke(Color.WHITE);
            gc.strokeRect(x, y, width, height);
        }
    }

    public boolean collidesWith(Paddle paddle) {
        return !collected
                && x < paddle.getX() + paddle.getWidth()
                && x + width > paddle.getX()
                && y < paddle.getY() + paddle.getHeight()
                && y + height > paddle.getY();
    }

    public void collect(Paddle paddle, Ball ball) {
        if (collidesWith(paddle)) {
            apply(paddle, ball);
            collected = true;
        }
    }

    public boolean isActive() {
        return !collected && y <= GameConstants.HEIGHT;
    }

    protected abstract Color getColor();

    public abstract void apply(Paddle paddle, Ball ball);

    public abstract String getBuffName();

    public abstract int getDurationSeconds();

    public abstract boolean isBuff();

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

    public boolean isExtraLife() {
        return false;
    }

    public boolean isCollected() {
        return collected;
    }
}