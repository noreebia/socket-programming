package client_exclusive;

import control.DataController;
import model.Bullet;
import model.GameObject;
import model.Player;
import processing.core.PApplet;

public class DisplayHandler {

	PApplet world;
	short connectionID;
	DataController dataController;
	User user;
	
	int numberOfParticleSystems = 15;
	ParticleSystem particleSystems[] = new ParticleSystem[numberOfParticleSystems];
	
	//boolean levelChanged;
	long timeOfLevelChange;
	int durationOfLevelChangeDisplay = 1000;

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
		displayUser();
		runParticleSystems();
		drawPlayersAndBullets();
		drawEnemies();

		int i;
		for (i = 0; i < dataController.getExplosions().size(); i++) {
			createExplosion(dataController.getExplosions().get(i).getX(), dataController.getExplosions().get(i).getY(),
					dataController.getExplosions().get(i).getRGB(0), dataController.getExplosions().get(i).getRGB(1),
					dataController.getExplosions().get(i).getRGB(2));
			dataController.getExplosions().remove(i);
		}
		displayGameStats();
		displayLevelChange();
	}
	
	public void displayUser() {
		world.stroke(user.getRGB(0), user.getRGB(1), user.getRGB(2));
		world.pushMatrix();
		world.translate(user.getX(), user.getY());
		user.setAngle();
		world.rotate((float)  user.getAngle());
		world.fill(user.getRGB(0), user.getRGB(1), user.getRGB(2));
		// world.ellipse(0, 0, size * 2, size * 2);
		world.beginShape();
		world.vertex(0, -user.getSize()- 5);
		world.vertex(-user.getSize(), 5);
		world.vertex(0, 15);
		world.vertex(user.getSize(), 5);
		world.endShape(world.CLOSE);
		// bulletSystem.display();
		world.popMatrix();

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
			/*
			for (Bullet b : p.getBullets()) {
				drawBullet(b);
			}
			*/
		}
	}

	public void drawPlayer(Player player) {
		world.stroke(player.getRGB(0), player.getRGB(1), player.getRGB(2));
		world.pushMatrix();
		world.translate(player.getX(), player.getY());
		world.rotate(world.PI / 4 * player.getDirection());
		world.fill(player.getRGB(0), player.getRGB(1), player.getRGB(2));
		world.ellipse(0, 0, player.size * 2, player.size * 2);
		world.rect(-2, -player.size, 4, -9);
		world.popMatrix();
	}

	public void drawBullet(Bullet bullet) {
		world.stroke(bullet.getRGB(0), bullet.getRGB(1), bullet.getRGB(2));
		world.fill(bullet.getRGB(0), bullet.getRGB(1), bullet.getRGB(2));
		world.ellipse(bullet.getX(), bullet.getY(), 2 * bullet.getSize(), 2 * bullet.getSize());
	}

	public void drawEnemies() {
		for (GameObject e : dataController.getEnemies()) {
			drawEnemy(e);
		}
	}

	public void drawEnemy(GameObject enemy) {
		world.stroke(enemy.getRGB(0), enemy.getRGB(1), enemy.getRGB(2));
		world.fill(enemy.getRGB(0), enemy.getRGB(1), enemy.getRGB(2));
		world.ellipse(enemy.getX(), enemy.getY(), 2 * enemy.getSize(), 2 * enemy.getSize());
	}

	public void createExplosion(float x, float y, short r, short g, short b) {
		for (ParticleSystem p : particleSystems) {
			if (!p.isActive()) {
				p.explodeAtPoint(x, y, r, g, b);
				return;
			}
		}
	}
	
	public void displayGameStats() {
		world.fill(255);
		world.textSize(26);
		float widthOfString = world.textWidth("Level " + dataController.getLevel());
		world.text("Level " + dataController.getLevel(), world.width/2 - widthOfString/2, 30);
	}
	
	public void displayLevelChange() {
		if(dataController.hasLevelChanged()) {		
			dataController.setLevelChanged(false);
			timeOfLevelChange = System.currentTimeMillis();
			
			/*
			if(System.currentTimeMillis() - timeOfLevelChange >= durationOfLevelChangeDisplay) {
				dataController.setLevelChanged(false);
			}
			*/
		}
		else if(System.currentTimeMillis() - timeOfLevelChange <= durationOfLevelChangeDisplay){
			world.fill(255);
			world.textSize(70);
			float widthOfString = world.textWidth("Level " + dataController.getLevel());
			world.text("Level "+ dataController.getLevel(), world.width/2 - widthOfString/2, world.height/2 - 35);
		}
	}
	
	/*
	public void setLevelChanged(boolean levelChanged) {
		this.levelChanged = levelChanged;
		timeOfLevelChange = System.currentTimeMillis();
	}
	
	public boolean hasLevelChanged() {
		return levelChanged;
	}
	*/
}
