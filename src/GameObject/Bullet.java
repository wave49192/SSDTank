package GameObject;

public class Bullet extends WObject {
	public Bullet(int x, int y) {
		super(x, y);
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
		setX(getX() + dx);
		setY(getY() + dy);
	}

	public void stop() {
		dx = 0;
		dy = 0;
	}

	public void refreshState(Tank tank) {
		setX(tank.getX());
		setY(tank.getY());
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
	}

	public int getDx() {
		return dx;
	}

	public int getDy() {
		return dy;
	}
}
