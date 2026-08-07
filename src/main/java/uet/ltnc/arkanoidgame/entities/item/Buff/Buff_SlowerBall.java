package uet.ltnc.arkanoidgame.entities.item.Buff;

import javafx.scene.paint.Color;
import uet.ltnc.arkanoidgame.entities.ball.Ball;
import uet.ltnc.arkanoidgame.entities.item.Item;
import uet.ltnc.arkanoidgame.entities.paddle.Paddle;

public class Buff_SlowerBall extends Item {

    private static final String IMAGE_PATH =
            "/Images/Items/Buff/SlowerBall.png";

    private static final double SPEED_MULTIPLIER = 0.5;
    private static final int DURATION_SECONDS = 7;


    public Buff_SlowerBall(double x, double y) {
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
        return Color.BLUE;
    }


    @Override
    public String getBuffName() {
        return "Slower Ball";
    }


    @Override
    public int getDurationSeconds() {
        return DURATION_SECONDS;
    }


    @Override
    public boolean isBuff() {
        return true;
    }
}