package control;

import java.util.ArrayList;
import java.util.Random;

import model.*;
public class EnemySystem {
	DataController dataController;
	ArrayList<Enemy> enemies;
	
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
}
