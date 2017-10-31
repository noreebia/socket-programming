package model;

import java.util.ArrayList;

public class Player extends GameObject{

	short playerID;
	short direction = 0;
	
	ArrayList<Bullet> bullets;
	
	public Player(ArrayList<Bullet> bullets) {
		this.bullets = bullets;
	}
	
	public void setID(short id) {
		playerID = id;
	}
	
	public int getID() {
		return playerID;
	}
	
	public void setDirection(short direction) {
		this.direction = direction;
	}
	
	public short getDirection() {
		return direction;
	}
	
	public void cloneInfoOf(GameObject object) {
		this.setXY(object.getX(), object.getY());
		this.setRGB(object.getRGB(0), object.getRGB(1), object.getRGB(2));
	}
	
	public void cloneInfoOf(Player player) {
		this.setXY(player.getX(), player.getY());
		this.setRGB(player.getRGB(0), player.getRGB(1), player.getRGB(2));
		this.setBullets(player.getBullets());
		this.setDirection(player.getDirection());
	}
	
	public void setBullets(ArrayList<Bullet> bullets) {
		this.bullets = bullets;
	}
	
	public ArrayList<Bullet> getBullets(){
		return bullets;
	}
}
