package org.toadking.redditu.learngameprogramming;

import org.newdawn.slick.SlickException;

public class GameEntityFactory {
    private static final String[] mobName = { "dvl1", "skl1", "spd1" };
    static EasyRand rand = new EasyRand();
    long difficultyLevel = 0;
    final Homework2 hw2;
    
    public GameEntityFactory(Homework2 newHW2) {
	hw2 = newHW2;
    }

    public GameEntity getMob(final int maxX, final int maxY)
	    throws SlickException {
	GameEntity g = new GameEntity(
		mobName[rand.getInt(0, mobName.length)], rand.getInt(0,
			maxX), rand.getInt(0, maxY));
	g.setAI(new GameEntityAI(g, hw2));
	return g;
    }
}
