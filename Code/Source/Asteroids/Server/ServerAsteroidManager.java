package Asteroids.Server;

import Asteroids.Server.*;
import Asteroids.Client.*;
import java.awt.*;
import concurrent.*;

/**
 *  This class manages the server side asteroids
 *  @author Roger Tinsley.
 */ 

public class ServerAsteroidManager {

	private static int asteroidsSpeed;
	public static ServerAsteroid asteroidArray[] = new ServerAsteroid[ServerConstants.MAX_ASTEROIDS];
	public static ServerAsteroid smallAsteroidArray[] = new ServerAsteroid[2 * ServerConstants.MAX_ASTEROIDS];
	
	
	/**
	 * checks a serverAsteroid array to see if it is empty.
	 * @param asteroid an ServerAsteroid array.
	 * @return boolean flag, true if it is empty.
	 */
	public static boolean isEmpty(ServerAsteroid[] asteroid) {
		for (int i=0; i < asteroid.length; i++) 
			if (asteroid[i] != null)
				return false;
		return true;		
	}
	
	/**
	 * creates new asteroids
	 * @return void.
	 */	
	public static void addAllAsteroids() {
		asteroidsSpeed = Constants.MIN_ROCK_SPEED;
		for (int i=0; i < asteroidArray.length; i++) {
			asteroidArray[i] = new ServerAsteroid(ServerConstants.BIG_ASTEROID_VELOCITY_X, ServerConstants.BIG_ASTEROID_VELOCITY_Y); 
      		int s = Constants.MIN_ROCK_SIDES + (int) (Math.random() * (Constants.MAX_ROCK_SIDES - Constants.MIN_ROCK_SIDES));
      		for (int j = 0; j < s; j ++) {
        		int theta = (360 / s) * j;
        		int r = Constants.MIN_ROCK_SIZE + (int) (Math.random() * (Constants.MAX_ROCK_SIZE - Constants.MIN_ROCK_SIZE));
        		asteroidArray[i].addPoint(theta, r );
      		}
			asteroidArray[i].startThread();
			System.out.println("created asteroid");
    	}
	}
	
	/**
	 * removes an asteroid from the asteroidArray array
	 * @param asteroidNumber id of asteroid to remove.
	 * @return void.
	 */
	public static void removeAsteroid(int asteroidNumber) {
		asteroidArray[asteroidNumber] = null;
	}

	/**
	 * updates the position of a big asteroid
	 * @param userId unique id for asteroid.
	 * @param aX x co-ordinate of asteroid.
	 * @param aY y co-ordinate of asteroid.
	 * @return void.
	 */
	public static void moveAsteroid(int userId, int x, int y) {
		if (asteroidArray[userId] != null) {
			Position position = asteroidArray[userId].getPosition();
			position.setX(x);
			position.setY(y);
		}
	}
	/**
	public static void turnAsteroid(int userId, int angle) {
		if (asteroidArray[userId] != null) {
			Position position = asteroidArray[userId].getPosition();
			position.setAngle(angle);
		}
	}
	**/
	public static void removeSmallAsteroid(int asteroidNumber) {
		smallAsteroidArray[asteroidNumber] = null;
	}
	
	/**
	 * gets the positon of the current asteroid
	 * @param userId id of the asteroid.
	 * @return a position.
	 */
	public static Position getPosition(int userId) {
		return asteroidArray[userId].getPosition();
	}

	/**
	 * gets the velocity of the current asteroid
	 * @param userId id of the asteroid.
	 * @return a position.
	 */
	public static Velocity getVelocity(int userId) {
		return asteroidArray[userId].getVelocity();
	}
	
	/**
	 * Splits a big asteroid into two smaller asteroids
	 * @param asteroidNumber id of asteroid to remove.
	 * @return void.
	 */
	public static void splitAsteroid(int asteroidNumber) {
		Position position = asteroidArray[asteroidNumber].getPosition();
		removeAsteroid(asteroidNumber);
		smallAsteroidArray[asteroidNumber] = new ServerAsteroid(ServerConstants.SMALL_ASTEROID_VELOCITY_X, ServerConstants.SMALL_ASTEROID_VELOCITY_Y);
		smallAsteroidArray[asteroidNumber+1] = new ServerAsteroid(ServerConstants.SMALL_ASTEROID_VELOCITY_X, ServerConstants.SMALL_ASTEROID_VELOCITY_Y);
		smallAsteroidArray[asteroidNumber].setPosition(position.getX()+15, position.getY() );		
		smallAsteroidArray[asteroidNumber+1].setPosition(position.getX()-15, position.getY() );	
		smallAsteroidArray[asteroidNumber].shape = new Polygon();
		smallAsteroidArray[asteroidNumber+1].shape = new Polygon();
	    int s = Constants.MIN_ROCK_SIDES + (int) (Math.random() * (Constants.MAX_ROCK_SIDES - Constants.MIN_ROCK_SIDES));
	    for (int j = 0; j < s; j ++) {
	    	int theta = (360 / s) * j;
	        int r = (Constants.MIN_ROCK_SIZE + (int) (Math.random() * (Constants.MAX_ROCK_SIZE - Constants.MIN_ROCK_SIZE))) / 2;
	        smallAsteroidArray[asteroidNumber].shape.addPoint(theta, r);
	     }
		for (int j = 0; j < s; j ++) {
	    	int theta = (360 / s) * j;
	        int r = (Constants.MIN_ROCK_SIZE + (int) (Math.random() * (Constants.MAX_ROCK_SIZE - Constants.MIN_ROCK_SIZE))) / 2;
	        smallAsteroidArray[asteroidNumber+1].shape.addPoint(theta, r);
	     }
        smallAsteroidArray[asteroidNumber].render();
		smallAsteroidArray[asteroidNumber+1].render();
        smallAsteroidArray[asteroidNumber].setSmall(true);
		smallAsteroidArray[asteroidNumber+1].setSmall(true);
		smallAsteroidArray[asteroidNumber].startThread();
		smallAsteroidArray[asteroidNumber+1].startThread();
	}
}
