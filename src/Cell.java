public class Cell {
	private boolean haveTank1;
	private boolean haveTank2;
	private boolean haveBullet;
	private boolean haveTree;
	private boolean haveBrick;
	private boolean haveSteel;
	private boolean haveWall;

	public boolean isHaveTank2() {
		return haveTank2;
	}

	public void setHaveTank2(boolean haveTank2) {
		this.haveTank2 = haveTank2;
	}


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


	public Cell() {
		haveBullet = false;
		haveTank1 = false;
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

	public boolean isHaveTank1() {
		return haveTank1;
	}

	public void setHaveTank1(boolean haveTank1) {
		this.haveTank1 = haveTank1;
	}

}
