package uet.ltnc.arkanoidgame.entities.brick;

public class BrickFactory {
    public static Brick createBrick(int id, double x, double y) {
        if (id == 0) return null;
        if (id >= 1 && id <= 14) return new Brick(x, y, Brick.byId(id));
        if (id == 15) return createRandomBrick(x, y);
        throw new IllegalArgumentException("Invalid brick ID: " + id);
    }

    public static Brick createRandomBrick(double x, double y) {
        int[] validIds = {1, 2, 3, 4, 5, 6, 7, 8};
        int randomIndex = (int)(Math.random() * validIds.length);
        return createBrick(validIds[randomIndex], x, y);
    }
}