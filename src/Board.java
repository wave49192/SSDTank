import java.util.Random;

public class Board {
	private Cell[][] cells;
	private final int size;
	private final int barSize;
	private Tank tank;
	private Tank tank2;
	private final Random random = new Random();

	private boolean isOver;
	private boolean isStart;

	public Board(int size, int barSize) {
		this.size = size;
		this.barSize = barSize;
		isOver = false;
		initCells();
		initWall();
//		initBrick();
//		initSteel();
//		initTree();
		initTank();
		initTank2();


	}

	private void initCells() {
		cells = new Cell[size + barSize][size];
		for (int row = barSize; row < size + barSize; ++row) {
			for (int col = 0; col < size; ++col) {
				cells[row][col] = new Cell();
			}
		}
	}

	private void initTank() {

		tank = new Tank(2, 1);
		cells[2][1].setHaveSteel(false);
		cells[2][1].setHaveBrick(false);
		cells[2][1].setHaveTank1(true);
	}
	private void initTank2() {
		tank2 = new Tank(2, size-2);
		cells[2][size-2].setHaveSteel(false);
		cells[2][size-2].setHaveBrick(false);
		cells[2][size-2].setHaveTank2(true);
	}

	private void initBrick() {
		for (int i = 0; i <= size * 5; i += 1) {
			int row = random.nextInt(size) + barSize;
			int col = random.nextInt(size);
			Cell cell = getCell(row, col);
			if (!cell.isHaveWall() || !cell.isHaveBrick()) {
				cell.setHaveBrick(true);
			}
		}
	}

	private void initSteel() {
		for (int i = 0; i <= size * 5; i += 1) {
			int row = random.nextInt(size) + barSize;
			int col = random.nextInt(size);
			Cell cell = getCell(row, col);
			if (!cell.isHaveWall() || !cell.isHaveBrick() || !cell.isHaveSteel()) {
				cell.setHaveSteel(true);
			}
		}
	}
	private void initTree() {
		for (int i = 0; i <= size * 5; i += 1) {
			int row = random.nextInt(size) + barSize;
			int col = random.nextInt(size);
			Cell cell = getCell(row, col);
			if (!cell.isHaveWall() || !cell.isHaveBrick() || !cell.isHaveSteel()) {
				cell.setHaveTree(true);
			}
		}
	}


	private void initWall() {
		for (int i = 0; i <= size - 1; i++) {
			cells[1][i].setHaveWall(true);
			cells[size][i].setHaveWall(true);
			cells[i + 1][0].setHaveWall(true);
			cells[i + 1][size - 1].setHaveWall(true);
		}

	}


	public void moveTank1() {
		int xBefore = tank.getX();
		int yBefore = tank.getY();
		Cell cellBeforeMove = getCell(tank.getX(), tank.getY());
		tank.move();
		Cell cell = getCell(tank.getX(), tank.getY());
		try {
			if (cell.isHaveBullet()) {
				cell.setHaveBullet(false);
				isOver = true;
			} else if (cell.isHaveWall() || cell.isHaveBrick() || cell.isHaveSteel()||cell.isHaveTank1()) {
				cell.setHaveTank1(false);
				cellBeforeMove.setHaveTank1(true);
				tank.setPosition(xBefore, yBefore);
			} else {
				cellBeforeMove.setHaveTank1(false);
				cell.setHaveTank1(true);

			}
		} catch (Exception e) {
			e.fillInStackTrace();
			isOver = true;
		}
	}

	public void moveTank2() {
		int xBefore = tank2.getX();
		int yBefore = tank2.getY();
		Cell cellBeforeMove = getCell(tank2.getX(), tank2.getY());
		tank2.move();
		Cell cell = getCell(tank2.getX(), tank2.getY());
		try {
			if (cell.isHaveBullet()) {
				cell.setHaveBullet(false);
				isOver = true;
			} else if (cell.isHaveWall() || cell.isHaveBrick() || cell.isHaveSteel()||cell.isHaveTank2()) {
				cell.setHaveTank2(false);
				cellBeforeMove.setHaveTank2(true);
				tank2.setPosition(xBefore, yBefore);
			} else {
				cellBeforeMove.setHaveTank2(false);
				cell.setHaveTank2(true);

			}
		} catch (Exception e) {
			e.fillInStackTrace();
			isOver = true;
		}
	}

	public Cell getCell(int row, int col) {
		if (row < barSize || col < 0 || row >= size + barSize || col >= size) {
			return null;
		}
		return cells[row][col];
	}

	public Tank getTank1() {
		return tank;
	}
	public Tank getTank2() {
		return tank2;
	}



	public boolean getIsOver() {
		return isOver;
	}

	public boolean getIsStart() {
		return isStart;
	}

	public void setIsStart(boolean isStartValue) {
		isStart = isStartValue;
	}
}
