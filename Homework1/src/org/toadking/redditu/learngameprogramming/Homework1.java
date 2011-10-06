package org.toadking.redditu.learngameprogramming;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.AppGameContainer;

/**
 * Homework 1 for /r/LearnGameProgramming
 * 
 * @author algrym@reddit.com
 * 
 *         Using your choice of language and framework, create a program that:
 *         creates a display, loads an image, starts a timing loop, draws the
 *         image to the display each frame, receives input and uses that input
 *         to move the image
 */
public class Homework1 extends BasicGame {
    private int imageX = 100, imageY = 100;
    private Image playerImage;

    public Homework1() {
	super("Homework 1");

	// Create the onscreen UI container for this object
	try {
	    // HW1: Create a display
	    AppGameContainer app = new AppGameContainer(this);
	    app.setShowFPS(false);
	    app.start(); // HW1: Start the timing loop
	} catch (SlickException e) {
	    e.printStackTrace();
	}
    }

    @Override
    public void init(GameContainer container) throws SlickException {
	// HW1: Load an image (from file)
	playerImage = new Image("Resources/Player.png");
    }

    @Override
    public void render(GameContainer container, Graphics g)
	    throws SlickException {
	// HW1: Draw the image to the display each frame
	playerImage.draw(imageX, imageY);
    }

    @Override
    public void update(GameContainer container, int delta)
	    throws SlickException {

	// HW1: receive input and use that input to move the image
	Input input = container.getInput();
	if (input.isKeyDown(Input.KEY_UP))
	    imageY--;
	else if (input.isKeyDown(Input.KEY_DOWN))
	    imageY++;

	if (input.isKeyDown(Input.KEY_LEFT))
	    imageX--;
	else if (input.isKeyDown(Input.KEY_RIGHT))
	    imageX++;

	// Assert player boundaries based on the dimensions of the container
	if (imageX < 0)
	    imageX = 0;
	if (imageY < 0)
	    imageY = 0;
	if (imageX > (container.getWidth() - playerImage.getWidth()))
	    imageX = container.getWidth() - playerImage.getWidth();
	if (imageY > (container.getHeight() - playerImage.getHeight()))
	    imageY = container.getHeight() - playerImage.getHeight();
    }

    public static void main(String[] args) {
	// Create a new instance of this object, and put it to work
	new Homework1();
    }
}
