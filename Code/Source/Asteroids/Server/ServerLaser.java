package Asteroids.Server;

import java.awt.*;
import concurrent.*;
import Asteroids.Client.*;

/**
 *  This class maintains a server laser
 *  @see Asteroids.Client.GameObject
 *  @author Roger Tinsley.
 */ 

public class ServerLaser extends GameObject implements Runnable {

	private Thread laserThread;
	private User user;
	private boolean initialised = false;
	private int laserNumber;

	
	/**
	 * ServerLaser constructor
	 * sets up velocity and position
	 * @param p position of the ship when laser was shot.
	 * @param aUser user who created the laser.
	 * @param aLaserNumber the number of the laser to be created.
	 */
	public ServerLaser(Position p, User aUser, int aLaserNumber) {
		laserNumber = aLaserNumber;
		user = aUser;
		position = new Position(p.getX(), p.getY(), p.getAngle());
		velocity = new Velocity((int) Math.round(ServerConstants.LASER_VELOCITY_X * Math.cos(p.getAngleRadians())), 
			(int) Math.round(ServerConstants.LASER_VELOCITY_Y * -Math.sin(p.getAngleRadians())));

		shape = new Polygon();
		sprite = new Polygon();
		
		initialised = true;

		shape.addPoint(5, 3 );
  		shape.addPoint(355, 3);
  		shape.addPoint(175, 3);
  		shape.addPoint(185, 3);
		render();

		laserThread = new Thread(this);
		laserThread.start();
	}
	
	/**
	 * returns the initialised flag
	 * @return boolean true if initialised.
	 */
	public boolean isInitialised() {
		return initialised;
	}
	
	/**
	 * sets the initialised flag 
	 * @param b value to set initialised.
	 * @return void.
	 */
	public void setInitialised(boolean b) {
		initialised = b;
	}

	/**
	 * the main thread of the server laser
	 * Renders the laser and then moves it. then when counter runs out it dies
	 * @return void.
	 */
	public void run() {
		for (int i=0; i<50; i++) {
			render();
			move();
			try {
				Thread.sleep(75);
			}
			catch (InterruptedException e) {System.err.println("error sending laser thread to sleep: "+e);}
		}
		initialised = false;
	}
}
