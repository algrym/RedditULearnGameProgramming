package org.toadking.redditu.learngameprogramming;

import java.io.IOException;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;

public class GameEntity implements Collidable {
    private Location2D location;
    final private Animation up, down, left, right;
    private Animation sprite;
    private final String mobName;
    private static final String IMAGEFORMAT = ".png";
    private GameEntityAI ai;
    private float spriteSpeed = 0.2f;
    private boolean isDone = false;
    private boolean isDead = false;
    Rectangle collisionShape;
    private int scoreValue = 100;

    private ParticleSystem explosionSystem;
    private ConfigurableEmitter explosionEmitter;

    private int deathClock = 500;

    public GameEntity(final String newMobName, final int newX, final int newY)
	    throws SlickException {
	location = new Location2D(newX, newY);
	mobName = newMobName;
	ai = null;

	final Image[] movementUp = {
		new Image(Homework2.RSCPREFIX + mobName + "_bk1" + IMAGEFORMAT),
		new Image(Homework2.RSCPREFIX + mobName + "_bk2" + IMAGEFORMAT) };
	final Image[] movementDown = {
		new Image(Homework2.RSCPREFIX + mobName + "_fr1" + IMAGEFORMAT),
		new Image(Homework2.RSCPREFIX + mobName + "_fr2" + IMAGEFORMAT) };
	final Image[] movementLeft = {
		new Image(Homework2.RSCPREFIX + mobName + "_lf1" + IMAGEFORMAT),
		new Image(Homework2.RSCPREFIX + mobName + "_lf2" + IMAGEFORMAT) };
	final Image[] movementRight = {
		new Image(Homework2.RSCPREFIX + mobName + "_rt1" + IMAGEFORMAT),
		new Image(Homework2.RSCPREFIX + mobName + "_rt2" + IMAGEFORMAT) };

	final int duration = 200;

	try {
	    explosionSystem = ParticleIO
		    .loadConfiguredSystem(Homework2.RSCPREFIX + "Fire.xml");
	    explosionEmitter = (ConfigurableEmitter) explosionSystem
		    .getEmitter(0);
	    explosionEmitter.setPosition(location.getX(), location.getY());
	} catch (IOException e) {
	    throw new SlickException("Failed to load particle systems", e);
	}

	up = new Animation(movementUp, duration, false);
	down = new Animation(movementDown, duration, false);
	left = new Animation(movementLeft, duration, false);
	right = new Animation(movementRight, duration, false);

	sprite = down;

	updateCollisionShape();
    }

    protected void updateCollisionShape() {
	collisionShape = new Rectangle(location.getX(), location.getY(),
		sprite.getWidth(), sprite.getHeight());
	explosionEmitter.setPosition(location.getX() + (sprite.getWidth() / 2),
		location.getY() + sprite.getHeight());
    }

    public void render(GameContainer container, Graphics g) {
	sprite.draw(location.getX(), location.getY());
	if (isDead) {
	    explosionSystem.render();
	}
    }

    public void fixLimits(int width, int height) {
	// handle things differently if this is a player or a mob
	if (ai == null) {
	    // player
	    if (location.getX() < 0)
		location.setX(0);
	    if (location.getY() < 0)
		location.setY(0);

	    if (location.getX() > (width - sprite.getWidth()))
		location.setX(width - sprite.getWidth());
	    if (location.getY() > (height - sprite.getHeight()))
		location.setY(height - sprite.getHeight());
	} else {
	    // mob
	    if (location.getX() <= sprite.getWidth()) {
		location.moveRight();
	    }
	    if (location.getY() <= sprite.getHeight()) {
		location.moveDown();
	    }

	    if (location.getX() >= (width - (2 * sprite.getWidth()))) {
		location.moveLeft();
	    }
	    if (location.getY() >= (height - (2 * sprite.getHeight()))) {
		location.moveUp();
	    }
	}
    }

    public void moveUp(int delta) {
	sprite = up;
	sprite.update(delta);
	location.moveUp(delta * spriteSpeed);
	updateCollisionShape();
    }

    public void moveDown(int delta) {
	sprite = down;
	sprite.update(delta);
	location.moveDown(delta * spriteSpeed);
	updateCollisionShape();
    }

    public void moveLeft(int delta) {
	sprite = left;
	sprite.update(delta);
	location.moveLeft(delta * spriteSpeed);
	updateCollisionShape();
    }

