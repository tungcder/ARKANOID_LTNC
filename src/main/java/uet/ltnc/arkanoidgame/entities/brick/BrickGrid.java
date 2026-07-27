package uet.ltnc.arkanoidgame.entities.brick;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class BrickGrid {
    private List<Brick> bricks = new ArrayList<>();

    public BrickGrid(int cols, int rows) {
        double brickWidth = 80;
        double brickHeight = 25;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                double x = col * (brickWidth + 5) + 40;
                double y = row * (brickHeight + 5) + 50;
                int brickType;

                if (row < 2) {
                    brickType = BrickFactory.WEAK;
                } else if (row < 4) {
                    brickType = BrickFactory.MEDIUM;
                } else {
                    brickType = BrickFactory.STRONG;
                }

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

    public List<Brick> getBricks() {
        return bricks;
    }
}