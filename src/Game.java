import GameObject.Bullet;
import GameObject.BulletPool;
import GameObject.Cell;
import audio.Sound;
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
	private BulletPool bulletPool;
	private List<Bullet> bullets;

	private Board board;
	private int boardSize = 20;
	private int barSize = 0;
	private Controller controller;
	Sound sound = new Sound();

	public Game() {
		controller = new Controller();
		addKeyListener(controller);
		board = new Board(boardSize, barSize);
		bulletPool = new BulletPool();
		bullets = new ArrayList<>();
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
		for (Bullet bullet : bullets) {
			bullet.move();

		}
	}

	private void cleanupBullets() {
		List<Bullet> toRemove = new ArrayList<Bullet>();
		for (Bullet bullet : bullets) {
			if (bullet.getX() <= 0 ||
					bullet.getX() >= boardSize - 1 ||
					bullet.getY() <= 0 ||
					bullet.getY() >= boardSize - 1) {
				toRemove.add(bullet);
			} else if (board.getCell(bullet.getX(), bullet.getY()).isBrick()) {
				board.modifyCells(bullet.getX(), bullet.getY());

			} else if (board.getCell(bullet.getX(), bullet.getY()).isSteel()) {
				toRemove.add(bullet);
			}
			else if (board.getCell(bullet.getX(), bullet.getY()).isContainTank(board.getTank1()))
			{

				toRemove.add(bullet);
			}
			else if (board.getCell(bullet.getX(), bullet.getY()).isContainTank(board.getTank2()))
			{

				toRemove.add(bullet);
			}


		}
		if (!toRemove.isEmpty()) {

			for (Bullet bullet2 : toRemove) {
				bullets.remove(bullet2);
				bulletPool.releaseBullet(bullet2);
			}
		}

	}

	class GridUI extends JPanel {
		public static final int CELL_PIXEL_SIZE = 30;
		private final Image imageBrick;
		private final Image imageSteel;
		private final Image imageTree;
		private final Image imageBullet;
		private final Image imageTankNorth1;
		private final Image imageTankEast1;
		private final Image imageTankSouth1;
		private final Image imageTankWest1;

		private final Image imageTankNorth2;
		private final Image imageTankEast2;
		private final Image imageTankSouth2;
		private final Image imageTankWest2;

		private Image lastTank1Move;
		private Image lastTank2Move;


		public GridUI() {
			setPreferredSize(new Dimension(boardSize * CELL_PIXEL_SIZE,
					(boardSize) * CELL_PIXEL_SIZE));
			imageBrick = new ImageIcon("images/break_brick.jpg").getImage();
			imageSteel = new ImageIcon("images/solid_brick.jpg").getImage();
			imageBullet = new ImageIcon("images/enemy_bullet.png").getImage();
			imageTankNorth1 = new ImageIcon("images/TankNorth1.png").getImage();
			imageTankWest1 = new ImageIcon("images/TankWest1.png").getImage();
			imageTankSouth1 = new ImageIcon("images/TankSouth1.png").getImage();
			imageTankEast1 = new ImageIcon("images/TankEast1.png").getImage();

			imageTankNorth2 = new ImageIcon("images/TankNorth2.png").getImage();
			imageTankWest2 = new ImageIcon("images/TankWest2.png").getImage();
			imageTankSouth2 = new ImageIcon("images/TankSouth2.png").getImage();
			imageTankEast2 = new ImageIcon("images/TankEast2.png").getImage();
			imageTree = new ImageIcon("images/tree.jpg").getImage();
			lastTank1Move = imageTankNorth1;
			lastTank2Move = imageTankNorth2;
		}

		@Override
		public void paint(Graphics g) {
			super.paint(g);

			g.drawString("Tank Game", 10, 20);
			for (int row = barSize; row < boardSize; ++row) {
				for (int col = 0; col < boardSize; ++col) {
					paintCell(g, row, col);
				}
			}
			paintBullets(g);
		}

		private void paintCell(Graphics g, int row, int col) {
			int x = row * CELL_PIXEL_SIZE;
			int y = col * CELL_PIXEL_SIZE;

			Cell cell = board.getCell(row, col);
			if (cell.isBush() && cell.isContainTank(board.getTank1())) {
				g.drawImage(imageTree, x, y, CELL_PIXEL_SIZE,
						CELL_PIXEL_SIZE, Color.BLACK, null);
			} else if (cell.isBush() && cell.isContainTank(board.getTank2())) {
				g.drawImage(imageTree, x, y, CELL_PIXEL_SIZE,
						CELL_PIXEL_SIZE, Color.BLACK, null);
			} else if (cell.isContainTank(board.getTank1())) {
				if (!board.getIsStart()) {
					g.drawImage(imageTankNorth1, x, y, CELL_PIXEL_SIZE,
							CELL_PIXEL_SIZE, Color.BLACK, null);
				}
				if (board.getTank1().isMoveNorth()) {
					g.drawImage(imageTankNorth1, x, y, CELL_PIXEL_SIZE,
							CELL_PIXEL_SIZE, Color.BLACK, null);
					lastTank1Move = imageTankNorth1;
				}
				if (board.getTank1().isMoveSouth()) {
					g.drawImage(imageTankSouth1, x, y, CELL_PIXEL_SIZE,
							CELL_PIXEL_SIZE, Color.BLACK, null);
					lastTank1Move = imageTankSouth1;
				}
				if (board.getTank1().isMoveWest()) {
					g.drawImage(imageTankWest1, x, y, CELL_PIXEL_SIZE,
							CELL_PIXEL_SIZE, Color.BLACK, null);
					lastTank1Move = imageTankWest1;
				}
				if (board.getTank1().isMoveEast()) {
					g.drawImage(imageTankEast1, x, y, CELL_PIXEL_SIZE,
							CELL_PIXEL_SIZE, Color.BLACK, null);
					lastTank1Move = imageTankEast1;
				}
				if (board.getTank1().isIdle()) {
					g.drawImage(lastTank1Move, x, y, CELL_PIXEL_SIZE,
							CELL_PIXEL_SIZE, Color.BLACK, null);
				}
			} else if (cell.isContainTank(board.getTank2())) {
				if (!board.getIsStart()) {
					g.drawImage(imageTankNorth2, x, y, CELL_PIXEL_SIZE,
							CELL_PIXEL_SIZE, Color.BLACK, null);
				}
				if (board.getTank2().isMoveNorth()) {
					g.drawImage(imageTankNorth2, x, y, CELL_PIXEL_SIZE,
							CELL_PIXEL_SIZE, Color.BLACK, null);
					lastTank2Move = imageTankNorth2;
				}
				if (board.getTank2().isMoveSouth()) {
					g.drawImage(imageTankSouth2, x, y, CELL_PIXEL_SIZE,
							CELL_PIXEL_SIZE, Color.BLACK, null);
					lastTank2Move = imageTankSouth2;
				}
				if (board.getTank2().isMoveWest()) {
					g.drawImage(imageTankWest2, x, y, CELL_PIXEL_SIZE,
							CELL_PIXEL_SIZE, Color.BLACK, null);
					lastTank2Move = imageTankWest2;
				}
				if (board.getTank2().isMoveEast()) {
					g.drawImage(imageTankEast2, x, y, CELL_PIXEL_SIZE,
							CELL_PIXEL_SIZE, Color.BLACK, null);
					lastTank2Move = imageTankEast2;
				}
				if (board.getTank2().isIdle()) {
					g.drawImage(lastTank2Move, x, y, CELL_PIXEL_SIZE,
							CELL_PIXEL_SIZE, Color.BLACK, null);
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
		}

		public void paintBullets(Graphics g) {
			for (Bullet b : bullets) {
				int x = b.getX() * CELL_PIXEL_SIZE;
				int y = b.getY() * CELL_PIXEL_SIZE;

				if (board.getCell(b.getX(), b.getY()).isBush()) {

					g.drawImage(imageTree, x, y, CELL_PIXEL_SIZE,
							CELL_PIXEL_SIZE, Color.BLACK, null);
				} else {
					g.drawImage(imageBullet, x, y, CELL_PIXEL_SIZE,
							CELL_PIXEL_SIZE, Color.BLACK, null);
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
				Command c = new CommandShoot(board.getTank2(), bulletPool, bullets);
				c.execute();
			}
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				Command c = new CommandShoot(board.getTank1(), bulletPool, bullets);
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
