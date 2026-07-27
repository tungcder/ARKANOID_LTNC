package uet.ltnc.arkanoidgame.utils;

public class GameManager {
    private int score = 0;
    private int lives = GameConstants.INITIAL_LIVES;
    private int currentLevel = 1;
    private boolean gameOver = false;
    private boolean paddleEnlarged = false;

    public void resetGame() {
        score = 0;
        lives = GameConstants.INITIAL_LIVES;
        currentLevel = 1;
        gameOver = false;
        paddleEnlarged = false;
    }

    public void addScore(int value) {
        this.score += value;
    }

    public void loseLife() {
        this.lives--;
        if (this.lives <= 0) {
            this.gameOver = true;
        }
    }

    public void addLife() {
        this.lives++;
    }

    public void nextLevel() {
        this.currentLevel++;
        if (this.currentLevel > GameConstants.MAX_LEVEL) {
            this.gameOver = true;
        }
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public boolean isPaddleEnlarged() {
        return paddleEnlarged;
    }

    public void setPaddleEnlarged(boolean paddleEnlarged) {
        this.paddleEnlarged = paddleEnlarged;
    }
}
