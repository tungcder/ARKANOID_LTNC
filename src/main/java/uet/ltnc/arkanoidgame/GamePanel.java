package uet.ltnc.arkanoidgame;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import uet.ltnc.arkanoidgame.effects.CameraShake;
import uet.ltnc.arkanoidgame.entities.ball.Ball;
import uet.ltnc.arkanoidgame.entities.brick.Brick;
import uet.ltnc.arkanoidgame.entities.paddle.LaserBeam;
import uet.ltnc.arkanoidgame.entities.paddle.Paddle;
import uet.ltnc.arkanoidgame.entities.powerup.Powerup;
import uet.ltnc.arkanoidgame.entities.powerup.PowerUpFactory;
import uet.ltnc.arkanoidgame.utils.GameBounds;
import uet.ltnc.arkanoidgame.utils.GameManager;
import uet.ltnc.arkanoidgame.utils.LevelManager;

public class GamePanel extends Canvas {
    private ArkanoidGame mainApp;
    private GameManager gameManager;
    private AnimationTimer gameTimer;

    private Paddle paddle;
    private ArrayList<Ball> balls;
    private List<Brick> bricks;
    private ArrayList<Powerup> powerups;
    private int breakableBricksCount;

    private boolean leftPressed, rightPressed, spacePressed;

    // Powerup timers
    private long slowPowerupEndTime = 0;
    private boolean slowPowerupActive = false;
    private long laserPowerupEndTime = 0;
    private boolean laserPowerupActive = false;
    private static final long SLOW_POWERUP_DURATION = 10000;
    private static final long LASER_POWERUP_DURATION = 20000;

    // Combo system
    private int comboCounter = 0;
    private int comboMultiplier = 1;
    private long lastBrickHitTime = 0;
    private static final long COMBO_TIMEOUT = 1000;
    private String comboText = "";
    private int comboTextAlpha = 0;

    // Camera shake
    private CameraShake cameraShake;

    // Game over/victory states
    private boolean showingGameOver = false;
    private boolean showingVictory = false;

    public GamePanel(ArkanoidGame mainApp) {
        super(800, 600);
        this.mainApp = mainApp;
        this.gameManager = new GameManager();
        this.balls = new ArrayList<>();
        this.bricks = new ArrayList<>();
        this.powerups = new ArrayList<>();
        this.cameraShake = new CameraShake();

        setFocusTraversable(true);
        setOnKeyPressed(e -> handleKeyPressed(e.getCode()));
        setOnKeyReleased(e -> handleKeyReleased(e.getCode()));
    }

    public void startNewGame() {
        gameManager.resetGame();
        showingGameOver = false;
        showingVictory = false;
        initializeLevel();
        startGameLoop();
    }

    private void startGameLoop() {
        if (gameTimer != null) gameTimer.stop();
        gameTimer = new AnimationTimer() {
            private long lastUpdate = 0;
            @Override
            public void handle(long now) {
                if (lastUpdate == 0) { lastUpdate = now; return; }
                if (now - lastUpdate >= 16_666_666) {
                    lastUpdate = now;
                    update();
                    render(getGraphicsContext2D());
                }
            }
        };
        gameTimer.start();
    }

    private void initializeLevel() {
        int paddleX = GameBounds.PLAY_LEFT + (GameBounds.PLAY_WIDTH - 80) / 2;
        int paddleY = GameBounds.PLAY_BOTTOM - 80;
        paddle = new Paddle(paddleX, paddleY, false);
        paddle.disableLaser();
        paddle.disableCatch();

        balls.clear();
        Ball ball = new Ball(paddle.getX() + paddle.getWidth() / 2.0, paddle.getY() - 10, gameManager.getCurrentLevel());
        ball.attachToPaddle(paddle);
        ball.restoreNormalSpeed();
        balls.add(ball);

        spacePressed = false;
        slowPowerupEndTime = 0;
        slowPowerupActive = false;
        laserPowerupEndTime = 0;
        laserPowerupActive = false;

        bricks = LevelManager.loadLevel(gameManager.getCurrentLevel());
        breakableBricksCount = 0;
        for (Brick brick : bricks) {
            if (brick.isBreakable()) breakableBricksCount++;
        }
        powerups.clear();
        resetCombo();
    }

    private void update() {
        if (gameManager.isGameOver() || showingGameOver || showingVictory) {
            return;
        }

        cameraShake.update();

        // Combo timeout check
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastBrickHitTime > COMBO_TIMEOUT && comboCounter > 0) {
            resetCombo();
        }

        if (comboTextAlpha > 0) {
            comboTextAlpha -= 3;
            if (comboTextAlpha < 0) comboTextAlpha = 0;
        }

