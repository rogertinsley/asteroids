package Asteroids.Server;
	
import java.awt.*;
import Asteroids.Client.*;
import concurrent.*;

/**
 *  This class maintains a server side asteroid
 *  @see Asteroids.Client.GameObject
 *  @author Roger Tinsley.
 */ 

public class ServerAsteroid extends GameObject implements Runnable  {
	
	private boolean small; 
	private boolean active = true;
	private Thread asteroidThread;
	
	/**
	 * Constructor for a serverAsteroid
	 * @param maxVelocityX maximum velocity in the x direction.
	 * @param maxVeloictyY maximum velocity in the y direction.
	 */
	public ServerAsteroid(int maxVelocityX, int maxVeloictyY) {
		position = new Position(RandomInt.integer(0,500), RandomInt.integer(0,500),
				RandomInt.integer(0,360));
		int vx = RandomInt.integer(1, maxVelocityX);
		int vy = RandomInt.integer(1, maxVeloictyY);
		if (Math.random() < 0.5)
			vx = -vx;
		if (Math.random() < 0.5)
			vy = -vy;
		velocity = new Velocity(vx, vy); 
		
		small = false;
		shape = new Polygon();
		
		asteroidThread = new Thread(this);
	}		

	/**
	 * starts the asteroid thread.
	 * @return void.
	 */
	public void startThread() {
		asteroidThread.start();
	}

	
	/**
	 * Sets the active flag
	 * @param b boolean value to set active to.
	 * @return void.
	 */
	public void setActive(boolean b) {
		active = b;
	}
	
	/**
	 * Sets the small flag
	 * @param b boolean value to set small to.
	 * @return void.
	 */
	public void setSmall(boolean b) {
		small = b;
	}
	
	/**
	 * returns the small flag
	 * @return boolean - true/false depending on small flag.
	 */
	public boolean isSmall() {
		return small;
	}

	/**
	 * the main serverAsteroid thread
	 * @return void.
	 */
	public void run() {
		while (true) {
			render();
			move();
			try {
				Thread.sleep(75);
			}
			catch (InterruptedException e) {System.err.println("error sending asteroid thread to sleep: "+e);}
		}
	}
}
