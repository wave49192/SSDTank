package ObjectPool;

import GameObject.Bullet;
import GameObject.state.State;

import java.util.ArrayList;
import java.util.List;

public class BulletPool {
    private List<Bullet> bullets = new ArrayList<Bullet>();
    int size = 30;

    public BulletPool() {
        for (int i = 0; i < size; i++) {
            bullets.add(new Bullet(-999, -999));
        }
    }

    public Bullet requestBullet(int x, int y) {
        try {
            Bullet bullet = bullets.remove(0);
            return bullet;
        } catch (IndexOutOfBoundsException e) {
            size += 1;
            System.out.println("Current Max Bullet = " + size);
            Bullet bullet = new Bullet(-999, -999);
            bullets.add(bullet);
            return bullet;
        }
    }

    public void releaseBullet(Bullet bullet) {
        bullets.add(bullet);
    }

    public void setDefault() {
        bullets.clear();
        size = 30;
    }
}
