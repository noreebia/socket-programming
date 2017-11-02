package model;

import java.io.Serializable;

public class GameObject implements Serializable{
	
	public float x,y;
	public short[] rgb = {255,255,255};
	public short size = 10;
	
	public GameObject() {
	}
	
	public GameObject(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void setXY(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public float getX() {
		return this.x;
	}
	
	public float getY() {
		return this.y;
	}
	
	public void setRGB(short x, short y, short z) {
		rgb[0] = x;
		rgb[1] = y;
		rgb[2] = z;
	}
	
	public short[] getRGB() {
		return rgb;
	}
	
	public short getRGB(int i) {
		return rgb[i];
	}
	
	public void setSize(short size) {
		this.size = size;
	}
	
	public int getSize() {
		return size;
	}
	
	public boolean isOutOfMap() {
		if(x < -(size * 2 + 100) || x > 1200 + (size * 2 +100)|| y < -(size*2 + 100) || y > (800 + size*2 + 100)) {
			return true;
		}
		return false;
	}
	
	public void move(float x, float y) {
		this.x += x;
		this.y += y;
	}
}
