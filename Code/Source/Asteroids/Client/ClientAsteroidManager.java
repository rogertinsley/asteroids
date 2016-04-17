package Asteroids.Client;

import Asteroids.Server.*;
import Asteroids.Client.Position;
import java.awt.*;
import concurrent.*;

/**
 *  This class manages the ClientAsteroids
 *  @author Roger Tinsley.
 */ 
	
public class ClientAsteroidManager {
	
	private static int asteroidsSpeed;
	
	public static Asteroid asteroidArray[] = new Asteroid[ServerConstants.MAX_ASTEROIDS];
	public static Asteroid smallAsteroidArray[] = new Asteroid[2 * ServerConstants.MAX_ASTEROIDS];

	/**
	 * creates new asteroids
	 * @return void.
	 */	
	public static void addAllAsteroids() {
		for (int i=0; i < asteroidArray.length; i++) {
			asteroidArray[i] = new Asteroid();
			createRandomAsteroid(i);
		}
	}
	
	/**
	 * Adds an asteroid to the asteroidArray only if that
	 * location is null.
	 * @param asteroidNumber the asteroid number to add.
	 */
	public static void addAsteroid(int asteroidNumber) {
		if (asteroidArray[asteroidNumber] == null) {
			asteroidArray[asteroidNumber] = new Asteroid();
			createRandomAsteroid(asteroidNumber);
		}
	}
	
	/**
	 * adds a small asteroid to the smallAsteroidArray array.
	 * @param asteroidNumber unique id for small asteroid.
	 * @param aX x co-ordinate of asteroid.
	 * @param aY y co-ordinate of asteroid.
	 * @return void.
	 */
	public static void addSmallAsteroid(int asteroidNumber, int aX, int aY) {
		smallAsteroidArray[asteroidNumber] = new Asteroid();
		smallAsteroidArray[asteroidNumber].setPosition(aX, aY);
		smallAsteroidArray[asteroidNumber].shape = new Polygon();
	    int s = Constants.MIN_ROCK_SIDES + (int) (Math.random() * (Constants.MAX_ROCK_SIDES - Constants.MIN_ROCK_SIDES));
	    for (int j = 0; j < s; j ++) {
	    	int theta = (360 / s) * j;
	        int r = (Constants.MIN_ROCK_SIZE + (int) (Math.random() * (Constants.MAX_ROCK_SIZE - Constants.MIN_ROCK_SIZE))) / 2;
	        smallAsteroidArray[asteroidNumber].shape.addPoint(theta, r);
	     }
        smallAsteroidArray[asteroidNumber].render();
        smallAsteroidArray[asteroidNumber].setSmall(true);		
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
	 * updates the position of a small asteroid
	 * @param userId unique id for asteroid.
	 * @param aX x co-ordinate of asteroid.
	 * @param aY y co-ordinate of asteroid.
	 * @return void.
	 */
	public static void moveSmallAsteroid(int userId, int x, int y) {
		if (smallAsteroidArray[userId] != null) {
			Position position = smallAsteroidArray[userId].getPosition();
			position.setX(x);
			position.setY(y);
		}
	}
	
	/**
	 * updates the angle of an asteroid
	 * not implemented, but left in for future expansion
	 * @param userId unique id for asteroid.
	 * @param angle new angle
	 * @return void.
	 */
	public static void turnAsteroid(int userId, int angle) {
		if (asteroidArray[userId] != null) {
			Position position = asteroidArray[userId].getPosition();
			position.setAngle(angle);
		}
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
	 * Creates the random shape for the asteroid
	 * @param asteroidNumber asteroid Id to create
	 * @return void.
	 */
	public static void createRandomAsteroid(int asteroidNumber) {
		asteroidsSpeed = Constants.MIN_ROCK_SPEED;
		asteroidArray[asteroidNumber].shape = new Polygon();
  		int s = Constants.MIN_ROCK_SIDES + (int) (Math.random() * (Constants.MAX_ROCK_SIDES - Constants.MIN_ROCK_SIDES));
  		for (int j = 0; j < s; j ++) {
    		int theta = (360 / s) * j;
    		int r = Constants.MIN_ROCK_SIZE + (int) (Math.random() * (Constants.MAX_ROCK_SIZE - Constants.MIN_ROCK_SIZE));
    		asteroidArray[asteroidNumber].shape.addPoint(theta, r);
  		}
  		
		asteroidArray[asteroidNumber].render();
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
	 * removes a small asteroid from the smallAsteroidArray array
	 * @param asteroidNumber id of asteroid to remove.
	 * @return void.
	 */
	public static void removeSmallAsteroid(int asteroidNumber) {
		if (Client.sound) Client.explosionSound.play();
		smallAsteroidArray[asteroidNumber] = null;
	}
	
	/**
	 * Splits a big asteroid into two smaller asteroids
	 * @param asteroidNumber id of asteroid to remove.
	 * @return void.
	 */
	public static void splitAsteroid(int asteroidNumber) {
		if (Client.sound) Client.explosionSound.play();
		removeAsteroid(asteroidNumber);
		smallAsteroidArray[asteroidNumber] = new Asteroid();
		smallAsteroidArray[asteroidNumber+1] = new Asteroid();
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
	}
	
}

