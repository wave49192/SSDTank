package GameObject;

public class Cell extends WObject {
	public Cell(int x, int y) {
		super(x, y);
	}

	public boolean isSteel() {
		return this.getClass().getName().equals("GameObject.Steel");
	}

	public boolean isBrick() {
		return this.getClass().getName().equals("GameObject.Brick");
	}

	public boolean isBush() {
		return this.getClass().getName().equals("GameObject.Bush");
	}

	public boolean isWall() {
		return this.getClass().getName().equals("GameObject.Wall");
	}

	public boolean isContainTank(Tank tank) {
		return x == tank.getX() && y == tank.getY() && !tank.isDead();
	}
}
