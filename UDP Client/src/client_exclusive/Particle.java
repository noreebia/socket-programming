package client_exclusive;

import processing.core.PApplet;

public class Particle {
	PApplet world;

	float x, y;
	float initialX, initialY;
	float speedX = 0;
	float speedY = 0;
	int direction;
	int activationRange = 50;
	float straightSpeed = 5;
	float diagonalSpeed = straightSpeed / world.sqrt(2);

	boolean active = false;
	
	short r,g,b;

	public Particle(PApplet world) {
		this.world = world;
	}

	public void explode(float x, float y, short r, short g, short b) {
		if (!isActive()) {
			setSpeed();
			this.x = x;
			this.y = y;
			initialX = x;
			initialY = y;
			activate();
			
			this.r = r;
			this.g= g;
			this.b = b;
		}
	}

	public void run() {
		if (isActive()) {
			move();
			display();
			determineDeactivation();
		}
	}

	public void determineDeactivation() {
		if (isOutOfActivationRange()) {
			deactivate();
		}
	}

	public boolean isOutOfActivationRange() {
		float distX = x - initialX;
		float distY = y - initialY;

		if (world.sqrt(distX * distX + distY * distY) >= activationRange) {
			return true;
		} else {
			return false;
		}
	}

	public void setSpeed() {
		switch (direction) {
		case 0:
			speedY = -straightSpeed;
			break;
		case 1:
			speedX = diagonalSpeed;
			speedY = -diagonalSpeed;
			break;
		case 2:
			speedX = straightSpeed;
			break;
		case 3:
			speedX = diagonalSpeed;
			speedY = diagonalSpeed;
			break;
		case 4:
			speedY = diagonalSpeed;
			break;
		case 5:
			speedX = -diagonalSpeed;
			speedY = diagonalSpeed;
			break;
		case 6:
			speedX = -straightSpeed;
			break;
		case 7:
			speedX = -diagonalSpeed;
			speedY = -diagonalSpeed;
			break;
		}
	}

	public void move() {
		x += speedX;
		y += speedY;
	}

	public void display() {
		world.fill(r,g,b);
		world.ellipse(x, y, 10, 10);
	}

	boolean isActive() {
		return active;
	}

	public void activate() {
		this.active = true;
	}

	public void deactivate() {
		this.active = false;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public int getDirection() {
		return direction;
	}
}
