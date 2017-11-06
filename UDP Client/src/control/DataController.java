package control;
import java.util.ArrayList;

import model.*;

public class DataController {

	private Data data = new Data();
	
	public DataController(){
	}
	
	public void updateData(Data data) {
		if(data.explosions.size() > 0) {
			this.data.explosions.addAll(data.explosions);
		}
		this.data.players = data.players;
		this.data.enemies = data.enemies;
	}
	
	public ArrayList<Player> getPlayers(){
		return data.players;
	}
	
	public Data getData() {
		return data;
	}
	
	public ArrayList<GameObject> getEnemies(){
		return data.enemies;
	}
	
	public ArrayList<GameObject> getExplosions(){
		return data.explosions;
	}
}
