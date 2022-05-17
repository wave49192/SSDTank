import GameObject.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Board {
	private Cell[][] cells;
	private final int size;
	private final int barSize;
	private Tank tank1;
	private Tank tank2;
	private List<Tank> playerTanks;
	private final Random random = new Random();

	private boolean isOver;
	private boolean isStart;

	public void modifyCells(int x, int y) {
		this.cells[x][y] = new Cell(x, y);
	}

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
		playerTanks = new ArrayList<Tank>(Arrays.asList(tank1,tank2));
	}

	private void initCells() {
		cells = new Cell[size + barSize][size];
		for (int row = barSize; row < size; ++row) {
			for (int col = 0; col < size; ++col) {
				cells[row][col] = new Cell(row, col);
			}
		}
	}

	private void initTank() {
		tank1 = new Tank(1, size - 2);
	}

	private void initTank2() {
		tank2 = new Tank(1, 1);
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
		for (int i = 0; i < size; i++) {
			cells[0][i] = new Wall(0, i);//แนวตั้งซ้าย
			cells[size - 1][i] = new Wall(size, i);
			cells[i][0] = new Wall(i, 0);
			cells[i][size - 1] = new Wall(i, size - 1);
		}
	}

	public boolean canMoveTank(Tank tank) {
		Cell nextCell = getCell(tank.getX() + tank.getDx(), tank.getY() + tank.getDy());
		return !(nextCell.isBrick() || nextCell.isWall() || nextCell.isSteel());
	}

	public boolean collideTank(Tank tank, Tank otherTank) {
		Cell nextCell = getCell(tank.getX() + tank.getDx(), tank.getY() + tank.getDy());
		return nextCell.isContainTank(otherTank);
	}

	public void moveTank() {
		for (Tank t1: playerTanks) {
			if (playerTanks.size() == 2) {
				for (Tank t2 : playerTanks) {
					if (t1 != t2 && canMoveTank(t1) && !collideTank(t1, t2) && !t1.isIdle()) {
						t1.move();
					}
				}
			} else {
				if (canMoveTank(t1) && !t1.isIdle()) {
					t1.move();
				}
			}
		}
	}

	public Cell getCell(int row, int col) {
		if (row < barSize || col < 0 || row >= size + barSize || col >= size) {
			return null;
		}
		return cells[row][col];
	}

	public List<Tank> getPlayerTanks() {
		return playerTanks;
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
