package Asteroids.Server;

import Asteroids.Client.Position;
import Asteroids.Client.Velocity;
import Asteroids.Client.Constants;

/**
 *  This class manages the ServerLasers
 *  @author Roger Tinsley.
 */ 

public class ServerLaserManager {
	
	public static ServerLaser laserArray[][] = new ServerLaser[ServerConstants.MAX_CLIENTS][Constants.MAX_SHOTS];
	
	/**
	 * addLaser to the laserArray array
	 * @param user the user who the laser will be assigned to.
	 * @param position the position of the laser.
	 * @return void.
	 */
	public static synchronized void addLaser(User user, Position position) {
		for (int laserNumber = 0; laserNumber < laserArray[user.getUserId()].length; laserNumber++) {
			if (laserArray[user.getUserId()][laserNumber] == null) {
				laserArray[user.getUserId()][laserNumber] = new ServerLaser(position, user, laserNumber);
				CommandDispatcher.createClientLaser(position, laserNumber, user.getUserId());
				break;
			}
		}
	}
	
	/**
	 * Remove the laser from the array
	 * @param uid the user id of the owner.
	 * @param laserNumber the unique number of the laser.
	 * @return void.
	 */
	public static synchronized void removeLaser(int uid, int laserNumber) {
		ServerLaserManager.laserArray[uid][laserNumber] = null;	
		CommandDispatcher.sendRemoveLaserCommand(uid, laserNumber);
	}	
	
	/**
	 * Remove all the lasers assigned to a user
	 * @param uid the user who owns the lasers.
	 * @return void.
	 */
	public static void removeAllLasers(int uid) {
		for (int i = 0; i < ServerLaserManager.laserArray[uid].length; i++) {
			ServerLaserManager.laserArray[uid][i] = null;
		}
	}
}
