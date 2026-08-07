package uet.ltnc.arkanoidgame;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import uet.ltnc.arkanoidgame.entities.ball.Ball;
import uet.ltnc.arkanoidgame.entities.brick.BrickGrid;
import uet.ltnc.arkanoidgame.entities.brick.BrickPowerup;
import uet.ltnc.arkanoidgame.entities.item.ItemManager;
import uet.ltnc.arkanoidgame.entities.item.Buff.Buff_BiggerPaddle;
import uet.ltnc.arkanoidgame.entities.map.MapManager;
import uet.ltnc.arkanoidgame.entities.paddle.Paddle;
import uet.ltnc.arkanoidgame.entities.item.Item;
import uet.ltnc.arkanoidgame.entities.item.Debuff.Debuff_SmallerPaddle;
import uet.ltnc.arkanoidgame.entities.item.Buff.Buff_BiggerBall;
import uet.ltnc.arkanoidgame.entities.item.Debuff.Debuff_SmallerBall;
import uet.ltnc.arkanoidgame.entities.item.Debuff.Debuff_FastBall;
import uet.ltnc.arkanoidgame.entities.item.Buff.Buff_SlowerBall;

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

        spawnPowerupItems();

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

    private void spawnPowerupItems() {
        for (BrickPowerup powerupBrick
                : bricks.getPendingPowerupDrops()) {

            double itemX =
                    powerupBrick.getX()
                            + powerupBrick.getWidth() / 2;

            double itemY =
                    powerupBrick.getY()
                            + powerupBrick.getHeight();

            Item item;

            int itemType = (int) (Math.random() * 6);

            if (itemType == 0) {
                item = new Buff_BiggerPaddle(itemX, itemY);
            } else if (itemType == 1) {
                item = new Debuff_SmallerPaddle(itemX, itemY);
            } else if (itemType == 2) {
                item = new Buff_BiggerBall(itemX, itemY);
            } else if (itemType == 3) {
                item = new Debuff_SmallerBall(itemX, itemY);
            }
            else if (itemType == 4) {
                item = new Debuff_FastBall(itemX, itemY);
            }
            else {
                item = new Buff_SlowerBall(itemX, itemY);
            }

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