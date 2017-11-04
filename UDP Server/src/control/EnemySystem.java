package control;

import java.util.ArrayList;

import java.util.Random;

import model.Bullet;
import model.Enemy;
import model.GameObject;
import model.Player;

public class EnemySystem {
	DataController dataController;

	ArrayList<Enemy> originals = new ArrayList<Enemy>();
	ArrayList<GameObject> shadows;

	float speed = 1;
	int offset = 150;
	
	int enemySpawnLocationWidth = 150;

	Random rand = new Random();

	public EnemySystem(DataController dataController) {
		this.dataController = dataController;
	}

	public void resetEnemies(int numOfEnemies) {
		originals = new ArrayList<Enemy>();
		dataController.createNewEnemyArrayList();
		shadows = dataController.getEnemies();

		int i;
		float spawnPointX = 0;
		float spawnPointY = 0;
		for (i = 0; i < numOfEnemies; i++) {
			int spawnDirection = rand.nextInt(4) + 1;
			switch(spawnDirection) {
			case 1:
				spawnPointX = rand.nextInt(1200) + 1;
				spawnPointY = rand.nextInt(enemySpawnLocationWidth) + 1;
				break;
			case 2:
				spawnPointX = rand.nextInt(1200 - (1200 - enemySpawnLocationWidth) + 1) + (1200 - enemySpawnLocationWidth);
				spawnPointY = rand.nextInt(800) + 1;
				break;
			case 3:
				spawnPointX = rand.nextInt(1200) + 1;
				spawnPointY = rand.nextInt(800 - (800 - enemySpawnLocationWidth) +1) + (800 - enemySpawnLocationWidth);
				break;
			case 4:
				spawnPointX = rand.nextInt(enemySpawnLocationWidth) + 1;
				spawnPointY = rand.nextInt(800) + 1;
				break;
			}
			originals.add(new Enemy(spawnPointX, spawnPointY));

			shadows.add(new GameObject(spawnPointX, spawnPointY));
			shadows.get(i).setRGB((short) (rand.nextInt(255) + 1), (short) (rand.nextInt(255) + 1), (short) (rand.nextInt(255) + 1));
		}
	}

	public void run() {
		System.out.println("enemy system run method");
		if (dataController.getPlayers().size() > 0) {
			for (Enemy e : originals) {
				if (e.isActive()) {
					if (e.getVelocityX() != -1 && e.getVelocityY() != -1) {
						if (e.isOutOfMap()) {
							setRandomPointAsTarget(e);
						}
					} else {
						setRandomPointAsTarget(e);
					}
					e.moveToDestination();
				}
			}
			updateShadows();
		}
	}
	
	public void setRandomPointAsTarget(Enemy e) {
		int randomX = rand.nextInt(1200) + 1;
		int randomY = rand.nextInt(800) + 1;
		
		e.setDestination(randomX, randomY);
	}

	public void setRandomPlayerAsTarget(Enemy e) {
		int randomNum = rand.nextInt(dataController.getPlayers().size());
		e.setDestination(getXLocationOfPlayer(randomNum), getYLocationOfPlayer(randomNum));
	}

	public void updateShadows() {
		if (originals.size() == shadows.size()) {
			int i;
			for (i = 0; i < originals.size(); i++) {
				if(originals.get(i).isActive()) {
					shadows.get(i).setX(originals.get(i).getX());
					shadows.get(i).setY(originals.get(i).getY());
				}				
			}
		}
	}
	
	public void changeShadowColor(int i) {
		shadows.get(i).setRGB((short)(rand.nextInt(255) + 1), (short)(rand.nextInt(255) + 1), (short)(rand.nextInt(255) + 1));
	}

	public void moveEnemiesTowardsDestination() {
		for (Enemy e : originals) {
			if (e.getVelocityX() != -1 && e.getVelocityY() != -1) {
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

	public void respawnEnemy(int i) {
		this.originals.get(i).setX(-offset);
	}

	public void respawnEnemy(GameObject enemy) {
		enemy.setX(-offset);
	}

	public double getDistance(GameObject a, GameObject b) {
		float xDistance = a.getX() - b.getX();
		float yDistance = a.getY() - b.getY();

		return Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2));
	}

	public ArrayList<GameObject> getShadows() {
		return shadows;
	}

	public ArrayList<Enemy> getOriginals() {
		return originals;
	}
}
