package org.toadking.redditu.learngameprogramming;

import org.newdawn.slick.geom.Shape;

public interface Collidable {
    public boolean collides(final Collidable other);
    public Shape getCollisionShape();
}

// Example collision code below

//@Override
//public boolean collides(final Collidable other) {
//	return collisionShape.intersects(other.getCollisionShape());
//}
//
//@Override
//public Shape getCollisionShape() {
//	return collisionShape;
//}
