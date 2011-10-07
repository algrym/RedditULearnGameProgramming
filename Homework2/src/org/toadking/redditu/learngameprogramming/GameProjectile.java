package org.toadking.redditu.learngameprogramming;

import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;

public class GameProjectile {
    private Location2D location;
    private final Location2D heading;
    private boolean isDone = false;

    private ParticleSystem explosionSystem;
    private ConfigurableEmitter explosionEmitter;

    long updateCount = 1000;

    public GameProjectile(final Location2D start, final Location2D newHeading)
	    throws SlickException {
	location = new Location2D(start);
	heading = new Location2D(newHeading);

	try {
	    explosionSystem = ParticleIO
		    .loadConfiguredSystem("Resources/endlessexplosion.xml");
	    explosionEmitter = (ConfigurableEmitter) explosionSystem
		    .getEmitter(0);
	    explosionEmitter.setPosition(location.getX(), location.getY());
	} catch (IOException e) {
	    throw new SlickException("Failed to load particle systems", e);
	}
    }

    public void draw(GameContainer container, Graphics g) {
	explosionSystem.render();
    }

    public void update(int delta) {
	location.move(heading, delta);
	
	explosionEmitter.setPosition(location.getX(), location.getY());
	explosionSystem.update(delta);
	
	updateCount -= delta;
    }

    public void fixLimits(int width, int height) {
	if ((location.getX() < 0) || (location.getY() < 0)
		|| (location.getX() > width) || (location.getY() > height)
		|| (updateCount < 0)) {
	    explosionEmitter.wrapUp();
	    isDone = true;
	}
    }

    public boolean isDone() {
	return isDone;
    }
}
