package control;
import java.util.ArrayList;

import java.util.Random;

import model.*;
public class EnemySystem {
	DataController dataController;
	
	ArrayList<Enemy> originals = new ArrayList<Enemy>();
	ArrayList<GameObject> shadows;
	
	float speed = 1;
	int offset = 150;
	
	Random rand = new Random();
	
	public EnemySystem(DataController dataController) {
		this.dataController = dataController;
	}

	public void resetEnemies(int numOfEnemies) {
		originals = new ArrayList<Enemy>();
		dataController.createNewEnemyArrayList();
		shadows = dataController.getEnemies();
		
		int i;
		for(i=0; i< numOfEnemies; i++) {
			float spawnPointX = rand.nextInt(1200) + 1;
			float spawnPointY = rand.nextInt(800) + 1;
			originals.add(new Enemy(spawnPointX, spawnPointY));
			
			shadows.add(new GameObject(spawnPointX, spawnPointY));
			shadows.get(i).setRGB((short)255, (short)128, (short)0);
		}
	}
	
	public void run() {
		//moveEnemiesRight();
		setTargetsForEnemies();
		moveEnemiesTowardsDestination();
		updateShadows();
		//handleBulletEnemyCollision();
	}
	
	public void updateShadows() {
		if(originals.size() == shadows.size()) {
			int i;
			for(i = 0; i < originals.size(); i++) {
				shadows.get(i).setX(originals.get(i).getX());
				shadows.get(i).setY(originals.get(i).getY());
			}
		}
	}
	
	public void setTargetsForEnemies() {
		if(dataController.getPlayers().size() > 0) {
			for(Enemy e: originals) {
				
				int randomNum = rand.nextInt(dataController.getPlayers().size());
				System.out.println("random number: " + randomNum);
				e.setDestination(getXLocationOfPlayer(randomNum), getYLocationOfPlayer(randomNum));
				
				//e.setDestination(getXLocationOfPlayer(0), getYLocationOfPlayer(0));

			}
		}
	}
	
	public void moveEnemiesTowardsDestination() {
		for(Enemy e:originals) {
			if(e.getVelocityX() != 0 && e.getVelocityY() != 0) {
				e.moveToDestination();
			}
		}
	}
	
	public float getXLocationOfPlayer(int i) {
		return dataController.getPlayers().get(i).getX();
	}
	
	public float getYLocationOfPlayer(int i) {
		return dataController.getPlayers().get(i).getY();
	}
	
	public void handleBulletEnemyCollision() {		
		for(Player p: dataController.getPlayers()) {
			for(Bullet b: p.getBullets()) {
				for(GameObject e: shadows) {
					if(getDistance(e, b) <= b.getSize() + e.getSize()) {
						//e.setX(-offset);
						respawnEnemy(e);
					}
				}
			}
		}
	}
	
	public void respawnEnemy(int i) {
		this.originals.get(i).setX(-offset);
		//this.shadows.get(i).setX(-offset);
	}
	
	public void respawnEnemy(GameObject enemy) {
		enemy.setX(-offset);
	}
	
	public double getDistance(GameObject a, GameObject b) {
		float xDistance = a.getX() - b.getX();
		float yDistance = a.getY() - b.getY();
		
		return Math.sqrt( Math.pow(xDistance, 2) + Math.pow(yDistance, 2) );
	}
	
	public void moveEnemiesRight() {
		for(Enemy e: originals) {
			e.move(e.getSpeed(), 0);
			if(e.getX() > 1200 + offset) {
				e.setX(-offset);
			}
		}
		/*
		for(GameObject e: shadows) {
			e.move(speed, 0);
			if(e.getX() > 1200 + offset) {
				e.setX(-offset);
			}
		}
		*/
	}
	
	public ArrayList<GameObject> getShadows(){
		return shadows;
	}
}
