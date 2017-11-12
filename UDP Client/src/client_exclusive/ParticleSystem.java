package client_exclusive;

import processing.core.PApplet;

public class ParticleSystem {

	PApplet world;
	Particle particles[] = new Particle[8];

	boolean active = false;
	
	short r,g,b;
		
	public ParticleSystem(PApplet world) {
		this.world = world;
		int i;
		for (i = 0; i < 8; i++) {
			particles[i] = new Particle(world);
			particles[i].setDirection(i);
		}
	}

	public void run() {
		if (isActive()) {
			world.stroke(r,g,b);
			world.fill(r,g,b);
			for (Particle p : particles) {
				p.run();
			}
			determineDeactivation();
		}
	}

	public void explodeAtPoint(float x, float y, short r, short g, short b) {
		if (!isActive()) {
			for (Particle p : particles) {
				p.explode(x, y);
			}
			this.r = r;
			this.g = g;
			this.b =b;
		}
		activate();
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
