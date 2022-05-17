package GameObject;

import java.util.ArrayList;
import java.util.List;

public class BulletPool {
	private List<Bullet> bullets = new ArrayList<Bullet>();

	public BulletPool() {
		int size = 30;
		for (int i = 0; i < size; i++) {
			bullets.add(new Bullet(-999, -999));
		}
	}

	public Bullet requestBullet(Tank tank) {
		if (bullets.isEmpty()) {
			bullets.add(new Bullet(-999, -999));
		}
		Bullet bullet = bullets.remove(0);
		bullet.refreshState(tank);
		return bullet;
	}

	public void releaseBullet(Bullet bullet) {
		bullets.add(bullet);
	}
}

