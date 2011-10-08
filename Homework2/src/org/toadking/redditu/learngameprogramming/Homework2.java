package org.toadking.redditu.learngameprogramming;

import java.util.LinkedList;

import org.newdawn.slick.BasicGame;
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

    private GameEntityFactory gef = new GameEntityFactory(this);

    private static final int MAXMOBS = 10;
    private static final int MAXSHOTS = 10;

    private TileBackground grass;

    private long score = 0;

    public Homework2() {
	super("Homework 2");

	// Create the on-screen UI container for this object
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
	// container.getGraphics().setBackground(background);
	if (grass == null)
	    grass = new TileBackground("Resources/grass.png");

	grass.render(container, g);

	LinkedList<GameProjectile> tempShotList = new LinkedList<GameProjectile>(
		shotList);
	for (GameProjectile gp : tempShotList) {
	    if (gp.isDone())
		shotList.remove(gp);
	    else
		gp.render(container, g);
	}

	player.render(container, g);

	LinkedList<GameEntity> tempMobList = new LinkedList<GameEntity>(mobList);
	for (GameEntity ge : tempMobList) {
	    if (ge.isDone()) {
		score += ge.getScoreValue() / (shotList.size() + 1);
		mobList.remove(ge);
	    } else
		ge.render(container, g);
	}

	if (player.isDead())
	    g.drawString("GAME OVER!  Final score: " + score, 10,
		    container.getHeight() - 20);
	else
	    g.drawString("Score: " + score, 10, container.getHeight() - 20);

	g.drawString("ajw@toadking.org", container.getWidth() - 200,
		container.getHeight() - 20);
    }

    @Override
    public void update(GameContainer container, int delta)
	    throws SlickException {
	if (player.isDead())
	    return;

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

	    // check for collisions
	    for (GameEntity ge : mobList) {
		if (ge.collides(gp))
		    ge.dieInFire();
	    }
	}

	// Update all the mobs!
	for (GameEntity ge : mobList) {
	    ge.update(delta);
	    ge.fixLimits(container.getWidth(), container.getHeight());

	    // check for collisions with the player
	    if (player.collides(ge))
		player.dieInBattle();
	}
    }

    public static void main(String[] args) {
	// Create a new instance of this object, and put it to work
	new Homework2();
    }

    public GameEntity getPlayer() {
	return player;
    }
}
