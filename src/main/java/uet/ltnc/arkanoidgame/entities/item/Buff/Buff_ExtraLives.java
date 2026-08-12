package uet.ltnc.arkanoidgame.entities.item.Buff;

import uet.ltnc.arkanoidgame.GamePanel;
import uet.ltnc.arkanoidgame.entities.ball.Ball;
import uet.ltnc.arkanoidgame.entities.item.Item;
import uet.ltnc.arkanoidgame.entities.paddle.Paddle;

public class Buff_ExtraLives extends Item {

    private static final String IMAGE_PATH = "/Images/Items/Buff/ExtraLives.png";
    private static final int EXTRA_LIVES = 1;

    public Buff_ExtraLives(double x, double y) {
        super(x, y, IMAGE_PATH);
    }

    @Override
    public void apply(Paddle paddle, Ball ball) {
        GamePanel.addLives(EXTRA_LIVES);
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
}