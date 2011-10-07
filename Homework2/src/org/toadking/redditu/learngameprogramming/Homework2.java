package org.toadking.redditu.learngameprogramming;

import java.util.Iterator;
import java.util.LinkedList;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.AppGameContainer;

/**
 * Homework 2 for /r/LearnGameProgramming
 * 
 * @author algrym@reddit.com
 * 
 */
public class Homework2 extends BasicGame {
    private GameEntity player;
    private LinkedList<GameEntity> mobList = new LinkedList<GameEntity>();
    private LinkedList<GameProjectile> shotList = new LinkedList<GameProjectile>();
    private GameEntityFactory gef = new GameEntityFactory();
    private static final int MAXMOBS = 10;
    private static final int MAXSHOTS = 10;
    private final Color background = new Color(0, 123, 12);

    public Homework2() {
	super("Homework 2");

	// Create the onscreen UI container for this object
	try {
	    AppGameContainer app = new AppGameContainer(this, 1024, 768, false);
	    // app.setShowFPS(false);
	    app.start();
	} catch (SlickException e) {
	    e.printStackTrace();
	}
    }

    @Override
    public void init(GameContainer container) throws SlickException {
	player = new GameEntity("amg1", container.getWidth() / 2,
		container.getHeight() / 2);
    }

    @Override
    public void render(GameContainer container, Graphics g)
	    throws SlickException {
	container.getGraphics().setBackground(background);

	player.draw(container, g);

	LinkedList<GameEntity> tempMobList = new LinkedList<GameEntity>(mobList);
	for (GameEntity ge : tempMobList) {
	    if (ge.isDone())
		mobList.remove(ge);
	    else
		ge.draw(container, g);
	}

	LinkedList<GameProjectile> tempShotList = new LinkedList<GameProjectile>(
		shotList);
	for (GameProjectile gp : tempShotList) {
	    if (gp.isDone())
		shotList.remove(gp);
	    else
		gp.draw(container, g);
	}
    }

    @Override
    public void update(GameContainer container, int delta)
	    throws SlickException {
	Input input = container.getInput();
	boolean playerMoved = false;

	if (input.isKeyDown(Input.KEY_UP)) {
	    player.moveUp(delta);
	    playerMoved = true;
	} else if (input.isKeyDown(Input.KEY_DOWN)) {
	    player.moveDown(delta);
	    playerMoved = true;
	}

	if (input.isKeyDown(Input.KEY_LEFT)) {
	    player.moveLeft(delta);
	    playerMoved = true;
	} else if (input.isKeyDown(Input.KEY_RIGHT)) {
	    player.moveRight(delta);
	    playerMoved = true;
	}

	// PEW PEW PEW!
	if (input.isKeyPressed(Input.KEY_SPACE))
	    if (shotList.size() < MAXSHOTS)
		shotList.add(player.shoot(delta));

	// Housekeeping

	if (input.isKeyPressed(Input.KEY_ESCAPE))
	    container.exit();

	if (input.isKeyPressed(Input.KEY_F11))
	    container.setFullscreen(!container.isFullscreen());

	// Assert player boundaries based on the dimensions of the container
	if (playerMoved)
	    player.fixLimits(container.getWidth(), container.getHeight());

	// Add new mobs as necessary
	while (mobList.size() < MAXMOBS)
	    mobList.add(gef.getMob(container.getWidth(), container.getHeight()));

	// Update all the bullets
	for (GameProjectile gp : shotList) {
	    gp.update(delta);
	    gp.fixLimits(container.getWidth(), container.getHeight());
	}

	// Update all the mobs!
	for (GameEntity ge : mobList) {
	    ge.update(delta);
	    ge.fixLimits(container.getWidth(), container.getHeight());
	}
    }

    public static void main(String[] args) {
	// Create a new instance of this object, and put it to work
	new Homework2();
    }
}
