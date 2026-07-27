package uet.ltnc.arkanoidgame.entities.powerup;

public class PowerUpFactory {
    public static Powerup createPowerUp(Powerup.PowerupType type, double x, double y) {
        return new Powerup(x, y, type);
    }

    // Weighted random:
    // ENLARGE 15%, CATCH 16%, LASER 19%, SLOW 20%, DUPLICATE 15%, PLAYER 8%, BREAK 7%
    public static Powerup createRandomPowerUp(double x, double y) {
        double random = Math.random() * 100;
        if (random < 15) return createPowerUp(Powerup.PowerupType.ENLARGE, x, y);
        else if (random < 31) return createPowerUp(Powerup.PowerupType.CATCH, x, y);
        else if (random < 50) return createPowerUp(Powerup.PowerupType.LASER, x, y);
        else if (random < 70) return createPowerUp(Powerup.PowerupType.SLOW, x, y);
        else if (random < 85) return createPowerUp(Powerup.PowerupType.DUPLICATE, x, y);
        else if (random < 93) return createPowerUp(Powerup.PowerupType.PLAYER, x, y);
        else return createPowerUp(Powerup.PowerupType.BREAK, x, y);
    }

    // 45% chance to drop a powerup from destroyed brick
    public static Powerup createPowerUpFromBrick(double brickX, double brickY, double dropChance) {
        if (Math.random() < dropChance) return createRandomPowerUp(brickX, brickY);
        return null;
    }
}