        // Update bricks (for moving bricks)
        for (Brick brick : bricks) {
            brick.update();
        }

        // Check powerup expirations
        if (slowPowerupActive && System.currentTimeMillis() > slowPowerupEndTime) {
            slowPowerupActive = false;
            for (Ball b : balls) { b.restoreNormalSpeed(); }
        }

        if (laserPowerupActive && System.currentTimeMillis() > laserPowerupEndTime) {
            laserPowerupActive = false;
            if (paddle.hasLaser()) {
                paddle.disableLaser();
                paddle.getLasers().clear();
            }
        }

        // Paddle movement
        if (leftPressed) paddle.moveLeft();
        if (rightPressed) paddle.moveRight();

        // Launch ball on space
        if (spacePressed) {
            for (Ball ball : balls) {
                if (ball.isAttached()) {
                    ball.launch();
                    spacePressed = false;
                    paddle.disableCatch();
                }
            }
        }

        // Update balls
        boolean needRespawn = false;
        Iterator<Ball> ballIterator = balls.iterator();
        while (ballIterator.hasNext()) {
            Ball ball = ballIterator.next();
            ball.update();

            if (ball.getY() > GameBounds.PLAY_BOTTOM) {
                ballIterator.remove();
                if (balls.isEmpty()) needRespawn = true;
            }

            if (!needRespawn) {
                checkBallBrickCollision(ball);
            }

            if (!needRespawn && ball.intersects(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight()) && !ball.isAttached()) {
                if (paddle.hasCatch()) {
                    ball.attachToPaddle(paddle);
                } else {
                    ball.bounceOffPaddle(paddle);
                }
            }
        }

        // Handle death & respawn
        if (needRespawn) {
            gameManager.loseLife();
            cameraShake.shake(8, 20);
            spacePressed = false;
            resetAllPowerups();
            resetCombo();

            if (gameManager.getLives() > 0) {
                Ball newBall = new Ball(paddle.getX() + paddle.getWidth() / 2.0, paddle.getY() - 10, gameManager.getCurrentLevel());
                newBall.attachToPaddle(paddle);
                balls.add(newBall);
            } else {
                showingGameOver = true;
            }
        }

        // Update powerups
        Iterator<Powerup> powerupIterator = powerups.iterator();
        while (powerupIterator.hasNext()) {
            Powerup powerup = powerupIterator.next();
            powerup.update();

            if (powerup.intersects(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight())) {
                applyPowerup(powerup);
                powerupIterator.remove();
            } else if (powerup.getY() > getHeight()) {
                powerupIterator.remove();
            }
        }

        // Update paddle and lasers
        if (paddle.hasLaser()) {
            paddle.update();
            checkLaserBrickCollision();
        }

