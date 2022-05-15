package GameObject;

import javax.swing.*;
import java.awt.*;

public class Tank extends WObject{
	private State state;
	private final int playerNumber;
	public Tank(int x, int y, int playerNumber) {
		super(x, y);
		state = new IdleState();
		this.playerNumber = playerNumber;
	}

	public void turnNorth() { state = new TurnNorthState(); }

	public void turnSouth() { state = new TurnSouthState(); }

	public void turnWest() { state = new TurnWestState(); }

	public void turnEast() { state = new TurnEastState(); }

	public void stop() { state = new IdleState(); }

	public void move() {
		setX(getX() + state.getDx());
		setY(getY() + state.getDy());
	}

	public boolean checkState(String stateName) {
		return state.getClass().getName().equals("GameObject.Tank$" + stateName);
	}

	public boolean isMoveNorth() { return checkState("TurnNorthState"); }

	public boolean isMoveSouth() { return checkState("TurnSouthState"); }

	public boolean isMoveWest() { return checkState("TurnWestState"); }

	public boolean isMoveEast() { return checkState("TurnEastState"); }

	public boolean isIdle() { return checkState("IdleState"); }

	public State getState() { return state; }

	abstract public class State {
		private int dx;
		private int dy;
		private Image image;

		public int getDx() { return dx; }
		public int getDy() { return dy; }
		public void setDx(int dx) { this.dx = dx; }
		public void setDy(int dy) { this.dy = dy; }

		public Image getImage() { return image; }
		public void setImage(Image image) { this.image = image; }
	}

	public class TurnNorthState extends State {
		public TurnNorthState() {
			setDx(-1);
			setDy(0);
			if (playerNumber == 1) {
				setImage(new ImageIcon("images/TankNorth1.png").getImage());
			} else if (playerNumber == 2) {
				setImage(new ImageIcon("images/TankNorth2.png").getImage());
			}
		}
	}

	public class TurnSouthState extends State {
		public TurnSouthState() {
			setDx(1);
			setDy(0);
			if (playerNumber == 1) {
				setImage(new ImageIcon("images/TankSouth1.png").getImage());
			} else if (playerNumber == 2) {
				setImage(new ImageIcon("images/TankSouth2.png").getImage());
			}
		}
	}

	public class TurnWestState extends State {
		public TurnWestState() {
			setDx(0);
			setDy(-1);
			if (playerNumber == 1) {
				setImage(new ImageIcon("images/TankWest1.png").getImage());
			} else if (playerNumber == 2) {
				setImage(new ImageIcon("images/TankWest2.png").getImage());
			}
		}
	}

	public class TurnEastState extends State {
		public TurnEastState() {
			setDx(0);
			setDy(1);
			if (playerNumber == 1) {
				setImage(new ImageIcon("images/TankEast1.png").getImage());
			} else if (playerNumber == 2) {
				setImage(new ImageIcon("images/TankEast2.png").getImage());
			}
		}
	}

	public class IdleState extends State {
		public IdleState() {
			setDx(0);
			setDy(0);
			if (playerNumber == 1) {
				setImage(new ImageIcon("images/TankNorth1.png").getImage());
			} else if (playerNumber == 2) {
				setImage(new ImageIcon("images/TankNorth2.png").getImage());
			}
		}
	}
}
