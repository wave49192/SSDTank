import GameObject.Cell;
import audio.Sound;
import audio.TankAudioController;
import command.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Game extends JFrame {
	private GridUI gridUI;
	private TankAudioController tankAudioController;
	private Thread thread;

	private Board board;
	private int boardSize = 20;
	private int barSize = 1;
	private Controller controller;
	Sound sound = new Sound();

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
						Thread.sleep(500);
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
	}

	class GridUI extends JPanel {
		public static final int CELL_PIXEL_SIZE = 30;
		private final Image imageBrick;
		private final Image imageSteel;
		private final Image imageTree;
		private final Image imageBullet;
		private final Image imageTankNorth;
		private final Image imageTankEast;
		private final Image imageTankSouth;
		private final Image imageTankWest;

		private final Image imageTankNorth2;
		private final Image imageTankEast2;
		private final Image imageTankSouth2;
		private final Image imageTankWest2;


		public GridUI() {
			setPreferredSize(new Dimension(boardSize * CELL_PIXEL_SIZE,
					(boardSize + barSize) * CELL_PIXEL_SIZE));
			imageBrick = new ImageIcon("images/break_brick.jpg").getImage();
			imageSteel = new ImageIcon("images/solid_brick.jpg").getImage();
			imageBullet = new ImageIcon("images/Bullet.png").getImage();
			imageTankNorth = new ImageIcon("images/TankNorth1.png").getImage();
			imageTankWest = new ImageIcon("images/TankWest1.png").getImage();
			imageTankSouth = new ImageIcon("images/TankSouth1.png").getImage();
			imageTankEast = new ImageIcon("images/TankEast1.png").getImage();

			imageTankNorth2 = new ImageIcon("images/TankNorth2.png").getImage();
			imageTankWest2 = new ImageIcon("images/TankWest2.png").getImage();
			imageTankSouth2 = new ImageIcon("images/TankSouth2.png").getImage();
			imageTankEast2 = new ImageIcon("images/TankEast2.png").getImage();
			imageTree = new ImageIcon("images/tree.jpg").getImage();
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
			}
			else if (cell.isBush() && cell.isContainTank(board.getTank2())) {
				g.drawImage(imageTree, x, y, CELL_PIXEL_SIZE,
						CELL_PIXEL_SIZE, Color.BLACK, null);
			}
			else if (cell.isContainTank(board.getTank1())) {
				if (!board.getIsStart()) {
					g.drawImage(imageTankNorth, x, y, CELL_PIXEL_SIZE,
							CELL_PIXEL_SIZE, Color.BLACK, null);
				}
				if (board.getTank1().isMoveNorth()) {
					g.drawImage(imageTankNorth, x, y, CELL_PIXEL_SIZE,
							CELL_PIXEL_SIZE, Color.BLACK, null);
				}
				if (board.getTank1().isMoveSouth()) {
					g.drawImage(imageTankSouth, x, y, CELL_PIXEL_SIZE,
							CELL_PIXEL_SIZE, Color.BLACK, null);
				}
				if (board.getTank1().isMoveWest()) {
					g.drawImage(imageTankWest, x, y, CELL_PIXEL_SIZE,
							CELL_PIXEL_SIZE, Color.BLACK, null);
				}
				if (board.getTank1().isMoveEast()) {
					g.drawImage(imageTankEast, x, y, CELL_PIXEL_SIZE,
							CELL_PIXEL_SIZE, Color.BLACK, null);
				}
				if (board.getTank1().isIdle()) {
					g.drawImage(imageTankNorth, x, y, CELL_PIXEL_SIZE,
							CELL_PIXEL_SIZE, Color.BLACK, null);
				}
			}
			else if(cell.isContainTank(board.getTank2())) {
				if (!board.getIsStart()) {
					g.drawImage(imageTankNorth2, x, y, CELL_PIXEL_SIZE,
							CELL_PIXEL_SIZE, Color.BLACK, null);
				}
				if (board.getTank2().isMoveNorth()) {
					g.drawImage(imageTankNorth2, x, y, CELL_PIXEL_SIZE,
							CELL_PIXEL_SIZE, Color.BLACK, null);
				}
				if (board.getTank2().isMoveSouth()) {
					g.drawImage(imageTankSouth2, x, y, CELL_PIXEL_SIZE,
							CELL_PIXEL_SIZE, Color.BLACK, null);
				}
				if (board.getTank2().isMoveWest()) {
					g.drawImage(imageTankWest2, x, y, CELL_PIXEL_SIZE,
							CELL_PIXEL_SIZE, Color.BLACK, null);
				}
				if (board.getTank2().isMoveEast()) {
					g.drawImage(imageTankEast2, x, y, CELL_PIXEL_SIZE,
							CELL_PIXEL_SIZE, Color.BLACK, null);
				}
				if (board.getTank2().isIdle()) {
					g.drawImage(imageTankNorth2, x, y, CELL_PIXEL_SIZE,
							CELL_PIXEL_SIZE, Color.BLACK, null);
				}
			}
			else if (cell.isWall()) {
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
			board.setIsStart(true);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			super.keyReleased(e);
			if (e.getKeyCode() == KeyEvent.VK_UP ||
					e.getKeyCode() == KeyEvent.VK_DOWN ||
					e.getKeyCode() == KeyEvent.VK_LEFT ||
					e.getKeyCode() ==KeyEvent.VK_RIGHT) {
				Command c = new CommandStop(board.getTank1());
				c.execute();
			}
			if (e.getKeyCode() == KeyEvent.VK_W ||
					e.getKeyCode() == KeyEvent.VK_S ||
					e.getKeyCode() == KeyEvent.VK_A ||
					e.getKeyCode() ==KeyEvent.VK_D) {
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
