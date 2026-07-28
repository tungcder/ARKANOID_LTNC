package uet.ltnc.arkanoidgame.entities.brick;

public class BrickFactory {

    public static final int WEAK = 1;
    public static final int MEDIUM = 2;
    public static final int STRONG = 3;
    public static final int UNBREAKABLE = 4;
    public static final int MOVING = 5;

    private BrickFactory() {
    }

    public static Brick createBrick(
            int type,
            double x,
            double y,
            double width,
            double height) {

        switch (type) {
            case WEAK:
                return new BrickWeak(x, y, width, height);
            case MEDIUM:
                return new BrickMedium(x, y, width, height);
            case STRONG:
                return new BrickStrong(x, y, width, height);
            case UNBREAKABLE:
                return new BrickUnbreakable(x, y, width, height);
            case MOVING:
                return new BrickMove(x, y, width, height);
            default:
                throw new IllegalArgumentException(   //lỗi tham số kh hợp lệ
                        "Loại gạch không hợp lệ: " + type
                );
        }
    }
}