package org.toadking.redditu.learngameprogramming;

import org.newdawn.slick.SlickException;

public class GameEntityAI {
    long tolerance = 0;
    protected GameEntity gameEntity;
    final Homework2 hw2;
    private static final String[] MOBNAMEOPTIONS = { "dvl1", "skl1", "spd1" };
    protected String mobName;

    public GameEntityAI(Homework2 newHW2) {
	hw2 = newHW2;
    }

    public void update(int delta) {
	tolerance += delta;

	if (GameEntityFactory.rand.getInt(0, 100000) < tolerance) {
	    tolerance = 0;
	    switch (GameEntityFactory.rand.getInt(0, 4)) {
	    case 0:
		gameEntity.moveUp(delta);
		return;
	    case 1:
		gameEntity.moveDown(delta);
		return;
	    case 2:
		gameEntity.moveLeft(delta);
		return;
	    case 3:
		gameEntity.moveRight(delta);
		return;
	    default:
		gameEntity.moveTo(hw2.player, delta);
		return;
	    }
	}

	gameEntity.moveForward(delta);

    }

    public void setLoc(int x, int y) throws SlickException {
	if (mobName == null)
	    mobName = MOBNAMEOPTIONS[GameEntityFactory.rand.getInt(0,
		    MOBNAMEOPTIONS.length)];

	gameEntity = new GameEntity(mobName, x, y);
	gameEntity.setAI(this);
    }

    public GameEntity getMob() {
	return gameEntity;
    }

    @Override
    public String toString() {
	return "AI for " + mobName + " at " + gameEntity.toString();
    }

    public void setLoc(Location2D l) throws SlickException {
	setLoc(l.getX(), l.getY());
    }
}

class DevilAI extends GameEntityAI {
    private static final float SPEEDDIVISOR = 100000f;

    long devilSpeed = 0;

    public DevilAI(Homework2 hw2) {
	super(hw2);
	mobName = new String("dvl1");
    }

    @Override
    public void update(int delta) {
	devilSpeed += delta;

	// devils are slow, but get faster
	gameEntity.setSpeed(devilSpeed / SPEEDDIVISOR);

	// Devils are smart: they head right for you!
	gameEntity.moveTo(hw2.player, delta);
    }

    @Override
    public void setLoc(int x, int y) throws SlickException {
	super.setLoc(x, y);
    }
}

class SkeletonAI extends GameEntityAI {
    public SkeletonAI(Homework2 hw2) {
	super(hw2);
	mobName = new String("skl1");
    }

    @Override
    public void update(int delta) {
	// Skeletons are dumb: they just wander around
	super.update(delta);
    }

    @Override
    public void setLoc(int x, int y) throws SlickException {
	super.setLoc(x, y);
	gameEntity.setSpeed(0.15f); // skeletons are fast
    }
}

class SpiderAI extends GameEntityAI {
    private long countSinceSpawn;

    public SpiderAI(Homework2 hw2) {
	super(hw2);

	countSinceSpawn = getSpawnTime();

	mobName = new String("spd1");
    }

    private long getSpawnTime() {
	return GameEntityFactory.rand.getInt(10000, 20000);
    }

    @Override
    public void update(int delta) {
	countSinceSpawn -= delta;

	if (countSinceSpawn <= 0) {
	    countSinceSpawn = getSpawnTime();
	    GameEntityAI ai = new SpiderAI(hw2);
	    try {
		ai.setLoc(gameEntity.getLocation());
	    } catch (SlickException e) {
		e.printStackTrace();
	    }
	    hw2.addMob(ai.getMob());
	}

	super.update(delta);
    }

    @Override
    public void setLoc(int x, int y) throws SlickException {
	super.setLoc(x, y);
	gameEntity.setSpeed(0.25f); // spiders are faster
    }
}