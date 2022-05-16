package command;

import GameObject.Bullet;
import GameObject.Tank;

public class CommandShoot extends Command {

    public CommandShoot(Tank tank) { super(tank); }

    @Override
    public void execute() { getTank().shoot(); }
}
