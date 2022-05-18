package GameObject;

import java.util.List;

public class Tank extends WObject{
	private boolean moving;
	private int hp;
	private int playerNumber;
	private boolean dead;

	public Tank(int x, int y) {
		super(x, y);
		hp = 5;
		moving = true;
		dead = false;
	}

	public Tank(int x, int y, int playerNumber) {
		this(x, y);
		this.playerNumber = playerNumber;
	}

	private int dx;
	private int dy;

	public void turnNorth() {
		dx = 0;
		dy = -1;
	}

	public void turnSouth() {
		dx = 0;
		dy = 1;
	}

	public void turnWest() {
		dx = -1;
		dy = 0;
	}

	public void turnEast() {
		dx = 1;
		dy = 0;
	}

	public void move() {
		if (!dead) {
			moving = true;
			x += dx;
			y += dy;
		}

	}

	public void stop() {
		moving = false;
	}

	public void shoot(BulletPool bulletPool, List<Bullet> bullets) {
		if (!dead) {
			Bullet bullet = bulletPool.requestBullet(this);
			bullets.add(bullet);
		}
	}

	public int getDx() { return dx; }

	public int getDy() { return dy; }

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getPlayerNumber() {
		return playerNumber;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public boolean isMoveNorth() { return dx == 0 && dy == -1; }

	public boolean isMoveSouth() { return dx == 0 && dy == 1; }

	public boolean isMoveWest() { return dx == -1 && dy == 0; }

	public boolean isMoveEast() { return dx == 1 && dy == 0; }

	public boolean isIdle() { return !moving; }
}
