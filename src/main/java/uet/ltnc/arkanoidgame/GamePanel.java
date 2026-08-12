package uet.ltnc.arkanoidgame;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import uet.ltnc.arkanoidgame.entities.ball.Ball;
import uet.ltnc.arkanoidgame.entities.brick.BrickGrid;
import uet.ltnc.arkanoidgame.entities.item.Item;
import uet.ltnc.arkanoidgame.entities.map.MapManager;
import uet.ltnc.arkanoidgame.entities.paddle.Paddle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GamePanel extends Canvas {

    private Paddle paddle;
    private Ball ball;
    private BrickGrid bricks;
    private MapManager mapManager;

    private final List<Item> items = new ArrayList<>();

    private static final int INITIAL_LIVES = 3;
    private static final int MAX_LIVES = 5;

    public static int playerLives = INITIAL_LIVES;

    public static void addLives(int amount) {
        playerLives += amount;

        if (playerLives > MAX_LIVES) {
            playerLives = MAX_LIVES;
        }
    }

    public GamePanel() {
        super(800, 600);

        paddle = new Paddle(350, 550, 100, 15);
        ball = new Ball(390, 300, 10);

        mapManager = new MapManager();
        bricks = new BrickGrid(mapManager.getCurrentMapPath());

        setFocusTraversable(true);

        setOnKeyPressed(e -> paddle.addKey(e.getCode()));
        setOnKeyReleased(e -> paddle.removeKey(e.getCode()));
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

        Item spawned = ball.checkCollision(bricks);

        if (spawned != null) {
            items.add(spawned);
        }

        updateItems();

        checkLevelTransition();
    }

    private void updateItems() {
        Iterator<Item> iter = items.iterator();

        while (iter.hasNext()) {
            Item item = iter.next();

            item.update();

            if (item.getY() > getHeight()) {
                iter.remove();
            } else if (item.collidesWith(paddle)) {
                item.apply(paddle, ball);
                iter.remove();
            }
        }
    }

    private void render(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, getWidth(), getHeight());

        bricks.render(gc);
        paddle.render(gc);
        ball.render(gc);

        for (Item item : items) {
            item.render(gc);
        }
    }

    private void checkLevelTransition() {
        if (!bricks.isLevelComplete()) {
            return;
        }

        if (mapManager.hasNextLevel()) {
            mapManager.nextLevel(bricks);
        }
    }
}