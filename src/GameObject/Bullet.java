package GameObject;

import GameObject.state.State;

import javax.swing.*;

public class Bullet extends WObject {

    private int x;
    private int y;
    private State state;

    private int speed = 1;

    public Bullet(int x, int y) {
        super(x, y);
        this.x = x;
        this.y = y;
        this.state = new IdleState();
    }

    public void turnNorth() { state = new TurnNorthState(); }

    public void turnSouth() { state = new TurnSouthState(); }

    public void turnWest() { state = new TurnWestState(); }

    public void turnEast() { state = new TurnEastState(); }

    public void refreshState(int x, int y, State state) {
        this.x = x;
        this.y = y;
        this.state = state;
    }

    public void move() {
        x += state.getDx() * speed;
        y += state.getDy() * speed;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    public class TurnNorthState extends State {
        public TurnNorthState() {
            setDx(-1);
            setDy(0);
            setImage(new ImageIcon("images/TankNorth1.png").getImage());
        }
    }

    public class TurnSouthState extends State {
        public TurnSouthState() {
            setDx(1);
            setDy(0);
            setImage(new ImageIcon("images/TankSouth1.png").getImage());
        }
    }

    public class TurnWestState extends State {
        public TurnWestState() {
            setDx(0);
            setDy(-1);
            setImage(new ImageIcon("images/TankWest1.png").getImage());
        }
    }

    public class TurnEastState extends State {
        public TurnEastState() {
            setDx(0);
            setDy(1);
            setImage(new ImageIcon("images/TankEast1.png").getImage());
        }
    }

    public class IdleState extends State {
        public IdleState() {
            setDx(0);
            setDy(0);
            setImage(new ImageIcon("images/TankNorth1.png").getImage());
        }
    }
}
