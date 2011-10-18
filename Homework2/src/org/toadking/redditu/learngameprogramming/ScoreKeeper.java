package org.toadking.redditu.learngameprogramming;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class ScoreKeeper {
    private long score = 0;
    private int toAdd = 0;
    private final Homework2 hw2;
    private Image titleScreen;

    private static final int FONTOFFSET = 60;

    /** The font we're going to use to render */
    private AngelCodeFont font;

    public ScoreKeeper(Homework2 newHW2) {
	hw2 = newHW2;
	
	try {
	    font = new AngelCodeFont(Homework2.RSCPREFIX + "hiero.fnt",
		    Homework2.RSCPREFIX + "hiero.png", true);
	} catch (SlickException e) {
	    System.err.println("Could not load AngelCodeFonts");
	    e.printStackTrace();
	}
	
	try {
	    titleScreen = new Image(Homework2.RSCPREFIX
		    + "Exterminator-Wizard-title.png");
	} catch (SlickException e) {
	    System.err.println("Could not load title PNG");
	    e.printStackTrace();
	}
    }

    public void addScore(int i) {
	toAdd += i;
    }

    public void render(GameContainer container, Graphics g) {
	if (hw2.player.isDead() || !hw2.gameRunning) {
	    titleScreen.drawCentered(container.getWidth() / 2,
		    container.getHeight() / 2);
	    font.drawString(10, container.getHeight() - FONTOFFSET,
		    "GAME OVER!  Final Score: " + score);
	} else
	    font.drawString(10, container.getHeight() - FONTOFFSET, "Score: "
		    + score);
    }

    public void update(GameContainer container, int delta) {
	delta = Math.min(delta, toAdd);

	if (delta > 0) {
	    toAdd -= delta;
	    score += delta;
	}
    }
}
