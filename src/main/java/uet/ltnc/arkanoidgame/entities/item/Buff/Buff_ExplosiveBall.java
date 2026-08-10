package uet.ltnc.arkanoidgame.entities.item.Buff;

import javafx.scene.paint.Color;
import uet.ltnc.arkanoidgame.entities.ball.Ball;
import uet.ltnc.arkanoidgame.entities.item.Item;
import uet.ltnc.arkanoidgame.entities.paddle.Paddle;

public class Buff_ExplosiveBall extends Item {

    public Buff_ExplosiveBall(double x, double y) {
        super(x, y);
    }

    @Override
    public void apply(Paddle paddle, Ball ball) {
        ball.applyExplosiveBuff(7);
    }

    @Override
    protected Color getColor() {
        return Color.RED;
    }

    @Override
    public String getBuffName() {
        return "Explosive Ball";
    }

    @Override
    public int getDurationSeconds() {
        return 7;
    }

    @Override
    public boolean isBuff() {
        return true;
    }
}