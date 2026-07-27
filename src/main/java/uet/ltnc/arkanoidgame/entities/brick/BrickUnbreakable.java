package uet.ltnc.arkanoidgame.entities.brick;

import javafx.scene.paint.Color;

public class BrickUnbreakable extends Brick {

    public BrickUnbreakable(double x, double y,
                            double width, double height) {
        super(x, y, width, height, 1);
    }

    @Override
    public boolean isBreakable() {
        return false;
    }

    @Override
    protected Color getColor() {
        return Color.GRAY;
    }
}