package org.toadking.redditu.learngameprogramming;

import java.util.Random;

/**
 * @author ajw
 * 
 */
public class EasyRand {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = -1627472285297574973L;
    private final long randSeed;
    private Random rnd;

    public EasyRand() {
	this(System.currentTimeMillis());
    }

    public EasyRand(long seed) {
	randSeed = seed;
	reset();
    }

    public void reset() {
	rnd = new Random(randSeed);
    }

    /**
     * Generate a random integer between i1 and i2
     * 
     * @param i1
     * @param i2
     * @return a random integer between i1 and i2
     */
    int getInt(final int i1, final int i2) {
	int r;

	// just in case we're dumb and end up in the "one equals another" state,
	// don't waste the overhead generating a random number
	if (i1 == i2)
	    return i1;

	if (i2 < i1) {
	    r = rnd.nextInt(i1 - i2) + i2;
	} else {
	    r = rnd.nextInt(i2 - i1) + i1;
	}
	return r;
    }
    
    boolean getBoolean() {
	return getInt(0,10) < 5;
    }
}
