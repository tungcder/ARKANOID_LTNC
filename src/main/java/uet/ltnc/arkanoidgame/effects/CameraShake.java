package uet.ltnc.arkanoidgame.effects;

public class CameraShake {
    private double offsetX = 0;
    private double offsetY = 0;
    private int intensity = 0;
    private int duration = 0;
    private int currentTime = 0;
    private boolean active = false;

    public void shake(int intensity, int duration) {
        this.intensity = intensity;
        this.duration = duration;
        this.currentTime = 0;
        this.active = true;
    }

    public void update() {
        if (!active) return;
        
        currentTime++;
        if (currentTime >= duration) {
            stop();
            return;
        }
        
        double decay = 1.0 - ((double) currentTime / duration);
        double currentIntensity = intensity * decay;
        
        offsetX = (Math.random() * 2 - 1) * currentIntensity;
        offsetY = (Math.random() * 2 - 1) * currentIntensity;
    }

    public int getOffsetX() {
        return (int) offsetX;
    }

    public int getOffsetY() {
        return (int) offsetY;
    }

    public void stop() {
        active = false;
        offsetX = 0;
        offsetY = 0;
        currentTime = 0;
        intensity = 0;
        duration = 0;
    }

    public boolean isActive() {
        return active;
    }
}
