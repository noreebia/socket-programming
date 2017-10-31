package model;

public class Bullet extends GameObject{
	
	public int direction;
	
	public Bullet(float x, float y, int direction) {
		super(x, y);
		this.direction = direction;
	}
}
