package uet.ltnc.arkanoidgame;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import uet.ltnc.arkanoidgame.entities.ball.Ball;
import uet.ltnc.arkanoidgame.entities.brick.BrickGrid;
import uet.ltnc.arkanoidgame.entities.brick.BrickPowerup;
import uet.ltnc.arkanoidgame.entities.item.ItemManager;
import uet.ltnc.arkanoidgame.entities.item.buff.BiggerPaddle;
import uet.ltnc.arkanoidgame.entities.map.MapManager;
import uet.ltnc.arkanoidgame.entities.paddle.Paddle;

public class GamePanel extends Canvas {

    private Paddle paddle;
    private Ball ball;
    private BrickGrid bricks;
    private MapManager mapManager;
    private ItemManager itemManager;

    public GamePanel() {
        super(800, 600);

        paddle = new Paddle(350, 550, 100, 15);
        ball = new Ball(390, 300, 10);

        mapManager = new MapManager();
        bricks = new BrickGrid(
                mapManager.loadCurrentMap()
        );

        itemManager = new ItemManager();

        setFocusTraversable(true);

        setOnKeyPressed(
                e -> paddle.addKey(e.getCode())
        );

        setOnKeyReleased(
                e -> paddle.removeKey(e.getCode())
        );
    }

    public void startGame() {
        GraphicsContext gc = getGraphicsContext2D();

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                render(gc);
            }
        }.start();
    }

    private void update() {
        ball.update();
        paddle.update();
        bricks.update();

        ball.checkCollision(paddle);
        ball.checkCollision(bricks);

        spawnBiggerPaddleItems();

        itemManager.update(paddle, ball);

        checkLevelTransition();
    }

    private void render(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(
                0,
                0,
                getWidth(),
                getHeight()
        );

        bricks.render(gc);
        itemManager.render(gc);
        paddle.render(gc);
        ball.render(gc);
    }

    private void spawnBiggerPaddleItems() {
        for (BrickPowerup powerupBrick
                : bricks.getPendingPowerupDrops()) {

            double itemX =
                    powerupBrick.getX()
                            + powerupBrick.getWidth() / 2;

            double itemY =
                    powerupBrick.getY()
                            + powerupBrick.getHeight();

            BiggerPaddle item =
                    new BiggerPaddle(itemX, itemY);

            itemManager.addItem(item);

            powerupBrick.markItemDropped();
        }
    }

    private void checkLevelTransition() {
        if (bricks.isLevelComplete()
                && mapManager.nextLevel()) {

            bricks = new BrickGrid(
                    mapManager.loadCurrentMap()
            );
        }
    }
}