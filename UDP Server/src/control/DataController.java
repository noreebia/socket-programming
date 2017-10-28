package control;
import java.util.ArrayList;

import model.*;

public class DataController {
	
	private Data data;
	
	public DataController(Data data) {
		this.data = data;
	}
	
	public void addPlayer(Player player) {
		data.getPlayers().add(player);
	}
	
	public void updatePlayer(Player player) {
		for(Player p: data.getPlayers()) {
			if(p.getID() == player.getID()) {
				p.cloneInfoOf(player);
				return;
			}
		}
		addPlayer(player);
	}
	
	public ArrayList<Player> getPlayers(){
		return data.players;
	}
	
	public Data getData() {
		return data;
	}
}
