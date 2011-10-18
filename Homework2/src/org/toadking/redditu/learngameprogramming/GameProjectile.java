package org.toadking.redditu.learngameprogramming;

import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;

public class GameProjectile implements Collidable {
    private Location2D location;
    private final Location2D heading;
    private boolean isDone = false;

    private ParticleSystem explosionSystem;
    private ConfigurableEmitter explosionEmitter;

    private Rectangle collisionShape;

    public GameProjectile(final Location2D start, final Location2D newHeading)
	    throws SlickException {
	location = new Location2D(start);
	heading = new Location2D(newHeading);

	try {
	    explosionSystem = ParticleIO
		    .loadConfiguredSystem(Homework2.RSCPREFIX + "fireball.xml");
	    explosionEmitter = (ConfigurableEmitter) explosionSystem
		    .getEmitter(0);
	    explosionEmitter.setPosition(location.getX(), location.getY());
	} catch (IOException e) {
	    throw new SlickException("Failed to load particle systems", e);
	}
    }

    public void render(GameContainer container, Graphics g) {
	explosionSystem.render();
    }

    public void update(int delta) {
	location.move(heading, delta);

	explosionEmitter.setPosition(location.getX(), location.getY());
	explosionSystem.update(delta);

	collisionShape = new Rectangle(location.getX() - 2,
		location.getY() - 2, 4, 4);
    }

    public void fixLimits(int width, int height) {
	if ((location.getX() < (0 - (0.25 * width)))
		|| (location.getY() < (0 - (0.25 * height)))
		|| (location.getX() > (width * 1.25))
		|| (location.getY() > (height * 1.25))) {
	    explosionEmitter.wrapUp();
	    isDone = true;
	}
    }

    public boolean isDone() {
	return isDone;
    }

    @Override
    public boolean collides(final Collidable other) {
	return collisionShape.intersects(other.getCollisionShape());
    }

    @Override
    public Shape getCollisionShape() {
	return collisionShape;
    }
}
