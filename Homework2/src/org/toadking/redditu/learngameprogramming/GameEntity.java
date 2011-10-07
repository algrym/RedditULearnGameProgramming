package org.toadking.redditu.learngameprogramming;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class GameEntity {
    private Location2D location;
    final private Animation up, down, left, right;
    private Animation sprite;
    final private String mobName;
    static final String IMAGEFORMAT = new String(".png");
    GameEntityAI ai;
    final private float spriteSpeed = 0.2f;
    private boolean isDone = false;

    public GameEntity(final String newMobName, final int newX, final int newY)
	    throws SlickException {
	location = new Location2D(newX, newY);
	mobName = newMobName;
	ai = null;

	final Image[] movementUp = {
		new Image("Resources/" + mobName + "_bk1" + IMAGEFORMAT),
		new Image("Resources/" + mobName + "_bk2" + IMAGEFORMAT) };
	final Image[] movementDown = {
		new Image("Resources/" + mobName + "_fr1" + IMAGEFORMAT),
		new Image("Resources/" + mobName + "_fr2" + IMAGEFORMAT) };
	final Image[] movementLeft = {
		new Image("Resources/" + mobName + "_lf1" + IMAGEFORMAT),
		new Image("Resources/" + mobName + "_lf2" + IMAGEFORMAT) };
	final Image[] movementRight = {
		new Image("Resources/" + mobName + "_rt1" + IMAGEFORMAT),
		new Image("Resources/" + mobName + "_rt2" + IMAGEFORMAT) };

	int duration = 200;

	up = new Animation(movementUp, duration, false);
	down = new Animation(movementDown, duration, false);
	left = new Animation(movementLeft, duration, false);
	right = new Animation(movementRight, duration, false);

	sprite = down;
    }

    public void draw(GameContainer container, Graphics g) {
	sprite.draw(location.getX(), location.getY());
    }

    public void fixLimits(int width, int height) {
	if (location.getX() < 0)
	    location.setX(0);
	if (location.getY() < 0)
	    location.setY(0);

	if (location.getX() > (width - sprite.getWidth()))
	    location.setX(width - sprite.getWidth());
	if (location.getY() > (height - sprite.getHeight()))
	    location.setY(height - sprite.getHeight());
    }

    public void moveUp(int delta) {
	sprite = up;
	sprite.update(delta);
	location.moveUp(delta * spriteSpeed);
    }

    public void moveDown(int delta) {
	sprite = down;
	sprite.update(delta);
	location.moveDown(delta * spriteSpeed);
    }

    public void moveLeft(int delta) {
	sprite = left;
	sprite.update(delta);
	location.moveLeft(delta * spriteSpeed);
    }

    public void moveRight(int delta) {
	sprite = right;
	sprite.update(delta);
	location.moveRight(delta * spriteSpeed);
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
	if (sprite == up)
	    return new GameProjectile(location, Location2D.UP);
	else if (sprite == down)
	    return new GameProjectile(location, Location2D.DOWN);
	else if (sprite == left)
	    return new GameProjectile(location, Location2D.LEFT);
	else if (sprite == right)
	    return new GameProjectile(location, Location2D.RIGHT);
	
	return null;
    }

    public void update(int delta) {
	if (ai != null)
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
}
