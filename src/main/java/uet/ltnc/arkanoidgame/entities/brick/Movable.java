package uet.ltnc.arkanoidgame.entities.brick;

public interface Movable {
    void initMovementRange(BrickGrid brickGrid);
    void update();
    void reset();
}