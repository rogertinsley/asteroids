package Asteroids.Client;

import java.awt.Polygon;

/**
 *  This abstract class is inherit by classes within Asteroids
 *  @author Roger Tinsley.
 */ 

public abstract class GameObject {
	
	public Polygon sprite; 
	public Polygon shape;
	public Position position;
	public Velocity velocity;
	
	/**
	 * Sets the position.
	 * @param x x co-ordinate
	 * @param y y co-ordinate.
	 * @return void.
	 */
	public void setPosition(int x, int y) {
		position.setX(x);
		position.setY(y);
	}
	
	/**
	 * gets the position.
	 * @return position.
	 */
	public Position getPosition() {
		return position;
	}
	
	/**
	 * gets the velocity.
	 * @return velocity.
	 */
	public Velocity getVelocity() {
		return velocity;
	}
	
	/**
	 * gets the sprite.
	 * @return java.awt.Polygon.
	 */
	public Polygon getPolygon() {
		return sprite;
	}

	/**
	 * adds a point to the Polygon
	 * @param x x co-ordinate.
	 * @param y y co-ordinate.
	 * @return void.
	 */
	public void addPoint(int x, int y) {
		shape.addPoint(x, y);
	}

	/**
	 * increase the velocity by a delta value
	 * @param deltaSpeed how much to increase the speed by.
	 * @return void.
	 */
	public void accelerate(int deltaSpeed) {
		int dx, dy;
		dx = (int) Math.round(deltaSpeed * Math.cos(position.getAngleRadians()));
    	dy = (int) Math.round(deltaSpeed * -Math.sin(position.getAngleRadians()));
     	velocity.increaseX(dx);
    	velocity.increaseY(dy);	
	}
	
	/**
	 * increase the angle by a delta value
	 * @param deltaAngle how many degrees to increase the angle.
	 * @return void.
	 */
	public void turn(int deltaAngle) {
		position.increaseAngle(deltaAngle);
	}
	
	/**
	 * increases the position according to the current velocity
	 * @return void.
	 */
	public void move() {
	    position.increaseX(velocity.getX());	
	    position.increaseY(velocity.getY());
  	}
	
	/**
	 * Renders the sprite to its new position
	 * @return void.
	 */
	public void render() {
		sprite = new Polygon();
		double angle = position.getAngleRadians();
		int x = position.getX();
		int y = position.getY();
		for (int i=0; i < shape.npoints; i++) {
			double pAngle = Math.toRadians(shape.xpoints[i]);
			int pLength = shape.ypoints[i];
			sprite.addPoint(x + getCos(angle + pAngle, pLength), y - getSin(angle + pAngle, pLength));
		}
  	}
	
	/**
	 * checks to see if two polygons are overlapping
	 * @param polygon polygon to check
	 * @return boolean.
	 */
	public synchronized boolean isColliding(Polygon polygon) {
	    for (int i=0; i<polygon.npoints; i++)
      		if (sprite.contains(polygon.xpoints[i], polygon.ypoints[i]))
        		return true;
		
    	for (int i = 0; i<sprite.npoints; i++)
      		if (polygon.contains(sprite.xpoints[i], sprite.ypoints[i]))
        		return true;
		
    	return false;
  	}	

	/**
	 * returns the Cosine of an angle
	 * @param angle angle to Cosine.
	 * @param multiplier need to multiply because there are only 3 values, -1,0,1.
	 * @return the cosine of the angle
	 */
	private int getCos(double angle, int multiplier) {
		return (int) Math.round(multiplier * Math.cos(angle));
	}
	
	/**
	 * returns the Sin of an angle
	 * @param angle angle to Sin.
	 * @param multiplier need to multiply because there are only 3 values, -1,0,1.
	 * @return the Sin of the angle
	 */
	private int getSin(double angle, int multiplier) {
		return (int) Math.round(multiplier * Math.sin(angle));
	}


	
}
