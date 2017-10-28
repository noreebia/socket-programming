package model;

public class Bullet extends GameObject{
	
	public int direction;
	
	public Bullet(float x, float y, int direction) {
		this.x = x;
		this.y = y;
		this.direction = direction;
	}
}
