package Asteroids.Server;
	
import java.awt.*;
import Asteroids.Client.*;

/**
 *  This class maintains the ServerShips
 *  @see Asteroids.Client.GameObject
 *  @author Roger Tinsley.
 */ 

public class ServerShip extends GameObject implements Runnable  {
	
	private boolean active = false;
	private boolean invincible;
	private int counter;

	/**
	 * Constructor of the serverShip
	 * @param p the position where the ServerShip is to created.
	 */
	public ServerShip(Position p) {
		position = new Position(p.getX(),p.getY(),0);
		velocity = new Velocity(0,0);
		shape = new Polygon();
		sprite = new Polygon();
		shape.addPoint(0, 10);
		shape.addPoint(120, 5);
		shape.addPoint(240, 5);
		counter=0;
		active = true;
		invincible = true;
		Thread shipThread = new Thread(this);
		shipThread.start();
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
	 * Checks if the ship is active
	 * @return boolean, true if the ship is active.
	 */
	public boolean isActive() {
		return active;
	}
	
	/**
	 * Checks if the ship is invincible
	 * @return boolean, true if the ship is invincible.
	 */
	public boolean isInvincible() {
		return invincible;
	}
	
	/**
	 * sets the invincible counter to 0
	 * @return void.
	 */
	public void resetInvincibleCounter() {
		counter = 0;
	}
	
	/**
	 * main thread for the servership
	 * renders the ship then moves it, counter is for checking the invinciblity of the ship
	 * @return void.
	 */
	public void run() {
		while (active) {
			if (counter < 50) {
				invincible = true;
				counter++;
			}
			else {
				invincible = false;
			}
			render();
			move();
			try {
				Thread.sleep(75);
			}
			catch (InterruptedException e) {System.err.println("error sending ship thread to sleep: " + e);}
		}
	}
}
