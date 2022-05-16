package GameObject;

import GameObject.state.State;
import ObjectPool.BulletPool;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Tank extends WObject{
	private State state;
	private final int playerNumber;
	private BulletPool bulletPool;
	private List<Bullet> bullets;

	public Tank(int x, int y, int playerNumber) {
		super(x, y);
		state = new IdleState();
		this.playerNumber = playerNumber;
		bulletPool = new BulletPool();
		bullets = new ArrayList<>();
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

	public void shoot() {
		Bullet b = bulletPool.requestBullet(getX(), getY());
		if (isMoveNorth()) { b.turnNorth(); }
		else if (isMoveSouth()) { b.turnSouth(); }
		else if (isMoveEast()) { b.turnEast(); }
		else if (isMoveWest()) { b.turnWest(); }
		bullets.add(b);
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

	public List<Bullet> getBullets() { return bullets; }

	public BulletPool getBulletPool() { return bulletPool; }

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
