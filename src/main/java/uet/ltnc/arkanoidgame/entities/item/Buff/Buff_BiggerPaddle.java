package uet.ltnc.arkanoidgame.entities.item.Buff;

import javafx.scene.paint.Color;
import uet.ltnc.arkanoidgame.entities.ball.Ball;
import uet.ltnc.arkanoidgame.entities.item.Item;
import uet.ltnc.arkanoidgame.entities.paddle.Paddle;

public class Buff_BiggerPaddle extends Item {

    private static final String IMAGE_PATH =
            "/Images/Items/Buff/BiggerPaddle.png";

    private static final double SIZE_MULTIPLIER = 1.25;
    private static final int DURATION_SECONDS = 7;

    public Buff_BiggerPaddle(double x, double y) {
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
        return Color.LIGHTGREEN;
    }

    @Override
    public String getBuffName() {
        return "Bigger Paddle";
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