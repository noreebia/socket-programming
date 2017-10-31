package control;
import java.util.ArrayList;

import java.util.Random;

import model.*;
public class EnemySystem {
	DataController dataController;
	ArrayList<Enemy> enemies;
	
	float speed = 1;
	int offset = 150;
	
	Random rand = new Random();
	
	public EnemySystem(DataController dataController) {
		this.dataController = dataController;
	}

	public void resetEnemies(int numOfEnemies) {
		dataController.createNewEnemyArrayList();
		enemies = dataController.getEnemies();
		
		int i;
		for(i=0; i< numOfEnemies; i++) {
			enemies.add(new Enemy(rand.nextInt(1200) + 1, rand.nextInt(800) + 1));
		}
	}
	
	public void run() {
		moveEnemiesRight();
		//handleBulletEnemyCollision();
	}
	
	public void handleBulletEnemyCollision() {		
		for(Player p: dataController.getPlayers()) {
			for(Bullet b: p.getBullets()) {
				for(Enemy e: enemies) {
					if(getDistance(e, b) <= b.getSize() + e.getSize()) {
						//e.setX(-offset);
						respawnEnemy(e);
					}
				}
			}
		}
	}
	
	public void respawnEnemy(int i) {
		this.enemies.get(i).setX(-offset);
	}
	
	public void respawnEnemy(Enemy enemy) {
		enemy.setX(-offset);
	}
	
	public double getDistance(GameObject a, GameObject b) {
		float xDistance = a.getX() - b.getX();
		float yDistance = a.getY() - b.getY();
		
		return Math.sqrt( Math.pow(xDistance, 2) + Math.pow(yDistance, 2) );
	}
	
	public void moveEnemiesRight() {
		for(Enemy e: enemies) {
			e.move(speed, 0);
			if(e.getX() > 1200 + offset) {
				e.setX(-offset);
			}
		}
	}
	
	public ArrayList<Enemy> getEnemies(){
		return enemies;
	}
}
