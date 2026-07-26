package uet.ltnc.arkanoidgame.entities.brick;

public class BrickFactory {

    public static final int WEAK = 1;

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

            default:
                throw new IllegalArgumentException(   //lỗi tham số kh hợp lệ
                        "Loại gạch không hợp lệ: " + type
                );
        }
    }
}