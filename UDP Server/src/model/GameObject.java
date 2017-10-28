package model;

import java.io.Serializable;

public class GameObject implements Serializable{
	
	public float x,y;
	public int[] rgb = {255,255,255};
	public int size = 10;
	
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
	
	public void setRGB(int x, int y, int z) {
		rgb[0] = x;
		rgb[1] = y;
		rgb[2] = z;
	}
	
	public int[] getRGB() {
		return rgb;
	}
	
	public int getRGB(int i) {
		return rgb[i];
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	
	public int getSize() {
		return size;
	}
}
