package client_exclusive;

import java.util.ArrayList;

import model.Bullet;
import processing.core.PApplet;

public class Gun {
	PApplet world;
	User owner;
	boolean isFiring;
	int length = 9;
	int width = 4;
	int lastFiredTime = 0;
	float reloadTime = 100;
	
	float bulletSpeedModifier;
	float bulletSpeedModifierStraight = 10;
	float bulletSpeedModifierDiagonal = (float) (bulletSpeedModifierStraight / Math.sqrt(2));
	float bulletSpeedX, bulletSpeedY;
	
	ArrayList<Bullet> bullets = new ArrayList<Bullet>();

	public Gun(PApplet world, User owner) {
		this.world = world;
		this.owner = owner;
	}

	public void display() {
		world.rect(-width / 2, -owner.size, width, -length);
	}

	public void run() {
		fire();
		manageBullets();
		displayBullets();
		System.out.println("Active bullets: " + bullets.size());
	}
	
	public void fire() {
		if (isFiring) {
			float bulletSpawnLocationX = (float) (owner.x + 1.5 * Math.sin(owner.directionModifier * Math.PI / 4) * length);
			float bulletSpawnLocationY = (float) (owner.y - 1.5 * Math.cos(owner.directionModifier * Math.PI / 4) * length);
			if (world.millis() - lastFiredTime >= reloadTime) {
				bullets.add(new Bullet(bulletSpawnLocationX, bulletSpawnLocationY, owner.directionModifier));
				lastFiredTime = world.millis();
			}
		}
	}
	
	public void manageBullets() {
		int i;
		for(i =0; i < bullets.size(); i++) {
			moveBullet(bullets.get(i));
			if(isBulletOutOfMap(bullets.get(i))) {
				bullets.remove(i);
			}
		}
	}
	
	public void displayBullets() {
		for(Bullet b: bullets) {
			world.ellipse(b.getX(), b.getY(), b.size, b.size);
		}
	}
	
	public void moveBullet(Bullet b) {
		bulletSpeedX = 0;
		bulletSpeedY = 0;
		switch(b.direction) {
		case 0:
			bulletSpeedY--;
			bulletSpeedModifier = bulletSpeedModifierStraight;
			break;
		case 1:
			bulletSpeedX++;
			bulletSpeedY--;
			bulletSpeedModifier = bulletSpeedModifierDiagonal;
			break;
		case 2:
			bulletSpeedX++;
			bulletSpeedModifier = bulletSpeedModifierStraight;
			break;
		case 3:
			bulletSpeedX++;
			bulletSpeedY++;
			bulletSpeedModifier = bulletSpeedModifierDiagonal;
			break;
		case 4:
			bulletSpeedY++;
			bulletSpeedModifier = bulletSpeedModifierStraight;
			break;
		case 5:
			bulletSpeedX--;
			bulletSpeedY++;
			bulletSpeedModifier = bulletSpeedModifierDiagonal;
			break;
		case 6:
			bulletSpeedX--;
			bulletSpeedModifier = bulletSpeedModifierStraight;
			break;
		case 7:
			bulletSpeedX--;
			bulletSpeedY--;
			bulletSpeedModifier = bulletSpeedModifierDiagonal;
			break;
		}
		b.setX(b.getX() + bulletSpeedX * bulletSpeedModifier);
		b.setY(b.getY() + bulletSpeedY * bulletSpeedModifier);
	}
	
	public boolean isBulletOutOfMap(Bullet b) {
		if(b.getX() < -b.size * 2 || b.getX() > (world.width + b.size * 2) || b.getY() < -b.size*2 || b.getY() > world.height + b.size * 2) {
			return true;
		}
		return false;
	}
	
	public ArrayList<Bullet> getBullets(){
		return bullets;
	}
	
	public void startFiring() {
		isFiring = true;
	}
	
	public void stopFiring() {
		isFiring = false;
	}
}