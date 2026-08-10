package uet.ltnc.arkanoidgame.entities.item.Debuff;

import javafx.scene.paint.Color;
import uet.ltnc.arkanoidgame.entities.ball.Ball;
import uet.ltnc.arkanoidgame.entities.item.Item;
import uet.ltnc.arkanoidgame.entities.paddle.Paddle;

public class Debuff_ReversePaddle extends Item {

    public Debuff_ReversePaddle(double x, double y) {
        super(x, y);
    }

    @Override
    public void apply(Paddle paddle, Ball ball) {
        paddle.applyReverseDirection(5);
    }

    @Override
    protected Color getColor() {
        return Color.ORANGE;
    }

    @Override
    public String getBuffName() {
        return "Reverse Paddle";
    }

    @Override
    public int getDurationSeconds() {
        return 5;
    }

    @Override
    public boolean isBuff() {
        return false;
    }
}