package Asteroids.Client;
	
import concurrent.*;
import java.awt.*;
import Asteroids.Server.*;

/**
 *  This class maintains the asteroids on the client side
 *  @see Asteroids.Client.GameObject
 *  @author Roger Tinsley.
 */ 

public class Asteroid extends GameObject  {
	
	private boolean small; 
	private boolean active = true;
	
	/**
	 * Asteroid Constructor
	 * Inisializes the position and sets the new asteroid to small
	 * @see package.class
	 */
	public Asteroid() {
		position = new Position(RandomInt.integer(0,500), RandomInt.integer(0,360),
				RandomInt.integer(0,360));		
		small = false;
	}		

	/**
	 * sets the variable active
	 * @param b a boolean value.
	 */
	public void setActive(boolean b) {
		active = b;
	}
	
	/**
	 * sets the variable small
	 * @param b a boolean value.
	 */
	public void setSmall(boolean b) {
		small = b;
	}

	/**
	 * checks to see whether or not the asteroid is small
	 * @return boolean.
	 */	
	public boolean isSmall() {
		return small;
	}

}
