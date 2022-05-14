public class Cell {
	private boolean haveTank;
	private boolean haveBullet;
	private boolean haveTree;

	public boolean isHaveTree() {
		return haveTree;
	}

	public void setHaveTree(boolean haveTree) {
		this.haveTree = haveTree;
	}

	public boolean isHaveBrick() {
		return haveBrick;
	}

	public void setHaveBrick(boolean haveBrick) {
		this.haveBrick = haveBrick;
	}

	public boolean isHaveSteel() {
		return haveSteel;
	}

	public void setHaveSteel(boolean haveSteel) {
		this.haveSteel = haveSteel;
	}

	private boolean haveBrick;
	private boolean haveSteel;
	private boolean haveWall;

	public Cell() {
		haveBullet = false;
		haveTank = false;
		haveWall = false;
	}

	public boolean isHaveWall() {
		return haveWall;
	}

	public void setHaveWall(boolean haveWall) {
		this.haveWall = haveWall;
	}

	public boolean isHaveBullet() {
		return haveBullet;
	}

	public void setHaveBullet(boolean haveBullet) {
		this.haveBullet = haveBullet;
	}

	public boolean isHaveTank() {
		return haveTank;
	}

	public void setHaveTank(boolean haveTank) {
		this.haveTank = haveTank;
	}

}
