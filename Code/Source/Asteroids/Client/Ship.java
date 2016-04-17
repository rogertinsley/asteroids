package Asteroids.Client;
	
import java.awt.*;
import concurrent.*;
import Asteroids.Client.Constants;

/**
 *  This class maintains a client side ship
 *  @see Asteroids.Client.GameObject
 *  @author Roger Tinsley.
 */ 

public class Ship extends GameObject  {
	
	private boolean invincible = false;
	private boolean active;
	
	/**
	 * Ship Constructor
	 * @param x x co-ordinate.
	 * @param y y co-ordinate.
	 */
	public Ship(int x, int y) {
		position = new Position(x,y,0);
		shape = new Polygon();
		sprite = new Polygon();
		
		shape.addPoint(0, 10);
		shape.addPoint(120, 5);
		shape.addPoint(240, 5);
		
		invincible = true;
		active = true;
	}		
	
	/**
	 * Checks if the ship is active
	 * @return boolean, true if the ship is active.
	 */
	public boolean isActive() {
		return active;
	}
	
	/**
	 * sets active to a boolean value
	 * @param value value to set active to.
	 * @return void.
	 */
	public void setActive(boolean value) {
		active = value;
	}		
	
	/**
	 * Checks if the ship is invincible
	 * @return boolean, true if the ship is invincible.
	 */
	public boolean isInvincible() {
		return invincible;
	}
	
	/**
	 * sets invincible to a boolean value
	 * @param value value to set invincible to.
	 * @return void.
	 */
	public void setInvincible(boolean value) {
		invincible = value;
	}

}
