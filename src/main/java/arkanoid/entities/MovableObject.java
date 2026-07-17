package arkanoid.entities;

public abstract class MovableObject extends GameObject {
	protected double dx, dy;

	public MovableObject(int x, int y, int w, int h) {
		super(x, y, w, h);
		this.dx = 0;
		this.dy = 0;
	}

	@Override
	public void update() {
		x += (int) dx;
		y += (int) dy;
	}

	public void setDx(double dx) { this.dx = dx; }
	public void setDy(double dy) { this.dy = dy; }
	public double getDx() { return dx; }
	public double getDy() { return dy; }
}

