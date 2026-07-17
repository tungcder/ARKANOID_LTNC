package arkanoid.factories;

import java.util.List;
import java.util.ArrayList;

public class BrickFactory {
    /**
     * Create a simple grid of bricks.
     * rows x cols, each brick sized w x h, starting at offsetX, offsetY
     */
    public static List<arkanoid.entities.Brick> createSimpleLayout(int rows, int cols, int brickW, int brickH, int offsetX, int offsetY) {
        List<arkanoid.entities.Brick> list = new ArrayList<>();
        int gap = 6;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                int x = offsetX + c * (brickW + gap);
                int y = offsetY + r * (brickH + gap);
                int hits = 1 + (r / 2); // higher rows tougher
                list.add(new arkanoid.entities.Brick(x, y, brickW, brickH, hits));
            }
        }
        return list;
    }
}