package uet.ltnc.arkanoidgame.entities.brick;

import javafx.scene.paint.Color;

public class BrickWeak extends Brick {

    public BrickWeak(double x, double y,
                     double width, double height) {
        super(x, y, width, height, 1);
    }

    @Override
    protected Color getColor() {
        return Color.LIGHTGREEN;
    }
}