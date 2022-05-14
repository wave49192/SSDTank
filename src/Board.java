import java.util.Random;
import java.util.*;

public class Board {
	private Cell[][] cells;
	private final int size;
	private final int barSize;
	private Tank tank;
	private final Random random = new Random();

	private boolean isOver;
	private boolean isStart;

	public Board(int size, int barSize) {
		this.size = size;
		this.barSize = barSize;
		isOver = false;
		initCells();
		initWall();
		initBrick();
		initSteel();
		initTree();
		initTank();



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
		cells[2][1].setHaveTank(true);
	}
	private void initTank2() {
		tank = new Tank(2, size-2);
		cells[2][size-2].setHaveSteel(false);
		cells[2][size-2].setHaveBrick(false);
		cells[2][size-2].setHaveTank(true);
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


	public void move() {
		int xBefore = tank.getX();
		int yBefore = tank.getY();
		Cell cellBeforeMove = getCell(tank.getX(), tank.getY());
		tank.move();
		Cell cell = getCell(tank.getX(), tank.getY());
		try {
			if (cell.isHaveBullet()) {
				cell.setHaveBullet(false);
				isOver = true;
			} else if (cell.isHaveWall() || cell.isHaveBrick() || cell.isHaveSteel()||cell.isHaveTank()) {
				cell.setHaveTank(false);
				cellBeforeMove.setHaveTank(true);
				tank.setPosition(xBefore, yBefore);
			} else {
				cellBeforeMove.setHaveTank(false);
				cell.setHaveTank(true);

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

	public Tank getTank() {
		return tank;
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
