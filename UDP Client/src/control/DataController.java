package control;
import model.*;

public class DataController {

	private Data data;
	
	public DataController(Data data){
		this.data = data;
	}
	
	public void cloneData(Data data) {
		this.data.players = data.players;
	}
	
	public Data getData() {
		return data;
	}
}
