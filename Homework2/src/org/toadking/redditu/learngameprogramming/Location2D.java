package org.toadking.redditu.learngameprogramming;

public class Location2D {
    private int x;
    private int y;

    final static Location2D UP = new Location2D(0, -1);
    final static Location2D DOWN = new Location2D(0, 1);
    final static Location2D LEFT = new Location2D(-1, 0);
    final static Location2D RIGHT = new Location2D(1, 0);

    public Location2D(final Location2D newL2D) {
	x = newL2D.x;
	y = newL2D.y;
    }

    public Location2D(final float newX, final float newY) {
	x = Math.round(newX * 100);
	y = Math.round(newY * 100);
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (this.getClass() != obj.getClass())
	    return false;

	final Location2D other = (Location2D) obj;
	if ((this.x != other.x) || (this.y != other.y))
	    return false;

	return true;
    }

    @Override
    public String toString() {
	return "(" + x / 100f + ", " + y / 100f + ")";
    }

    public int getX() {
	return Math.round(x / 100f);
    }

    public void setX(float x) {
	this.x = Math.round(x * 100f);
    }

    public int getY() {
	return Math.round(y / 100f);
    }

    public void setY(float y) {
	this.y = Math.round(y * 100f);
    }

    public void moveUp() {
	moveUp(1f);
    }

    public void moveDown() {
	moveDown(1f);
    }

    public void moveLeft() {
	moveLeft(1f);
    }

    public void moveRight() {
	moveRight(1f);
    }

    public void moveUp(final float d) {
	y -= Math.round(d * 100);
    }

    public void moveDown(final float d) {
	y += Math.round(d * 100);
    }

    public void moveLeft(final float d) {
	x -= Math.round(d * 100);
    }

    public void moveRight(final float d) {
	x += Math.round(d * 100);
    }

    public void move(final Location2D newLocation, final float scale) {
	x += newLocation.x * scale;
	y += newLocation.y * scale;
    }

    public Double distanceTo(final Location2D other) {
	return Math.sqrt(((other.x - x) * (other.x - x))
		+ ((other.y - y) * (other.y - y)));
    }
}
