package uet.ltnc.arkanoidgame.entities.item.DeBuff;

import uet.ltnc.arkanoidgame.entities.ball.Ball;
import uet.ltnc.arkanoidgame.entities.item.Item;
import uet.ltnc.arkanoidgame.entities.paddle.Paddle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class DeBuff_ReversePaddle extends Item {

    private static final String IMAGE_PATH = "/Images/Items/DeBuff/ReversePaddle.png";
    private static final int DEBUFF_DURATION = 5;

    public DeBuff_ReversePaddle(double x, double y) {
        super(x, y, IMAGE_PATH);
    }

    @Override
    public void apply(Paddle paddle, Ball ball) {
        paddle.setReverseDirection(true);

        Timeline timeline = new Timeline(new KeyFrame(
                Duration.seconds(DEBUFF_DURATION),
                ae -> paddle.setReverseDirection(false)
        ));
        timeline.play();
    }


    @Override
    public String getBuffName() {
        return "Reverse Control";
    }

    @Override
    public int getDurationSeconds() {
        return DEBUFF_DURATION;
    }

    @Override
    public boolean isBuff() {
        return false;
    }
}