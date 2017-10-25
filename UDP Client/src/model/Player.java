package model;


public class Player extends GameObject{

	int playerID;
	
	public void setID(int id) {
		playerID = id;
	}
	
	public int getID() {
		return playerID;
	}
	
	public void cloneInfoOf(GameObject object) {
		this.setXY(object.getX(), object.getY());
		this.setRGB(object.getRGB(0), object.getRGB(1), object.getRGB(2));
	}
}
