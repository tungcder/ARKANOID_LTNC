package uet.ltnc.arkanoidgame.entities.brick;

import javafx.scene.paint.Color;

public class BrickPowerup extends Brick {

    private boolean itemDropped;

    public BrickPowerup(double x, double y,
                        double width, double height) {
        super(x, y, width, height, 1);
        itemDropped = false;
    }

    @Override
    protected Color getColor() {
        return Color.PURPLE;
    }

    public boolean shouldDropItem() {
        return isDestroyed() && !itemDropped;
    }

    public void markItemDropped() {
        itemDropped = true;
    }

    @Override
    public void reset() {
        super.reset();
        itemDropped = false;
    }
}