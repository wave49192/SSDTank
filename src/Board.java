import GameObject.*;

import java.util.Random;

public class Board {
	private Cell[][] cells;
	private final int size;
	private final int barSize;
	private Tank tank1;
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
		initBrick();
		initSteel();
		initTree();
		initTank();
		initTank2();
	}

	private void initCells() {
		cells = new Cell[size + barSize][size];
		for (int row = barSize; row < size + barSize; ++row) {
			for (int col = 0; col < size; ++col) {
				cells[row][col] = new Cell(row, col);
			}
		}
	}

	private void initTank() {
		tank1 = new Tank(2, size-2);
	}
	private void initTank2() {
		tank2 = new Tank(2, 1);
	}

	private boolean hasTank(Cell cell) {
		return cell.isContainTank(tank1) && cell.isContainTank(tank2);
	}

	private void initBrick() {
		for (int i = 0; i <= size * 3; i += 1) {
			int row = random.nextInt(size) + barSize;
			int col = random.nextInt(size);
			Cell cell = getCell(row, col);
			if (!cell.isWall() && !cell.isBrick()) {
				cells[row][col] = new Brick(row, col);
			}
		}
	}

	private void initSteel() {
		for (int i = 0; i <= size * 2; i += 1) {
			int row = random.nextInt(size) + barSize;
			int col = random.nextInt(size);
			Cell cell = getCell(row, col);
			if (!cell.isWall() && !cell.isBrick()) {
				cells[row][col] = new Steel(row, col);
			}
		}
	}
	private void initTree() {
		for (int i = 0; i <= size * 3; i += 1) {
			int row = random.nextInt(size) + barSize;
			int col = random.nextInt(size);
			Cell cell = getCell(row, col);
			if (!cell.isWall() && !cell.isBrick() && !cell.isSteel()) {
				cells[row][col] = new Bush(row, col);
			}
		}
	}


	private void initWall() {
		for (int i = 0; i <= size - 1; i++) {
			cells[1][i] = new Wall(1, i);
			cells[size][i] = new Wall(size, i);
			cells[i + 1][0] = new Wall(i + 1, 0);
			cells[i + 1][size - 1] = new Wall(i + 1, size - 1);
		}
	}

	public boolean canMoveTank(Tank tank, Tank otherTank) {
		Cell nextCell = getCell(tank.getX() + tank.getDx(), tank.getY() + tank.getDy());
		return !(nextCell.isContainTank(otherTank) || nextCell.isBrick() || nextCell.isWall() || nextCell.isSteel());
	}

	public void moveTank1() {
		if (canMoveTank(tank1, tank2)) {
			tank1.move();
		}
	}

	public void moveTank2() {
		if (canMoveTank(tank2, tank1) && !tank2.isIdle()) {
			tank2.move();
		}
	}

	public Cell getCell(int row, int col) {
		if (row < barSize || col < 0 || row >= size + barSize || col >= size) {
			return null;
		}
		return cells[row][col];
	}

	public Tank getTank1() {
		return tank1;
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
