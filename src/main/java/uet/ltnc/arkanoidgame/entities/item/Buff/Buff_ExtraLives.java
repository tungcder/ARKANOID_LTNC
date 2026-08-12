package uet.ltnc.arkanoidgame.entities.item.Buff;

import javafx.scene.paint.Color;
import uet.ltnc.arkanoidgame.entities.ball.Ball;
import uet.ltnc.arkanoidgame.entities.item.Item;
import uet.ltnc.arkanoidgame.entities.paddle.Paddle;

public class Buff_ExtraLives extends Item {

    public Buff_ExtraLives(double x, double y) {
        super(x, y);
    }

    @Override
    public void apply(Paddle paddle, Ball ball) {
        // Xử lý cộng mạng ở GamePanel.
    }

    @Override
    protected Color getColor() {
        return Color.GOLD;
    }

    @Override
    public String getBuffName() {
        return "Extra Life";
    }

    @Override
    public int getDurationSeconds() {
        return 0;
    }

    @Override
    public boolean isBuff() {
        return true;
    }

    @Override
    public boolean isExtraLife() {
        return true;
    }
}