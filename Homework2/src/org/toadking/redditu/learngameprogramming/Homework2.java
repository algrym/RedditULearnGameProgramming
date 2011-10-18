package org.toadking.redditu.learngameprogramming;

import java.net.URL;
import java.net.URLClassLoader;
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
    GameEntity player;

    private LinkedList<GameEntity> mobList;
    private LinkedList<GameProjectile> shotList;

    private GameEntityFactory gef = new GameEntityFactory(this);

    private static final int MINMOBS = 5;
    private static final int MAXMOBS = 10;
    private static final int MAXSHOTS = 10;

    public static final String RSCPREFIX = "Rscs/";
    
    private TileBackground grass;

    private long countSinceSpawn;
    ScoreKeeper score;

    boolean gameRunning = false;

    public Homework2() {
	super("Exterminator Wizard - ajw@toadking.org");

	// Create the on-screen UI container for this object
	try {
	    AppGameContainer app = new AppGameContainer(this, 1024, 768, false);
	    // app.setShowFPS(false);
	    app.start();
	} catch (SlickException e) {
	    e.printStackTrace();
	}
    }

    private long getSpawnTime() {
	return GameEntityFactory.rand.getInt(1000, 20000);
    }

    @Override
    public void init(GameContainer container) throws SlickException {
	player = new GameEntity("amg1", container.getWidth() / 2,
		container.getHeight() / 2);

	mobList = new LinkedList<GameEntity>();
	shotList = new LinkedList<GameProjectile>();
	countSinceSpawn = getSpawnTime();
	grass = new TileBackground(RSCPREFIX + "grass.png");
	score = new ScoreKeeper(this);
    }

    @Override
    public void render(GameContainer container, Graphics g)
	    throws SlickException {
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
		mobList.remove(ge);
	    } else
		ge.render(container, g);
	}

	score.render(container, g);

	g.drawString("ajw@toadking.org", container.getWidth() - 150,
		container.getHeight() - 20);
    }

    @Override
    public void update(GameContainer container, int delta)
	    throws SlickException {
	Input input = container.getInput();

	// Housekeeping
	if (input.isKeyPressed(Input.KEY_ESCAPE))
	    if (player.isDead() || !gameRunning) {
		init(container);
		gameRunning = true;
		return;
	    } else
		container.exit();

	if (input.isKeyPressed(Input.KEY_F11))
	    container.setFullscreen(!container.isFullscreen());

	score.update(container, delta);

	if ((player.isDead()) || !gameRunning)
	    return;

	// Handle player movement
	boolean playerMoved = false;
	if (input.isKeyDown(Input.KEY_W)) {
	    player.moveUp(delta);
	    playerMoved = true;
	} else if (input.isKeyDown(Input.KEY_S)) {
	    player.moveDown(delta);
	    playerMoved = true;
	}

	if (input.isKeyDown(Input.KEY_A)) {
	    player.moveLeft(delta);
	    playerMoved = true;
	} else if (input.isKeyDown(Input.KEY_D)) {
	    player.moveRight(delta);
	    playerMoved = true;
	}

	// PEW PEW PEW!
	if (shotList.size() < MAXSHOTS) {
	    if (input.isKeyPressed(Input.KEY_UP)) {
		shotList.add(player.shootUp(delta));
	    } else if (input.isKeyPressed(Input.KEY_DOWN)) {
		shotList.add(player.shootDown(delta));
	    } else if (input.isKeyPressed(Input.KEY_LEFT)) {
		shotList.add(player.shootLeft(delta));
	    } else if (input.isKeyPressed(Input.KEY_RIGHT)) {
		shotList.add(player.shootRight(delta));
	    }
	}

	// Assert player boundaries based on the dimensions of the container
	if (playerMoved)
	    player.fixLimits(container.getWidth(), container.getHeight());

	// Add new mobs as necessary
	countSinceSpawn -= delta;
	if (countSinceSpawn <= 0) {
	    countSinceSpawn = getSpawnTime();
	    if (mobList.size() < MAXMOBS)
		addMob(gef.getMob(container.getWidth(), container.getHeight()));
	}
	while (mobList.size() < MINMOBS)
	    addMob(gef.getMob(container.getWidth(), container.getHeight()));

	// Update all the bullets
	for (GameProjectile gp : shotList) {
	    gp.update(delta);
	    gp.fixLimits(container.getWidth(), container.getHeight());

	    // check for collisions
	    for (GameEntity ge : mobList) {
		if (ge.collides(gp) && !ge.isDead()) {
		    ge.dieInFire();
		    score.addScore(ge.getScoreValue()
			    / Math.max(shotList.size(), 1));
		}
	    }
	}

	// Update all the mobs!
	LinkedList<GameEntity> tempMobList = new LinkedList<GameEntity>(mobList);
	for (GameEntity ge : tempMobList) {
	    ge.update(delta);
	    ge.fixLimits(container.getWidth(), container.getHeight());

	    // check for collisions with the player
	    if (player.collides(ge) && !ge.isDead())
		player.dieInBattle();
	}
    }

    public static void printClassPath() {
	// Get the System Classloader
	ClassLoader sysClassLoader = ClassLoader.getSystemClassLoader();

	// Get the URLs
	URL[] urls = ((URLClassLoader) sysClassLoader).getURLs();

	System.err.println("Classpath = {");
	for (int i = 0; i < urls.length; i++) {
	    System.err.println("  " + urls[i].getFile());
	}
	System.err.println("}");
    }
    
    public static void main(String[] args) {
	printClassPath();
	
	// Create a new instance of this object, and put it to work
	new Homework2();
    }

    public GameEntity getPlayer() {
	return player;
    }

    public void addMob(GameEntity mob) {
	mobList.add(mob);
    }
}
