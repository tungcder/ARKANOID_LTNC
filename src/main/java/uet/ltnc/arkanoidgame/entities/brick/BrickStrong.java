package uet.ltnc.arkanoidgame.entities.brick;

import javafx.scene.paint.Color;

public class BrickStrong extends Brick {

    public BrickStrong(double x, double y,
                       double width, double height) {
        super(x, y, width, height, 3);
    }

    @Override
    protected Color getColor() {
        if (getCurrentHits() == 0) {
            return Color.RED;
        }

        if (getCurrentHits() == 1) {
            return Color.YELLOW;
        }

        return Color.LIGHTGREEN;
    }
}