package uet.ltnc.arkanoidgame.entities.item.DeBuff;

import uet.ltnc.arkanoidgame.entities.ball.Ball;
import uet.ltnc.arkanoidgame.entities.item.Item;
import uet.ltnc.arkanoidgame.entities.paddle.Paddle;

public class DeBuff_FastBall extends Item {

    private static final String IMAGE_PATH = "/Images/Items/DeBuff/FastBall.png";
    private static final double SPEED_INCREASE = 2.0;
    private static final double DEBUFF_DURATION = 7.0;

    public DeBuff_FastBall(double x, double y) {
        super(x, y, IMAGE_PATH);
    }

    @Override
    public void apply(Paddle paddle, Ball ball) {
        ball.applySpeedBuff(SPEED_INCREASE, DEBUFF_DURATION);
    }

    @Override
    public String getBuffName() {
        return "Fast Ball";
    }

    @Override
    public int getDurationSeconds() {
        return (int) DEBUFF_DURATION;
    }

    @Override
    public boolean isBuff() {
        return false;
    }
}