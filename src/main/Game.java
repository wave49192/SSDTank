package main;

import GameObject.Bullet;
import GameObject.BulletPool;
import GameObject.Cell;
import GameObject.Tank;
import audio.TankAudioController;
import command.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game extends JFrame {
	private final GridUI gridUI;
	private final BulletPool bulletPool;
	private final List<Bullet> bullets;
	private Board board;
	private final int boardSize = 20;
	private final int barSize = 0;

	public Game() {
		Controller controller = new Controller();
		addKeyListener(controller);
		board = new Board(boardSize, barSize, false);
		bulletPool = new BulletPool();
		bullets = new ArrayList<>();

		gridUI = new GridUI();
		Thread thread = new Thread() {
			@Override
			public void run() {
				while (!board.getIsOver()) {
					gridUI.repaint();
					moving();
					if (board.getAi() != null){
						board.getAi().executeStrategy();
					}
					setDeadTanks();
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

	public void setDeadTanks() {
		for (Tank t: board.getAllTanks()) {
			if (t.getHp() <= 0) {
				t.setDead(true);
			}
		}
	}

	public void moving() {
		if (!board.getIsStart()) {
			return;
		}
		board.moveTank();
		moveBullets();
		cleanupBullets();
	}

	private void moveBullets() {
		for (Bullet bullet : bullets) {
			bullet.move();
		}
	}

	private void cleanupBullets() {
		List<Bullet> toRemove = new ArrayList<>();
		for (Bullet bullet : bullets) {
			Cell cell = board.getCell(bullet.getX(), bullet.getY());
			if (cell.isWall()) {
				toRemove.add(bullet);
			} else if (cell.isBrick()) {
				board.modifyCells(bullet.getX(), bullet.getY());
				toRemove.add(bullet);
			} else if (cell.isSteel()) {
				toRemove.add(bullet);
			}
			for (Tank t: board.getAllTanks()) {
				 if (board.getCell(bullet.getX(), bullet.getY()).isContainTank(t))
				{
					t.setHp(t.getHp() - 1);
					toRemove.add(bullet);
				}
			}
		}
		if (!toRemove.isEmpty()) {
			for (Bullet bullet : toRemove) {
				bullets.remove(bullet);
				bulletPool.releaseBullet(bullet);
			}
		}

	}

	class GridUI extends JPanel {
		private JButton startButton;
		private JButton singlePlayerButton;
		private JButton multiPlayerButton;
		public static final int CELL_PIXEL_SIZE = 30;
		private final Image imageBrick;
		private final Image imageSteel;
		private final Image imageTree;
		private final Image imageBullet;
		private final Image imageDead;
		private List<List<Image>> playerImages = new ArrayList<>();
		private List<Image> enemyImages;

		public GridUI() {
			startButton = new JButton("Start");
			add(startButton);
			startButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					startButton.setEnabled(false);
					Game.this.requestFocus();
				}
			});
			multiPlayerButton = new JButton("Multiplayer Mode");
			singlePlayerButton = new JButton("Single Player Mode");
			add(singlePlayerButton);
			singlePlayerButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					startButton.setEnabled(true);
					singlePlayerButton.setEnabled(false);
					multiPlayerButton.setEnabled(true);

					board = new Board(boardSize, barSize, false);
					start();
				}
			});
			add(multiPlayerButton);
			multiPlayerButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					startButton.setEnabled(true);
					singlePlayerButton.setEnabled(true);
					multiPlayerButton.setEnabled(false);

					board = new Board(boardSize, barSize, true);
					start();
				}
			});
			setPreferredSize(new Dimension(boardSize * CELL_PIXEL_SIZE,
					(boardSize) * CELL_PIXEL_SIZE));
			imageTree = new ImageIcon("images/tree.jpg").getImage();
			imageBrick = new ImageIcon("images/break_brick.jpg").getImage();
			imageSteel = new ImageIcon("images/solid_brick.jpg").getImage();
			imageBullet = new ImageIcon("images/enemy_bullet.png").getImage();
			imageDead = new ImageIcon("images/dead.png").getImage();


			playerImages.add(new ArrayList<>(Arrays.asList(
					new ImageIcon("images/TankNorth1.png").getImage(),
					new ImageIcon("images/TankSouth1.png").getImage(),
					new ImageIcon("images/TankWest1.png").getImage(),
					new ImageIcon("images/TankEast1.png").getImage()
			)));
			playerImages.add(new ArrayList<>(Arrays.asList(
					new ImageIcon("images/TankNorth2.png").getImage(),
					new ImageIcon("images/TankSouth2.png").getImage(),
					new ImageIcon("images/TankWest2.png").getImage(),
					new ImageIcon("images/TankEast2.png").getImage()
			)));
			enemyImages = Arrays.asList(
					new ImageIcon("images/EnemyNorth.png").getImage(),
					new ImageIcon("images/EnemySouth.png").getImage(),
					new ImageIcon("images/EnemyWest.png").getImage(),
					new ImageIcon("images/EnemyEast.png").getImage()
			);
		}

		@Override
		public void paint(Graphics g) {
			super.paint(g);

			g.drawString("Tank Game", 10, 20);
			for (int row = barSize; row < boardSize; ++row) {
				for (int col = 1; col < boardSize; ++col) {
					paintCell(g, row, col);
				}
			}
			paintBullets(g);
		}

		private void paintCell(Graphics g, int row, int col) {
			int x = row * CELL_PIXEL_SIZE;
			int y = col * CELL_PIXEL_SIZE;

			Cell cell = board.getCell(row, col);
			List<Tank> playerTanks = board.getPlayerTanks();
			List<Tank> enemyTanks = board.getEnemyTanks();
			if (cell.isWall()) {
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
			for (int i = 0; i < playerTanks.size(); ++i) {
				Tank t = playerTanks.get(i);
				if (t.isDead()) {
					int deadX = t.getX() * CELL_PIXEL_SIZE;
					int deadY = t.getY() * CELL_PIXEL_SIZE;
					g.drawImage(imageDead, deadX, deadY, CELL_PIXEL_SIZE,
							CELL_PIXEL_SIZE, Color.BLACK, null);
					continue;
				}
				if (cell.isBush() && cell.isContainTank(t)) {
					g.drawImage(imageTree, x, y, CELL_PIXEL_SIZE,
							CELL_PIXEL_SIZE, Color.BLACK, null);
				} else if (cell.isBush() && cell.isContainTank(t)) {
					g.drawImage(imageTree, x, y, CELL_PIXEL_SIZE,
							CELL_PIXEL_SIZE, Color.BLACK, null);
					g.drawImage(playerImages.get(i).get(0), x, y, CELL_PIXEL_SIZE / 2,
							CELL_PIXEL_SIZE / 2, Color.BLACK, null);
				} else if (cell.isContainTank(t)) {
					if (!board.getIsStart()) {
						g.drawImage(playerImages.get(i).get(0), x, y, CELL_PIXEL_SIZE,
								CELL_PIXEL_SIZE, Color.BLACK, null);
					}
					if (t.isMoveNorth()) {
						g.drawImage(playerImages.get(i).get(0), x, y, CELL_PIXEL_SIZE,
								CELL_PIXEL_SIZE, Color.BLACK, null);
					}
					if (t.isMoveSouth()) {
						g.drawImage(playerImages.get(i).get(1), x, y, CELL_PIXEL_SIZE,
								CELL_PIXEL_SIZE, Color.BLACK, null);
					}
					if (t.isMoveWest()) {
						g.drawImage(playerImages.get(i).get(2), x, y, CELL_PIXEL_SIZE,
								CELL_PIXEL_SIZE, Color.BLACK, null);
					}
					if (t.isMoveEast()) {
						g.drawImage(playerImages.get(i).get(3), x, y, CELL_PIXEL_SIZE,
								CELL_PIXEL_SIZE, Color.BLACK, null);
					}
				}
			}
			for (Tank t: enemyTanks) {
				if (t.isDead()) {
					int deadX = t.getX() * CELL_PIXEL_SIZE;
					int deadY = t.getY() * CELL_PIXEL_SIZE;
					g.drawImage(imageDead, deadX, deadY, CELL_PIXEL_SIZE,
							CELL_PIXEL_SIZE, Color.BLACK, null);
					continue;
				}
				if (cell.isBush() && cell.isContainTank(t)) {
					g.drawImage(imageTree, x, y, CELL_PIXEL_SIZE,
							CELL_PIXEL_SIZE, Color.BLACK, null);
				} else if (cell.isContainTank(t)) {
					if (!board.getIsStart()) {
						g.drawImage(enemyImages.get(0), x, y, CELL_PIXEL_SIZE,
								CELL_PIXEL_SIZE, Color.BLACK, null);
					}
					if (t.isMoveNorth()) {
						g.drawImage(enemyImages.get(0), x, y, CELL_PIXEL_SIZE,
								CELL_PIXEL_SIZE, Color.BLACK, null);
					}
					if (t.isMoveSouth()) {
						g.drawImage(enemyImages.get(1), x, y, CELL_PIXEL_SIZE,
								CELL_PIXEL_SIZE, Color.BLACK, null);
					}
					if (t.isMoveWest()) {
						g.drawImage(enemyImages.get(2), x, y, CELL_PIXEL_SIZE,
								CELL_PIXEL_SIZE, Color.BLACK, null);
					}
					if (t.isMoveEast()) {
						g.drawImage(enemyImages.get(3), x, y, CELL_PIXEL_SIZE,
								CELL_PIXEL_SIZE, Color.BLACK, null);
					}
				}
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
					g.drawImage(imageBullet, x + CELL_PIXEL_SIZE / 3, y + CELL_PIXEL_SIZE / 3, CELL_PIXEL_SIZE / 3,
							CELL_PIXEL_SIZE / 3, Color.BLACK, null);
				}
			}
		}
	}

	class Controller extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				Command c = new CommandTurnNorth(board.getPlayerTanks().get(0));
				c.execute();
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				Command c = new CommandTurnSouth(board.getPlayerTanks().get(0));
				c.execute();
			}
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				Command c = new CommandTurnWest(board.getPlayerTanks().get(0));
				c.execute();
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				Command c = new CommandTurnEast(board.getPlayerTanks().get(0));
				c.execute();
			}
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				Command c = new CommandShoot(board.getPlayerTanks().get(0), bulletPool, bullets);
				c.execute();
			}
			if (board.getPlayerTanks().size() == 2) {
				if (e.getKeyCode() == KeyEvent.VK_W) {
					Command c = new CommandTurnNorth(board.getPlayerTanks().get(1));
					c.execute();
				}
				if (e.getKeyCode() == KeyEvent.VK_S) {
					Command c = new CommandTurnSouth(board.getPlayerTanks().get(1));
					c.execute();
				}
				if (e.getKeyCode() == KeyEvent.VK_A) {
					Command c = new CommandTurnWest(board.getPlayerTanks().get(1));
					c.execute();
				}
				if (e.getKeyCode() == KeyEvent.VK_D) {
					Command c = new CommandTurnEast(board.getPlayerTanks().get(1));
					c.execute();
				}
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					Command c = new CommandShoot(board.getPlayerTanks().get(1), bulletPool, bullets);
					c.execute();
				}
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
				Command c = new CommandStop(board.getPlayerTanks().get(0));
				c.execute();
			}
			if (board.getPlayerTanks().size() == 2) {
				if (e.getKeyCode() == KeyEvent.VK_W ||
						e.getKeyCode() == KeyEvent.VK_S ||
						e.getKeyCode() == KeyEvent.VK_A ||
						e.getKeyCode() == KeyEvent.VK_D) {
					Command c = new CommandStop(board.getPlayerTanks().get(1));
					c.execute();
				}
			}
		}
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}
}
