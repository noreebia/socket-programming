package model;

public class Enemy extends GameObject {

	int hp;
	float velocityX = 0;
	float velocityY = 0; 
	float speed = 1;
	
	public Enemy(float x, float y) {
		super(x, y);
	}
	
	public void setDestination(float x, float y) {
		float dx = x - this.getX();
		float dy = y - this.getY();
		
		float mag = (float) Math.sqrt( Math.pow(dx, 2) + Math.pow(dy, 2) );
		setVelocityX(dx/mag * speed);
		setVelocityY(dy/mag * speed);
	}
	
	public void moveToDestination() {
		this.move(velocityX, velocityY);
	}
	
	public void setVelocityX(float velocityX) {
		this.velocityX = velocityX;
	}
	
	public float getVelocityX() {
		return velocityX;
	}
	
	public void setVelocityY(float velocityY) {
		this.velocityY = velocityY;
	}
	
	public float getVelocityY() {
		return velocityY;
	}
	
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	public float getSpeed() {
		return speed;
	}
}
