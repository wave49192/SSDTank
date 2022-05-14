public abstract class WObject {
	private int x;
	private int y;

	private int dx;
	private int dy;
	boolean isMoveNorth = true;
	boolean isMoveSouth = false;
	boolean isMoveEast = false;
	boolean isMoveWest = false;

	public WObject(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void turnNorth() {
		isMoveNorth = true;
		isMoveSouth = false;
		isMoveEast = false;
		isMoveWest = false;
		dx = -1;
		dy = 0;
	}

	public void turnSouth() {
		isMoveNorth = false;
		isMoveSouth = true;
		isMoveEast = false;
		isMoveWest = false;
		dx = 1;
		dy = 0;
	}

	public void turnWest() {
		isMoveNorth = false;
		isMoveSouth = false;
		isMoveEast = false;
		isMoveWest = true;
		dx = 0;
		dy = -1;
	}

	public void turnEast() {

		isMoveNorth = false;
		isMoveSouth = false;
		isMoveEast = true;
		isMoveWest = false;
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

//	public boolean getIsMoveVertical() { return isMoveVertical; };
//
//	public boolean getIsMoveHorizontal() { return isMoveHorizontal; };

}
