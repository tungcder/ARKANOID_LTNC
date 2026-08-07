package uet.ltnc.arkanoidgame.entities.item.debuff;

import javafx.scene.paint.Color;
import uet.ltnc.arkanoidgame.entities.ball.Ball;
import uet.ltnc.arkanoidgame.entities.item.Item;
import uet.ltnc.arkanoidgame.entities.paddle.Paddle;

public class SmallerBall extends Item {

    private static final String IMAGE_PATH =
            "/Images/Items/DeBuff/SmallerBall.png";

    private static final double SIZE_MULTIPLIER = 0.8;
    private static final int DURATION_SECONDS = 7;

    public SmallerBall(double x, double y) {
        super(x, y, IMAGE_PATH);
    }

    @Override
    public void apply(Paddle paddle, Ball ball) {
        ball.applySizeBuff(
                SIZE_MULTIPLIER,
                DURATION_SECONDS
        );
    }

    @Override
    protected Color getColor() {
        return Color.INDIANRED;
    }

    @Override
    public String getBuffName() {
        return "Smaller Ball";
    }

    @Override
    public int getDurationSeconds() {
        return DURATION_SECONDS;
    }

    @Override
    public boolean isBuff() {
        return false;
    }
}