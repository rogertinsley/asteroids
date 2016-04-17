package Asteroids.Client;

import Asteroids.Server.*;
import java.awt.Color;

/**
 *  This class manages client ships
 *  @author Roger Tinsley.
 */ 
	
public class ClientShipManager {
	
	public static Ship shipArray[] = new Ship[ServerConstants.MAX_CLIENTS];
	
	/**
	 * adds a ship to the shipArray
	 * @param userId user id of ship to add.
	 * @return void.
	 */
	public static void addShip(int userId) {
		System.out.println("User Added: " +userId);
		if (shipArray[userId] == null) 
			shipArray[userId] = new Ship(100,100); 		
	}

	
	/**
	 * updates the ships position
	 * @param userId unique user Id.
	 * @param x x co-ordinate.
	 * @param y y co-ordinate.
	 * @return void.
	 */
	public static void moveShip(int userId, int x, int y) {
		if (userId < shipArray.length) {
			if (shipArray[userId] != null) {
				Position position = shipArray[userId].getPosition();
				position.setX(x);
				position.setY(y);
			}
		}
	}

	/**
	 * sets the angle of a ship
	 * @param userId unique user Id.
	 * @param angle the new angle.
	 * @return void.
	 */
	public static void turnShip(int userId, int angle) {
		if (shipArray[userId] != null) {
			Position position = shipArray[userId].getPosition();
			position.setAngle(angle);
		}
	}
	
	
	/**
	 * gets the position of the ship
	 * @param userId id of the ship.
	 * @return position.
	 */
	public static Position getPosition(int userId) {
		return shipArray[userId].getPosition();
	}
	
	/**
	 * removes a ship from the shipArray
	 * @param userId unique user id.
	 * @return void.
	 */
	public static void removeShip(int userId) {
		shipArray[userId] = null;
	}

	/**
	 * gets the colour of the ship
	 * @param shipNumber ship id.
	 * @return java.awt.Color.
	 */
	public static Color getColor(int shipNumber) {
		switch(shipNumber) {
		case 0:			
			return Color.blue;
		case 1:
			return Color.red;
		case 2:
			return Color.green;
		case 3:
			return Color.orange;
		default:
			return Color.white;
		}	
	}
	
	/**
	 * returns a random colour
	 * @return java.awt.Color.
	 */
	public static Color flash() {
		double test = Math.random();
		if (test < 0.2)
			return Color.blue;
		else if (test < 0.4 && test >= 0.2)
			return Color.red;
		else if (test < 0.6 && test >=0.4)
			return Color.green;
		else if (test < 0.8 && test >=0.6)
			return Color.orange;
		else if (test < 1.0 && test >= 0.8)
			return Color.white;
		return Color.black;
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
