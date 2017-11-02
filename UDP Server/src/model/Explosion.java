package model;

import java.io.Serializable;

public class Explosion implements Serializable{
	short x, y;
	
	short[] rgb = {255, 255 ,255};
			
	public Explosion(short x, short y) {
		this.x = x;
		this.y = y;
	}
	
	public short getX() {
		return x;
	}
	
	public short getY() {
		return y;
	}
}
