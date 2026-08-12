package uet.ltnc.arkanoidgame.entities.brick;

import javafx.scene.paint.Color;

public class BrickMedium extends Brick {

    public BrickMedium(double x, double y,
                       double width, double height) {
        super(x, y, width, height, 2);
    }

    @Override
    protected Color getColor() {
        if (getCurrentHits() == 0) {
            return Color.YELLOW;        //chưa bị đập màu vàng
        }

        return Color.LIGHTGREEN;    //bị đập 1 lần về màu BrickWeek
    }
}