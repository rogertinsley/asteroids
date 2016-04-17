package Asteroids.Client;

import java.awt.*;
import concurrent.*;
import Asteroids.Client.Constants;
import Asteroids.Server.*;

/**
 *  This class maintains the ClientLaser
 *  @see Asteroids.Client.GameObject
 *  @author Roger Tinsley.
 */ 

public class ClientLaser extends GameObject {

	private boolean initialised = false;
	private int laserNumber;
	
	/**
	 * ClientLaser constructor
	 * @param x x co-ordinate.
	 * @param y y co-ordinate.
	 * @param vAngle angle.
	 */
	public ClientLaser(int x, int y, int vAngle) {
		position = new Position(x, y, vAngle);
		
		shape = new Polygon();
		sprite = new Polygon();
		initialised = true;
		
		shape.addPoint(5, 3 );
  		shape.addPoint(355, 3);
  		shape.addPoint(175, 3);
  		shape.addPoint(185, 3);
		render();
	}
	
	/**
	 * checks to see if laser is initialised
	 * @return boolean.
	 */
	public boolean isInitialised() {
		return initialised;
	}
	
	/**
	 * sets initialised
	 * @param b boolean.
	 * @return void.
	 */	
	public void setInitialised(boolean b) {
		initialised = b;	
  	}
}
