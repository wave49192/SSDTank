package GameObject;

public abstract class WObject {
	private int x;
	private int y;

	private int dx;
	private int dy;

	public WObject(int x, int y) {
		this.x = x;
		this.y = y;
	}

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
		this.x += dx;
		this.y += dy;
	}

	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getDx() { return dx; }

	public int getDy() { return dy; }

	public boolean isMoveNorth() { return dx == -1 && dy == 0; }

	public boolean isMoveSouth() { return dx == 1 && dy == 0; }

	public boolean isMoveWest() { return dx == 0 && dy == -1; }

	public boolean isMoveEast() { return dx == 0 && dy == 1; }

}