    public void moveRight(int delta) {
	sprite = right;
	sprite.update(delta);
	location.moveRight(delta * spriteSpeed);
	updateCollisionShape();
    }

    public void moveForward(int delta) {
	if (sprite == up)
	    moveUp(delta);
	else if (sprite == down)
	    moveDown(delta);
	else if (sprite == left)
	    moveLeft(delta);
	else if (sprite == right)
	    moveRight(delta);
    }

    public GameProjectile shoot(int delta) throws SlickException {
	Location2D centerLocation = new Location2D(location.getX()
		+ (sprite.getWidth() / 2), location.getY()
		+ (sprite.getHeight() / 2));

	if (sprite == up)
	    return new GameProjectile(centerLocation, Location2D.UP);
	else if (sprite == down)
	    return new GameProjectile(centerLocation, Location2D.DOWN);
	else if (sprite == left)
	    return new GameProjectile(centerLocation, Location2D.LEFT);
	else if (sprite == right)
	    return new GameProjectile(centerLocation, Location2D.RIGHT);

	return null;
    }

    public void update(final int delta) {
	if (isDead) {
	    deathClock -= delta;
	    if (deathClock <= 0) {
		isDone = true;
		explosionEmitter.wrapUp();
	    }
	    explosionSystem.update(delta);
	} else if (ai != null)
	    ai.update(delta);
    }

    @Override
    public String toString() {
	return mobName + " @ " + location;
    }

    public void setAI(GameEntityAI newGameEntityAI) {
	ai = newGameEntityAI;
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

    public void dieInFire() {
	isDead = true;
    }

    public void dieInBattle() {
	isDead = true;
    }

    public int getScoreValue() {
	return scoreValue;
    }

    public void setScoreValue(int scoreValue) {
	this.scoreValue = scoreValue;
    }

    public Double distanceTo(GameEntity other) {
	return location.distanceTo(other.location);
    }

    public boolean isDead() {
	return isDead;
    }

    public void setSpeed(float f) {
	spriteSpeed = f;
    }

    public Location2D getLocation() {
	return location;
    }

    public void setLocation(Location2D location) {
	this.location = location;
    }

    public void moveTo(GameEntity other, int delta) {
	// up, down, left, right
	double[] distance = { 0, 0, 0, 0 };
	distance[0] = location.upSquare(delta).distanceTo(other.location);
	distance[1] = location.downSquare(delta).distanceTo(other.location);
	distance[2] = location.leftSquare(delta).distanceTo(other.location);
	distance[3] = location.rightSquare(delta).distanceTo(other.location);

	// find the shortest distance
	if ((distance[0] < distance[1]) && (distance[0] < distance[2])
		&& (distance[0] < distance[3]))
	    moveUp(delta);
	else if ((distance[1] < distance[0]) && (distance[1] < distance[2])
		&& (distance[1] < distance[3]))
	    moveDown(delta);
	else if ((distance[2] < distance[0]) && (distance[2] < distance[1])
		&& (distance[2] < distance[3]))
	    moveLeft(delta);
	else if ((distance[3] < distance[0]) && (distance[3] < distance[1])
		&& (distance[3] < distance[2]))
	    moveRight(delta);
	else
	    moveForward(delta);
    }

    public GameProjectile shootUp(int delta) throws SlickException {
	Location2D centerLocation = new Location2D(location.getX()
		+ (sprite.getWidth() / 2), location.getY()
		+ (sprite.getHeight() / 2));

	return new GameProjectile(centerLocation, Location2D.UP);
    }

    public GameProjectile shootDown(int delta) throws SlickException {
	Location2D centerLocation = new Location2D(location.getX()
		+ (sprite.getWidth() / 2), location.getY()
		+ (sprite.getHeight() / 2));

	return new GameProjectile(centerLocation, Location2D.DOWN);
    }

    public GameProjectile shootLeft(int delta) throws SlickException {
	Location2D centerLocation = new Location2D(location.getX()
		+ (sprite.getWidth() / 2), location.getY()
		+ (sprite.getHeight() / 2));

	return new GameProjectile(centerLocation, Location2D.LEFT);
    }

    public GameProjectile shootRight(int delta) throws SlickException {
	Location2D centerLocation = new Location2D(location.getX()
		+ (sprite.getWidth() / 2), location.getY()
		+ (sprite.getHeight() / 2));

	return new GameProjectile(centerLocation, Location2D.RIGHT);
    }
}