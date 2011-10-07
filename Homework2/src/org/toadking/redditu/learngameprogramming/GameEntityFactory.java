package org.toadking.redditu.learngameprogramming;

import org.newdawn.slick.SlickException;

public class GameEntityFactory {
    private static final String[] mobName = { "dvl1", "skl1", "spd1" };
    static EasyRand rand = new EasyRand();
    long difficultyLevel = 0;

    public GameEntity getMob(final int maxX, final int maxY)
	    throws SlickException {
	GameEntity g = new GameEntity(
		mobName[rand.getInt(0, mobName.length)], rand.getInt(0,
			maxX), rand.getInt(0, maxY));
	g.setAI(new GameEntityAI(g));
	System.out.println("New GameEntity: " + g);
	return g;
    }
}
