package client_exclusive;

import model.GameObject;
import processing.core.PApplet;

public class ParticleSystem {

	PApplet world;
	Particle particles[] = new Particle[8];

	boolean active = false;
	short r,g,b;
	
	float xExplosionPoint;
	float yExplosionPoint;
	
	float deactivationRange = 300;
	
	public ParticleSystem(PApplet world) {
		this.world = world;
		int i;
		for (i = 0; i < 8; i++) {
			particles[i] = new Particle(world);
			particles[i].setDirection(i);
		}
	}

	public void explodeAtPoint(float x, float y, short r, short g, short b) {
		if (!isActive()) {
			xExplosionPoint = x;
			yExplosionPoint = y;
			this.r = r;
			this.g = g;
			this.b =b;
			for (Particle p : particles) {
				p.explode(x, y);
			}
		}
		this.activate();
	}
	
	public void run() {
		if (isActive()) {
			world.stroke(r,g,b);
			world.fill(r,g,b);
			boolean isEveryParticleDeactivated = true;
			for (Particle p : particles) {				
				if(!isParticleOutOfRange(p)) {
					p.run();
					if(isEveryParticleDeactivated) {
						isEveryParticleDeactivated = false;
					}
				}
				else {
					p.deactivate();
				}
				
				//p.run();
			}
			if(isEveryParticleDeactivated) {
				this.deactivate();
			}
			//determineDeactivation();
		}
	}

	public void determineDeactivation() {
		if (shouldDeactivate()) {
			deactivate();
		}
	}
	
	public boolean shouldDeactivate() {
		for (Particle p : particles) {
			if (p.isActive()) {
				return false;
			}
		}
		return true;
	}
	
	public boolean isParticleOutOfRange(Particle p) {
		if(getDistance(p.getX(), p.getY(), xExplosionPoint, yExplosionPoint) > deactivationRange) {
			return true;
		}
		return false;
	}
	
	public double getDistance(float x1, float y1, float x2, float y2) {
		float xDistance = x2 - x1;
		float yDistance = y2 - y1;
		return Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2));
	}

	public boolean isActive() {
		return active;
	}

	public void activate() {
		active = true;
	}

	public void deactivate() {
		active = false;
	}
}
