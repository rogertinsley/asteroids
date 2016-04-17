package Asteroids.Server;

import Asteroids.Client.*;

/**
 *  This class manages the ServerShips
 *  @author Roger Tinsley.
 */ 

public class ServerShipManager {
	
	public static ServerShip shipArray[] = new ServerShip[ServerConstants.MAX_CLIENTS];
	
	/**
	 * adds a ship to the shipArray
	 * @param uid user id of ship to add.
	 * @return void.
	 */
	public static void addShip(int uid) {
		if (shipArray[uid] == null) {
			shipArray[uid] = new ServerShip(determinePosition());
			shipArray[uid].setActive(true);
		}
	}
	
	/**
	 * removes a ship from the shipArray
	 * @param uid unique user id.
	 * @return void.
	 */
	public static void removeShip(int uid) {
		shipArray[uid].setActive(false);
		shipArray[uid] = null;
	}		
	
	/**
	 * sets a random position for the ship, used when the ship appears on the gaming area
	 * @return void.
	 */
	public static Position determinePosition() {
		return new Position(RandomInt.integer(0,500), RandomInt.integer(0,500), 270);
	}
	
	/**
	 * sets a new position for the ship
	 * sets velocity to 0.
	 * @param uid unique user id.
	 * @return void.
	 */
	public static void newPosition(int uid) {
		shipArray[uid].setPosition(Constants.CLIENT_WIDTH/2, Constants.CLIENT_HEIGHT/2);
		shipArray[uid].velocity.setX(0);
		shipArray[uid].velocity.setY(0);
	}
	
	/**
	 * suspends a ship by making it not active
	 * @param uid user id to suspend.
	 * @return void.
	 */
	public static void suspend(int uid) {
		shipArray[uid].setActive(false);
	}
	
	/**
	 * wakes a ship by making it active
	 * @param uid user id to wake.
	 * @return void.
	 */
	public static void wake(int uid) {
		shipArray[uid].setActive(true);
	}
	
}
