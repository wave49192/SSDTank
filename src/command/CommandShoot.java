package command;

import GameObject.Bullet;
import GameObject.BulletPool;
import GameObject.Tank;

import java.util.List;

public class CommandShoot extends Command {
    private BulletPool bulletPool;
    private List<Bullet> bullets;

    public CommandShoot(Tank tank) { super(tank); }

    public CommandShoot(Tank tank, BulletPool bulletPool, List<Bullet> bullets) {
        super(tank);
        this.bulletPool = bulletPool;
        this.bullets = bullets;
    }

    @Override
    public void execute() { getTank().shoot(bulletPool, bullets); }
}
