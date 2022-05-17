package GameObject;

public class Bullet extends WObject {
	private Tank tank;
	public Bullet(int x, int y,int dx, int dy) {
		super(x, y);
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
		setX(getX() + dx);
		setY(getY() + dy);
	}

	public void stop() {
		dx = 0;
		dy = 0;
	}

	public int getDx() {
		return dx;
	}

	public int getDy() {
		return dy;
	}

	public boolean isMoveNorth() {
		return dx == -1 && dy == 0;
	}

	public boolean isMoveSouth() {
		return dx == 1 && dy == 0;
	}

	public boolean isMoveWest() {
		return dx == 0 && dy == -1;
	}

	public boolean isMoveEast() {
		return dx == 0 && dy == 1;
	}

	public boolean isIdle() {
		return dx == 0 && dy == 0;
	}

	public Bullet(int x, int y, int dx, int dy, Tank tank) {
		super(x, y);
		setSpeed(6);
		setHitBoxSize(10);
		setDx(dx);
		setDy(dy);
		this.tank = tank;
	}

	public void refreshState(int x, int y, int dx, int dy, Tank tank) {
		setX(x);
		setY(y);
		if (tank.isMoveSouth()) {
			turnSouth();
		}
		if (tank.isMoveNorth()) {
			turnNorth();
		}
		if (tank.isMoveEast()) {
			turnEast();
		}
		if (tank.isMoveWest()) {
			turnWest();
		}

		this.tank = tank;
	}

	public Tank getTank() {
		return tank;
	}
}
