package Asteroids.Client;

import Asteroids.Server.ServerConstants;
import Asteroids.Client.Constants;

/**
 *  This class manages the Client Lasers
 *  @author Roger Tinsley.
 */ 

public class ClientLaserManager {
	
	public static ClientLaser laserArray[][] = new ClientLaser[ServerConstants.MAX_CLIENTS][Constants.MAX_SHOTS];
	
	/**
	 * adds a clientLaser to the laserArray.
	 * @param x x co-ordinate.
	 * @param y y co-ordinate.
	 * @param angle angle of the laser.
	 * @param laserNumber unique laser id.
	 * @param uid unique user id.
	 * @return void.
	 */
	public static synchronized void createLaser(int x, int y, int angle, int laserNumber, int uid) {
		if (laserArray[uid][laserNumber] == null) {
			laserArray[uid][laserNumber] = new ClientLaser(x, y, angle);
			if (Client.sound) Client.fireSound.play();
		}
		else System.out.println("error creating laser: "+uid+" "+laserNumber+" "+x+" "+y+" "+angle);
	}
	
	/**
	 * Removes a laser from the laserArray
	 * @param uid unique id.
	 * @param laserNumber unique laser id.
	 * @return void.
	 */
	public static synchronized void removeLaser(int uid, int laserNumber) {
		laserArray[uid][laserNumber] = null;
	}

	/**
	 * updates the position of a laser
	 * @param uid unique user id.
	 * @param laserNumber unique laser number
	 * @param x x co-ordinate.
	 * @param y y co-ordinate.
	 * @return void.
	 */
	public static synchronized void moveLaser(int uid, int laserNumber, int x, int y) {
		if (laserArray[uid][laserNumber] != null) {
			Position position = laserArray[uid][laserNumber].getPosition();
			Velocity velocity = laserArray[uid][laserNumber].getVelocity();
			position.setX(x);
			position.setY(y);
		}	
	}
	
}
