package Asteroids.Client;
	
import Asteroids.Client.Constants;

/**
 *  Holds a position for each game object.
 *  @see Asteroids.Client.Mechanics
 *  @author Roger Tinsley.
 */ 

public class Position extends Mechanics {
	
	private int angle;
	
	/**
	 * Position constructor
	 * @param initX the x co-ordinate.
	 * @param initY the y co-ordinate.
	 * @param initAngle the angle.
	 */
	public Position(int initX, int initY, int initAngle) {
		super(true, initX, initY, 0, Constants.CLIENT_WIDTH, 0, Constants.CLIENT_HEIGHT);		
		angle = initAngle;
	}
	
	/**
	 * converts the object to a string
	 * @return String.
	 */
	public String toString() {
		return super.toString() + "   angle= " + angle;
	}
	
	/**
	 * sets the position
	 * @param p the position to set.
	 * @return void.
	 */
	public void setPosition(Position p) {
		setX(p.getX());
		setY(p.getY());
		setAngle(p.getAngle());
	}
	
	/**
	 * gets the angle
	 * @return integer - angle that is returned
	 */
	public int getAngle() {
		return angle;
	}
	
	/**
	 * set the angle
	 * @param anAngle the new angle.
	 * @return void.
	 */
	public void setAngle(int anAngle) {
		angle = anAngle;
	}
	
	/**
	 * increase the angle by deltaAngle
	 * @param deltaAngle the new angle.
	 * @return void.
	 */
	public void increaseAngle(int deltaAngle) {
		angle = increase(angle, deltaAngle, 0, 360);
	}
	
	/**
	 * returns the current angle as radians
	 * @return double - the angle in radians.
	 */
	public double getAngleRadians() {
		return Math.toRadians(angle);
	}

}

