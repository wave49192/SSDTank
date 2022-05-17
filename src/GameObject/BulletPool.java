package GameObject;

import java.util.ArrayList;
import java.util.List;

public class BulletPool {
	private List<Bullet> bullets = new ArrayList<Bullet>();
	private int currentSize = 0;
	private long expandedTime;

	public BulletPool() {
		int size = 30;
		for (int i = 0; i < size; i++) {
			bullets.add(new Bullet(-999, -999, 0, 0, null));
			++currentSize;
		}
		Thread sizeLoop = new Thread() {
			@Override
			public void run() {
				while (true) {
					long test = System.currentTimeMillis();
					if(test >= expandedTime + 30000 && currentSize > 30) {
						bullets.remove(0);
						--currentSize;
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		sizeLoop.start();
	}

	public Bullet requestBullet(int x, int y, int dx, int dy, Tank owner) {
		if (bullets.isEmpty()) {
			bullets.add(new Bullet(-999, -999, 0, 0, null));
			expandedTime = System.currentTimeMillis();
			++currentSize;
		}
		Bullet bullet = bullets.remove(0);
		bullet.refreshState(x, y, dx, dy, owner);
		return bullet;
	}

	public void releaseBullet(Bullet bullet) {
		bullets.add(bullet);
	}
}

