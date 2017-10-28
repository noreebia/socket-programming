package control;
import java.util.ArrayList;

import model.*;

public class DataController {

	private Data data = new Data();
	
	public DataController(){
	}
	
	public void cloneData(Data data) {
		this.data.players = data.players;
	}
	
	public ArrayList<Player> getPlayers(){
		return data.players;
	}
	
	public Data getData() {
		return data;
	}
}
