package uet.ltnc.arkanoidgame.entities.item.Debuff;

import javafx.scene.paint.Color;
import uet.ltnc.arkanoidgame.entities.ball.Ball;
import uet.ltnc.arkanoidgame.entities.item.Item;
import uet.ltnc.arkanoidgame.entities.paddle.Paddle;

public class Debuff_SmallerPaddle extends Item {

    private static final String IMAGE_PATH =
            "/Images/Items/DeBuff/SmallerPaddle.png";

    private static final double SIZE_MULTIPLIER = 0.7;
    private static final int DURATION_SECONDS = 7;

    public Debuff_SmallerPaddle(double x, double y) {
        super(x, y, IMAGE_PATH);
    }

    @Override
    public void apply(Paddle paddle, Ball ball) {
        paddle.applySizeBuff(
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
        return "Smaller Paddle";
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