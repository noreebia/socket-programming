package model;

public class Bullet extends GameObject{
	
	public short direction;
	
	public Bullet(float x, float y, short direction) {
		super(x, y);
		this.direction = direction;
	}
}
