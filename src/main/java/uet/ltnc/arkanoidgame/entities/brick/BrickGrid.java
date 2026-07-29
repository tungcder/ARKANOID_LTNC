package uet.ltnc.arkanoidgame.entities.brick;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class BrickGrid {

    private List<Brick> bricks = new ArrayList<>();

    public BrickGrid(int[][] map) {
        double brickWidth = 80;
        double brickHeight = 25;
        double horizontalGap = 5;
        double verticalGap = 5;
        double startX = 40;
        double startY = 50;

        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {

                int brickType = map[row][col];

                //số 0 nghĩa là vị trí trống, không tạo gạch
                if (brickType == 0) {
                    continue;
                }

                double x = col * (brickWidth + horizontalGap) + startX;
                double y = row * (brickHeight + verticalGap) + startY;

                Brick brick = BrickFactory.createBrick(
                        brickType,
                        x,
                        y,
                        brickWidth,
                        brickHeight
                );

                bricks.add(brick);
            }
        }
    }

    public void render(GraphicsContext gc) {
        for (Brick brick : bricks) {
            brick.render(gc);
        }
    }

    public void update() {
        for (Brick brick : bricks) {
            brick.update();
        }
    }

    public boolean isLevelComplete() {
        for (Brick brick : bricks) {
            if (!brick.isDestroyed() && brick.isBreakable()) {
                return false;
            }
        }

        return true;
    }

    public List<Brick> getBricks() {
        return bricks;
    }
}