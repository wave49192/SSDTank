package GameObject;

import java.util.List;

public class Tank extends WObject{
	private boolean moving;

	public Tank(int x, int y) {
		super(x, y);
		moving = true;
	}
	private int dx;
	private int dy;

	public void turnNorth() {
		dx = -1;
		dy = 0;
	}

	public void turnSouth() {
		dx = 1;
		dy = 0;
	}

	public void turnWest() {
		dx = 0;
		dy = -1;
	}

	public void turnEast() {
		dx = 0;
		dy = 1;
	}

	public void move() {
		moving = true;
		setX(getX() + dx);
		setY(getY() + dy);
	}

	public void stop() {
		moving = false;
	}

	public void shoot(BulletPool bulletPool, List<Bullet> bullets) {
		Bullet bullet = bulletPool.requestBullet(this);
		bullets.add(bullet);
	}

	public int getDx() { return dx; }

	public int getDy() { return dy; }

	public void setMoving(boolean moving) {
		this.moving = moving;
	}

	public boolean isMoveNorth() { return dx == -1 && dy == 0; }

	public boolean isMoveSouth() { return dx == 1 && dy == 0; }

	public boolean isMoveWest() { return dx == 0 && dy == -1; }

	public boolean isMoveEast() { return dx == 0 && dy == 1; }

	public boolean isIdle() { return !moving; }
}
