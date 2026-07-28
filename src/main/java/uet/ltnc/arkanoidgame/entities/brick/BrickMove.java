package uet.ltnc.arkanoidgame.entities.brick;

import javafx.scene.paint.Color;
import uet.ltnc.arkanoidgame.utils.GameConstants;

public class BrickMove extends Brick {

    private double speedX;
    private final double minX;
    private final double maxX;

    public BrickMove(double x, double y,
                     double width, double height) {
        super(x, y, width, height, 1);

        speedX = 1.5;

        minX = Math.max(0, x - 40);

        maxX = Math.min(
                GameConstants.WIDTH - width,
                x + 40
        );
    }

    @Override
    public void update() {
        if (isDestroyed()) {
            return;
        }

        double nextX = getX() + speedX;

        if (nextX <= minX) {
            nextX = minX;
            speedX = Math.abs(speedX);
        } else if (nextX >= maxX) {
            nextX = maxX;
            speedX = -Math.abs(speedX);
        }

        setX(nextX);
    }

    @Override
    protected Color getColor() {
        return Color.VIOLET;
    }
}