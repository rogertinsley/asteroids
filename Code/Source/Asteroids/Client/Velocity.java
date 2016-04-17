package Asteroids.Client;

/**
 *  This class holds the velocity for game objects
 *  @see Asteroids.Client.Mechanics
 *  @author Roger Tinsley.
 */ 

public class Velocity extends Mechanics {
	
	/**
	 * Velocity constructor
	 * @param initX x value.
	 * @param initY y value.
	 */
	public Velocity(int initX, int initY) {
		super(false, initX, initY, -Constants.SPEED_LIMIT, Constants.SPEED_LIMIT, -Constants.SPEED_LIMIT, Constants.SPEED_LIMIT);		
	}

}

