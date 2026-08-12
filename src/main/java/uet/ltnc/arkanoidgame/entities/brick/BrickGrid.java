package uet.ltnc.arkanoidgame.entities.brick;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import javafx.scene.canvas.GraphicsContext;

public class BrickGrid {

    private List<Brick> bricks = new ArrayList<>();

    private static final double BRICK_WIDTH = 100;
    private static final double BRICK_HEIGHT = 50;
    private static final double GRID_OFFSET_X = 0;
    private static final double GRID_OFFSET_Y = 0;

    public BrickGrid(String csvPath) {
        loadFrom(csvPath);
    }

    private void updateMovingBricks() {
        for (Brick brick : bricks) {
            if (brick instanceof BrickMove) {
                ((BrickMove) brick).initMovementRange(this);
            }
        }
    }

    public void loadFrom(String csvPath) {
        bricks.clear();

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(getClass().getResourceAsStream(csvPath)))) {

            if (br == null) {
                System.err.println("Cannot find CSV file: " + csvPath);
                return;
            }

            String line;
            int row = 0;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                for (int col = 0; col < values.length; col++) {
                    int type = Integer.parseInt(values[col].trim());

                    if (type != 0) {
                        double x = GRID_OFFSET_X + col * BRICK_WIDTH;
                        double y = GRID_OFFSET_Y + row * BRICK_HEIGHT;
                        Brick brick = BrickFactory.createBrick(type, x, y, BRICK_WIDTH, BRICK_HEIGHT);
                        bricks.add(brick);
                    }
                }
                row++;
            }

            System.out.println("Loaded " + bricks.size() + " bricks from " + csvPath);

        } catch (IOException | NumberFormatException | NullPointerException e) {
            System.err.println("Error loading CSV: " + e.getMessage());
            e.printStackTrace();
        }

        updateMovingBricks();
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
        int breakableLeft = 0;
        int unbreakableLeft = 0;

        for (Brick brick : bricks) {
            if (!brick.isDestroyed()) {
                if (brick.isBreakable()) {
                    breakableLeft++;
                } else {
                    unbreakableLeft++;
                }
            }
        }

        System.out.println("Breakable bricks: " + breakableLeft + " | Unbreakable: " + unbreakableLeft);
        return breakableLeft == 0;
    }

    public List<Brick> getBricks() {
        return bricks;
    }

    public int getActiveBrickCount() {
        int count = 0;

        for (Brick b : bricks) {
            if (!b.isDestroyed() && b.isBreakable()) {
                count++;
            }
        }

        return count;
    }
}