package client_exclusive;

import control.DataController;
import model.Bullet;
import model.Enemy;
import model.Explosion;
import model.GameObject;
import model.Player;
import processing.core.PApplet;

public class DisplayHandler {

	PApplet world;
	short connectionID;
	DataController dataController;
	User user;

	int numberOfParticleSystems = 10;

	ParticleSystem particleSystems[] = new ParticleSystem[numberOfParticleSystems];

	public DisplayHandler(PApplet world, short connectionID, DataController dataController, User user) {
		this.world = world;
		this.connectionID = connectionID;
		this.dataController = dataController;
		this.user = user;

		int i;
		for (i = 0; i < numberOfParticleSystems; i++) {
			particleSystems[i] = new ParticleSystem(world);
		}
	}

	public void run() {
		runParticleSystems();
		drawPlayersAndBullets();
		drawEnemies();

		int i;
		for (i = 0; i < dataController.getExplosions().size(); i++) {
			createExplosion(dataController.getExplosions().get(i).getX(), dataController.getExplosions().get(i).getY(), dataController.getExplosions().get(i).getRGB(0), dataController.getExplosions().get(i).getRGB(1), dataController.getExplosions().get(i).getRGB(2));
			dataController.getExplosions().remove(i);
		}
	}

	public void runParticleSystems() {
		int i;
		for (i = 0; i < numberOfParticleSystems; i++) {
			particleSystems[i].run();
		}
	}

	public void drawPlayersAndBullets() {
		for (Player p : dataController.getPlayers()) {
			if (p.getID() != connectionID) {
				System.out.println("player's id: " + p.getID());
				System.out.println("my id: " + connectionID);
				System.out.println("drawing player...");
				drawPlayer(p);
			} else {
				System.out.println("player's id: " + p.getID());
				System.out.println("my id: " + connectionID);
				System.out.println("drawing me...");

				world.fill(255, 128, 0);
				world.ellipse(p.getX(), p.getY(), 5, 5);
			}
			// System.out.println("Num of player bullets: " + p.getBullets().size());
			for (Bullet b : p.getBullets()) {
				drawBullet(b);
			}
		}

	}

	public void drawPlayer(Player player) {
		world.pushMatrix();
		world.translate(player.getX(), player.getY());
		world.rotate(world.PI / 4 * player.getDirection());
		world.fill(player.getRGB(0), player.getRGB(1), player.getRGB(2));
		world.ellipse(0, 0, player.size * 2, player.size * 2);
		world.rect(-2, -player.size, 4, -9);
		world.popMatrix();
	}

	public void drawBullet(Bullet bullet) {
		world.fill(bullet.getRGB(0), bullet.getRGB(1), bullet.getRGB(2));
		world.ellipse(bullet.getX(), bullet.getY(), 2 * bullet.getSize(), 2 * bullet.getSize());
	}

	public void drawEnemies() {
		for (GameObject e : dataController.getEnemies()) {
			drawEnemy(e);
		}
	}

	public void drawEnemy(GameObject enemy) {
		world.fill(enemy.getRGB(0), enemy.getRGB(1), enemy.getRGB(2));
		world.ellipse(enemy.getX(), enemy.getY(), 2 * enemy.getSize(), 2 * enemy.getSize());
	}

	public void createExplosion(float x, float y, short r, short g, short b) {
		try {
			for (ParticleSystem p : particleSystems) {
				if (!p.isActive()) {
					p.explodeAtPoint(x, y, r, g, b);
					return;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
