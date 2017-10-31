package client_exclusive;

import control.DataController;
import model.Bullet;
import model.Enemy;
import model.Player;
import processing.core.PApplet;

public class DisplayHandler {
	
	PApplet world;
	int connectionID;
	DataController dataController;
	
	public DisplayHandler(PApplet world, int connectionID, DataController dataController) {
		this.world = world;
		this.connectionID = connectionID;
		this.dataController = dataController;
	}
	
	public void displayGameObjectData() {
		for(Player p: dataController.getPlayers()) {
			if(p.getID()!= connectionID) {
				//ellipse(p.getX(), p.getY(), 2 * p.getSize(), 2* p.getSize());
				drawPlayer(p);
			}
			else {
				world.ellipse(p.getX(), p.getY(), 5, 5);
			}
			//System.out.println("Num of player bullets: " + p.getBullets().size());
			
			for(Bullet b: p.getBullets()) {
				//world.ellipse(b.getX(), b.getY(), 2 * b.getSize(), 2 * b.getSize());
				drawBullet(b);
			}
		}
		for(Enemy e: dataController.getEnemies()) {
			drawEnemy(e);
		}
	}
	
	public void drawPlayer(Player player) {
		world.pushMatrix();
		world.translate(player.getX(), player.getY());
		world.rotate(world.PI/4 * player.getDirection());
		world.fill(player.getRGB(0), player.getRGB(1), player.getRGB(2));
		world.ellipse(0, 0, player.size * 2, player.size * 2);
		world.rect(-2, -player.size, 4, -9);
		world.popMatrix();
	}
	
	public void drawBullet(Bullet bullet) {
		//world.fill(bullet.getRGB(0), bullet.getRGB(1), bullet.getRGB(2));
		world.ellipse(bullet.getX(), bullet.getY(), 2 * bullet.getSize(), 2 * bullet.getSize());
	}
	
	public void drawEnemy(Enemy enemy) {
		world.fill(enemy.getRGB(0), enemy.getRGB(1), enemy.getRGB(2));
		world.ellipse(enemy.getX(), enemy.getY(), 2 * enemy.getSize(),  2 * enemy.getSize());
	}

}