        // Level completion check
        if (breakableBricksCount <= 0) {
            levelCompleted();
        }
    }

    private void checkBallBrickCollision(Ball ball) {
        int maxIterations = 3;
        int iterations = 0;

        while (iterations < maxIterations) {
            Brick hitBrick = null;

            for (Brick brick : bricks) {
                if (ball.intersects(brick.getX(), brick.getY(), brick.getWidth(), brick.getHeight())) {
                    hitBrick = brick;
                    break;
                }
            }

            if (hitBrick == null) break;

            ball.bounceOffBrick(hitBrick);

            if (hitBrick.isBreakable()) {
                hitBrick.hit();

                if (hitBrick.isDestroyed()) {
                    breakableBricksCount--;
                    updateCombo();
                    int points = hitBrick.getPoints() * comboMultiplier;
                    gameManager.addScore(points);
                    bricks.remove(hitBrick);

                    Powerup powerup = PowerUpFactory.createPowerUpFromBrick(hitBrick.getX(), hitBrick.getY(), 0.45);
                    if (powerup != null) powerups.add(powerup);
                } else if (hitBrick.isSilver()) {
                    cameraShake.shake(4, 8);
                }
            }
            iterations++;
        }
    }

    private void checkLaserBrickCollision() {
        for (LaserBeam laser : paddle.getLasers()) {
            Iterator<Brick> brickIterator = bricks.iterator();
            while (brickIterator.hasNext()) {
                Brick brick = brickIterator.next();

                if (laser.intersects(brick.getX(), brick.getY(), brick.getWidth(), brick.getHeight())) {
                    if (brick.isBreakable()) {
                        brick.hit();
                        laser.setActive(false);

                        if (brick.isDestroyed()) {
                            breakableBricksCount--;
                            gameManager.addScore(brick.getPoints());
                            brickIterator.remove();
                        }
                    } else {
                        laser.setActive(false);
                    }
                    break;
                }
            }
        }
    }

    private void applyPowerup(Powerup powerup) {
        switch (powerup.getType()) {
            case ENLARGE:
                if (!paddle.hasLaser()) {
                    paddle.enlarge();
                    gameManager.setPaddleEnlarged(true);
                }
                break;

            case LASER:
                if (paddle.isEnlarged()) {
                    paddle.shrink();
                    gameManager.setPaddleEnlarged(false);
                }
                laserPowerupActive = true;
                laserPowerupEndTime = System.currentTimeMillis() + LASER_POWERUP_DURATION;
                paddle.enableLaser();
                break;

            case CATCH:
                if (balls.size() == 1) {
                    paddle.enableCatch();
                    for (Ball ball : balls) {
                        if (!ball.isAttached()) ball.attachToPaddle(paddle);
                    }
                }
                break;

            case SLOW:
                slowPowerupActive = true;
                slowPowerupEndTime = System.currentTimeMillis() + SLOW_POWERUP_DURATION;
                for (Ball ball : balls) ball.slow();
                break;

            case DUPLICATE:
                if (!paddle.hasCatch() && balls.size() < 10) {
                    int ballsToCreate = Math.min(2, balls.size());
                    for (int i = 0; i < ballsToCreate; i++) {
                        Ball original = balls.get(i);
                        Ball newBall = new Ball(original.getX(), original.getY(), gameManager.getCurrentLevel());
                        if (original.isAttached()) {
                            newBall.attachToPaddle(paddle);
                        } else {
                            newBall.setVelocity(-original.getDx(), original.getDy());
                        }
                        balls.add(newBall);
                    }
                }
                break;

            case BREAK:
                destroyBottomRow();
                break;

            case PLAYER:
                gameManager.addLife();
                break;
        }
    }

    private void destroyBottomRow() {
        if (bricks.isEmpty()) return;

        double maxY = bricks.stream()
                .mapToDouble(Brick::getY)
                .max()
                .orElse(0);

        bricks.removeIf(brick -> {
            if (brick.getY() == maxY && brick.isBreakable()) {
                breakableBricksCount--;
                return true;
            }
            return false;
        });
    }

    private void resetAllPowerups() {
        powerups.clear();
        if (paddle.hasLaser()) {
            paddle.disableLaser();
            paddle.getLasers().clear();
        }
        if (paddle.hasCatch()) {
            paddle.disableCatch();
        }
        if (paddle.isEnlarged()) {
            paddle.shrink();
            gameManager.setPaddleEnlarged(false);
        }
        slowPowerupActive = false;
        slowPowerupEndTime = 0;
        laserPowerupActive = false;
        laserPowerupEndTime = 0;
    }

    private void updateCombo() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastBrickHitTime > COMBO_TIMEOUT) {
            comboCounter = 0;
            comboMultiplier = 1;
        }

        comboCounter++;
        lastBrickHitTime = currentTime;

        if (comboCounter >= 20) {
            comboMultiplier = 5;
            comboText = "AMAZING! x5";
            comboTextAlpha = 255;
            cameraShake.shake(6, 15);
        } else if (comboCounter >= 15) {
            comboMultiplier = 4;
            comboText = "AWESOME! x4";
            comboTextAlpha = 255;
            cameraShake.shake(5, 12);
        } else if (comboCounter >= 10) {
            comboMultiplier = 3;
            comboText = "GREAT! x3";
            comboTextAlpha = 255;
            cameraShake.shake(4, 10);
        } else if (comboCounter >= 5) {
            comboMultiplier = 2;
            comboText = "COMBO! x2";
            comboTextAlpha = 255;
        } else {
            comboMultiplier = 1;
        }
    }

    private void resetCombo() {
        comboCounter = 0;
        comboMultiplier = 1;
        lastBrickHitTime = 0;
        comboText = "";
        comboTextAlpha = 0;
    }

    private void levelCompleted() {
        gameManager.nextLevel();
        if (gameManager.getCurrentLevel() <= 18) {
            initializeLevel();
        } else {
            showingVictory = true;
        }
    }

    private void render(GraphicsContext gc) {
        // Clear background
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, getWidth(), getHeight());

        // Apply Camera Shake
        int shakeX = cameraShake.getOffsetX();
        int shakeY = cameraShake.getOffsetY();
        gc.save();
        gc.translate(shakeX, shakeY);

        // Render Game Entities
        for (Brick brick : bricks) brick.render(gc);
        paddle.render(gc);
        for (Ball ball : balls) ball.render(gc);
        for (Powerup powerup : powerups) powerup.render(gc);

        gc.restore();

        // Draw HUD Overlay
        drawUI(gc);
    }

    private void drawUI(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        gc.setTextAlign(TextAlignment.LEFT);

        gc.fillText("SCORE: " + gameManager.getScore(), 20, 30);
        gc.fillText("LIVES: " + gameManager.getLives(), 20, 50);

        gc.setTextAlign(TextAlignment.RIGHT);
        gc.fillText("LEVEL: " + gameManager.getCurrentLevel(), getWidth() - 20, 30);

        boolean anyBallAttached = false;
        for (Ball ball : balls) {
            if (ball.isAttached()) {
                anyBallAttached = true;
                break;
            }
        }

        if (anyBallAttached) {
            gc.setFont(Font.font("Arial", FontWeight.BOLD, 20));
            gc.setTextAlign(TextAlignment.CENTER);
            gc.setFill(Color.YELLOW);
            gc.fillText("Press SPACE to launch", getWidth() / 2.0, getHeight() / 2.0 + 100);
        }

        // Combo Text
        if (comboCounter >= 5 && comboTextAlpha > 0) {
            gc.setFont(Font.font("Arial", FontWeight.BOLD, 36));
            gc.setTextAlign(TextAlignment.CENTER);
            gc.setFill(Color.rgb(255, 215, 0, comboTextAlpha / 255.0));
            gc.fillText(comboText + " (" + comboCounter + ")", getWidth() / 2.0, getHeight() / 2.0 - 50);
        }

        if (comboMultiplier > 1) {
            gc.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            gc.setTextAlign(TextAlignment.RIGHT);
            gc.setFill(Color.ORANGE);
            gc.fillText("x" + comboMultiplier + " MULTIPLIER", getWidth() - 20, 50);
        }

        // Game Over Overlay
        if (showingGameOver) {
            gc.setFill(Color.rgb(0, 0, 0, 0.8));
            gc.fillRect(0, 0, getWidth(), getHeight());

            gc.setFont(Font.font("Arial", FontWeight.BOLD, 48));
            gc.setTextAlign(TextAlignment.CENTER);
            gc.setFill(Color.RED);
            gc.fillText("GAME OVER", getWidth() / 2.0, getHeight() / 2.0 - 40);

            gc.setFont(Font.font("Arial", FontWeight.BOLD, 24));
            gc.setFill(Color.WHITE);
            gc.fillText("Final Score: " + gameManager.getScore(), getWidth() / 2.0, getHeight() / 2.0 + 20);

            gc.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
            gc.setFill(Color.LIGHTGRAY);
            gc.fillText("Press ENTER to return to Main Menu", getWidth() / 2.0, getHeight() / 2.0 + 70);
        }

        // Victory Overlay
        if (showingVictory) {
            gc.setFill(Color.rgb(0, 0, 0, 0.8));
            gc.fillRect(0, 0, getWidth(), getHeight());

            gc.setFont(Font.font("Arial", FontWeight.BOLD, 48));
            gc.setTextAlign(TextAlignment.CENTER);
            gc.setFill(Color.GOLD);
            gc.fillText("VICTORY!", getWidth() / 2.0, getHeight() / 2.0 - 40);

            gc.setFont(Font.font("Arial", FontWeight.BOLD, 24));
            gc.setFill(Color.WHITE);
            gc.fillText("Final Score: " + gameManager.getScore(), getWidth() / 2.0, getHeight() / 2.0 + 20);

            gc.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
            gc.setFill(Color.LIGHTGRAY);
            gc.fillText("Press ENTER to return to Main Menu", getWidth() / 2.0, getHeight() / 2.0 + 70);
        }
    }

    private void handleKeyPressed(KeyCode code) {
        if (showingGameOver || showingVictory) {
            if (code == KeyCode.ENTER) {
                if (gameTimer != null) gameTimer.stop();
                mainApp.showMenu();
            }
            return;
        }

        if (code == KeyCode.LEFT || code == KeyCode.A) leftPressed = true;
        if (code == KeyCode.RIGHT || code == KeyCode.D) rightPressed = true;

        if (code == KeyCode.SPACE) {
            spacePressed = true;
            if (paddle.hasLaser()) {
                boolean anyBallLaunched = false;
                for (Ball ball : balls) {
                    if (!ball.isAttached()) {
                        anyBallLaunched = true;
                        break;
                    }
                }
                if (anyBallLaunched) {
                    paddle.fireLaser();
                }
            }
        }

        if (code == KeyCode.ESCAPE) {
            if (gameTimer != null) gameTimer.stop();
            mainApp.showMenu();
        }
    }

    private void handleKeyReleased(KeyCode code) {
        if (code == KeyCode.LEFT || code == KeyCode.A) leftPressed = false;
        if (code == KeyCode.RIGHT || code == KeyCode.D) rightPressed = false;
    }
}