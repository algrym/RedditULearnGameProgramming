package org.toadking.redditu.learngameprogramming;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class TileBackground {
    private Image backgroundImage;

    public TileBackground(String ImagePath) {
	try {
	    backgroundImage = new Image(ImagePath);
	} catch (SlickException e) {
	    e.printStackTrace();
	}
    }

    public void render(GameContainer container, Graphics g) {
	for (int x = 0; x < container.getWidth(); x += backgroundImage
		.getWidth()) {
	    for (int y = 0; y < container.getHeight(); y += backgroundImage
		    .getHeight()) {
		backgroundImage.draw(x, y);
	    }
	}
    }
}
