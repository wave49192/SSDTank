import GameObject.Bullet;
import GameObject.Cell;
import GameObject.Tank;
import ObjectPool.BulletPool;
import audio.TankAudioController;
import command.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Game extends JFrame {
	private GridUI gridUI;
	private TankAudioController tankAudioController;
	private Thread thread;

	private Board board;
	private int boardSize = 20;
	private int barSize = 1;
	private Controller controller;


	public Game() {
		controller = new Controller();
		addKeyListener(controller);
		board = new Board(boardSize, barSize);

		tankAudioController = new TankAudioController(board.getTank1());
		tankAudioController.initialSound();

		gridUI = new GridUI();
		thread = new Thread() {
			@Override
			public void run() {
				while (!board.getIsOver()) {
					gridUI.repaint();
					moving();
					tankAudioController.playTankMovementSound();
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		thread.start();
		add(gridUI);
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
	}

	public void start() {
		setVisible(true);
	}

	public void moving() {
		if (!board.getIsStart()) {
			return;
		}
		board.moveTank1();
		board.moveTank2();
		moveBullets();
		cleanupBullets();
	}

	private void moveBullets() {
		for (Tank tank : board.playerTanks) {
			for (Bullet bullet : tank.getBullets()) {
				bullet.move();
				gridUI.repaint();
			}
		}

	}

	private void cleanupBullets() {
		List<Bullet> toRemove = new ArrayList<Bullet>();
		for (Tank tank : board.playerTanks) {
			for (Bullet bullet : tank.getBullets()) {
				if (bullet.getX() <= 0 ||
						bullet.getX() >= 600 ||
						bullet.getY() <= 0 ||
						bullet.getY() >= 600) {
					toRemove.add(bullet);
				}
			}
			for (Bullet bullet : toRemove) {
				tank.getBullets().remove(bullet);
				tank.getBulletPool().releaseBullet(bullet);
			}
		}
	}

	class GridUI extends JPanel {
		public static final int CELL_PIXEL_SIZE = 30;
		private final Image imageBrick;
		private final Image imageSteel;
		private final Image imageTree;
		private final Image imageBullet;

		private Image lastTank1Move;
		private Image lastTank2Move;


		public GridUI() {
			setPreferredSize(new Dimension(boardSize * CELL_PIXEL_SIZE,
					(boardSize + barSize) * CELL_PIXEL_SIZE));
			imageBrick = new ImageIcon("images/break_brick.jpg").getImage();
			imageSteel = new ImageIcon("images/solid_brick.jpg").getImage();
			imageBullet = new ImageIcon("images/Bullet.png").getImage();

			imageTree = new ImageIcon("images/tree.jpg").getImage();
			lastTank1Move = board.getTank1().getState().getImage();
			lastTank2Move = board.getTank2().getState().getImage();
		}

		@Override
		public void paint(Graphics g) {
			super.paint(g);

			g.drawString("WObject.Tank Game", 10, 20);
			for (int row = barSize; row < boardSize + barSize; ++row) {
				for (int col = 0; col < boardSize; ++col) {
					paintCell(g, row, col);
				}
			}
		}

		private void paintCell(Graphics g, int row, int col) {
			int x = col * CELL_PIXEL_SIZE;
			int y = row * CELL_PIXEL_SIZE;

			Cell cell = board.getCell(row, col);
			if (cell.isBush() && cell.isContainTank(board.getTank1())) {
				g.drawImage(imageTree, x, y, CELL_PIXEL_SIZE,
						CELL_PIXEL_SIZE, Color.BLACK, null);
			} else if (cell.isBush() && cell.isContainTank(board.getTank2())) {
				g.drawImage(imageTree, x, y, CELL_PIXEL_SIZE,
						CELL_PIXEL_SIZE, Color.BLACK, null);
			} else if (cell.isContainTank(board.getTank1())) {
				if (board.getTank1().isIdle()) {
					g.drawImage(lastTank1Move, x, y, CELL_PIXEL_SIZE,
							CELL_PIXEL_SIZE, Color.BLACK, null);
				} else {
					g.drawImage(board.getTank1().getState().getImage(), x, y, CELL_PIXEL_SIZE,
							CELL_PIXEL_SIZE, Color.BLACK, null);
					lastTank1Move = board.getTank1().getState().getImage();
				}
			} else if (cell.isContainTank(board.getTank2())) {
				if (board.getTank2().isIdle()) {
					g.drawImage(lastTank2Move, x, y, CELL_PIXEL_SIZE,
							CELL_PIXEL_SIZE, Color.BLACK, null);
				} else {
					g.drawImage(board.getTank2().getState().getImage(), x, y, CELL_PIXEL_SIZE,
							CELL_PIXEL_SIZE, Color.BLACK, null);
					lastTank2Move = board.getTank2().getState().getImage();
				}
			} else if (cell.isWall()) {
				g.setColor(Color.darkGray);
				g.fillRect(x, y, CELL_PIXEL_SIZE, CELL_PIXEL_SIZE);
			} else if (cell.isBrick()) {
				g.drawImage(imageBrick, x, y, CELL_PIXEL_SIZE,
						CELL_PIXEL_SIZE, Color.BLACK, null);
			} else if (cell.isSteel()) {
				g.drawImage(imageSteel, x, y, CELL_PIXEL_SIZE,
						CELL_PIXEL_SIZE, Color.BLACK, null);

			} else if (cell.isBush()) {
				g.drawImage(imageTree, x, y, CELL_PIXEL_SIZE,
						CELL_PIXEL_SIZE, Color.BLACK, null);
			} else {
				g.setColor(Color.black);
				g.fillRect(x, y, CELL_PIXEL_SIZE, CELL_PIXEL_SIZE);
			}
			for (Tank tank : board.playerTanks) {
				for (Bullet b : tank.getBullets()) {
					if (cell.isBulletPassing(b)) {
						g.drawImage(imageBullet, x, y, CELL_PIXEL_SIZE,
								CELL_PIXEL_SIZE, Color.BLACK, null);
					}
				}
			}
		}
	}

	class Controller extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				Command c = new CommandTurnNorth(board.getTank1());
				c.execute();
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				Command c = new CommandTurnSouth(board.getTank1());
				c.execute();
			}
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				Command c = new CommandTurnWest(board.getTank1());
				c.execute();
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				Command c = new CommandTurnEast(board.getTank1());
				c.execute();
			}
			if (e.getKeyCode() == KeyEvent.VK_W) {
				Command c = new CommandTurnNorth(board.getTank2());
				c.execute();
			}
			if (e.getKeyCode() == KeyEvent.VK_S) {
				Command c = new CommandTurnSouth(board.getTank2());
				c.execute();
			}
			if (e.getKeyCode() == KeyEvent.VK_A) {
				Command c = new CommandTurnWest(board.getTank2());
				c.execute();
			}
			if (e.getKeyCode() == KeyEvent.VK_D) {
				Command c = new CommandTurnEast(board.getTank2());
				c.execute();
			}
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {

				Command c = new CommandShoot(board.getTank2());
				c.execute();
			}
			board.setIsStart(true);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			super.keyReleased(e);
			if (e.getKeyCode() == KeyEvent.VK_UP ||
					e.getKeyCode() == KeyEvent.VK_DOWN ||
					e.getKeyCode() == KeyEvent.VK_LEFT ||
					e.getKeyCode() == KeyEvent.VK_RIGHT) {
				Command c = new CommandStop(board.getTank1());
				c.execute();
			}
			if (e.getKeyCode() == KeyEvent.VK_W ||
					e.getKeyCode() == KeyEvent.VK_S ||
					e.getKeyCode() == KeyEvent.VK_A ||
					e.getKeyCode() == KeyEvent.VK_D) {
				Command c = new CommandStop(board.getTank2());
				c.execute();
			}
		}
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}
}
