package uet.ltnc.arkanoidgame.entities.item.Debuff;

import javafx.scene.paint.Color;
import uet.ltnc.arkanoidgame.entities.ball.Ball;
import uet.ltnc.arkanoidgame.entities.item.Item;
import uet.ltnc.arkanoidgame.entities.paddle.Paddle;

public class Debuff_FastBall extends Item {

    private static final String IMAGE_PATH =
            "/Images/Items/DeBuff/FastBall.png";

    private static final double SPEED_MULTIPLIER = 2.0;
    private static final int DURATION_SECONDS = 7;

    public Debuff_FastBall(double x, double y) {
        super(x, y, IMAGE_PATH);
    }

    @Override
    public void apply(Paddle paddle, Ball ball) {
        ball.applySpeedBuff(
                SPEED_MULTIPLIER,
                DURATION_SECONDS
        );
    }

    @Override
    protected Color getColor() {
        return Color.RED;
    }

    @Override
    public String getBuffName() {
        return "Fast Ball";
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