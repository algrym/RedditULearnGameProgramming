package org.toadking.redditu.learngameprogramming;

import org.newdawn.slick.SlickException;

public class GameEntityFactory {
    static EasyRand rand = new EasyRand();
    long difficultyLevel = 0;
    final Homework2 hw2;

    public GameEntityFactory(Homework2 newHW2) {
	hw2 = newHW2;
    }

    public GameEntity getMob(final int maxX, final int maxY)
	    throws SlickException {
	GameEntityAI ai = null;

	switch (rand.getInt(0, 5)) {
	case 0:
	case 1:
	    // Devil!
	    ai = new DevilAI(hw2);
	    break;
	case 2:
	case 3:
	    // Skellington!
	    ai = new SkeletonAI(hw2);
	    break;
	case 4:
	    // Spider!
	    ai = new SpiderAI(hw2);
	    break;
	}

	// Place mobs randomly around the edge
	switch (rand.getInt(0, 5)) {
	case 0: // top
	    ai.setLoc(rand.getInt(0, maxX), 0);
	    break;
	case 1: // bottom
	    ai.setLoc(rand.getInt(0, maxX), maxY);
	    break;
	case 2: // left side
	    ai.setLoc(0, rand.getInt(0, maxY));
	    break;
	case 3: // right side
	    ai.setLoc(maxX, rand.getInt(0, maxY));
	    break;
	default:
	    // Place mobs randomly in the area
	    ai.setLoc(rand.getInt(0, maxX), rand.getInt(0, maxY));
	}

	return ai.getMob();
    }
}
