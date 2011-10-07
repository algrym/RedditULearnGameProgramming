package org.toadking.redditu.learngameprogramming;

public class GameEntityAI {
    long tolerance = 0;
    final GameEntity g;

    public GameEntityAI(GameEntity newG) {
	g = newG;
    }

    public void update(int delta) {
	tolerance += delta;

	if (GameEntityFactory.rand.getInt(0, 100000) < tolerance) {
	    tolerance = 0;
	    switch (GameEntityFactory.rand.getInt(0, 4)) {
	    case 0:
		g.moveUp(delta);
		return;
	    case 1:
		g.moveDown(delta);
		return;
	    case 2:
		g.moveLeft(delta);
		return;
	    case 3:
		g.moveRight(delta);
		return;
	    default:
		g.moveForward(delta);
		return;
	    }
	}

	g.moveForward(delta);
    }
}
