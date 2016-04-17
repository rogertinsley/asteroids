package Asteroids.Server;

import Asteroids.Client.*;
import java.io.*;
import java.net.*;

/**
 *  This class processes the client commands on the server side
 *  @see CommandHandler
 *  @author Roger Tinsley.
 */ 

public class ServerCommandHandler implements CommandHandler {
	
	private User user;
	private boolean alive = false;
	
	/**
	 * ServerCommandHandler constructor
	 * @param aUser user assigned to the serverCommandHandler.
	 */
	public ServerCommandHandler(User aUser) {
		user = aUser;
		alive = true;
	}

	
	/**
	 * determines which command to process
	 * @param commandId the id of the command to execute.
	 * @return void.
	 */
	public synchronized void processCommand(int commandId) {
		if (commandId == 1) {
			processMoveCommand();
		}
		else if (commandId == 2) {
				processTurnCommand();		
		}
		else if (commandId == 3) {
				processShootLaserCommand();
		}
		
		else if (commandId == 1000); // ignore
			
		else
			System.out.println("Client Command not found: "+commandId);
	}

	/**
	 * close the connection
	 * @return void.
	 */
	public void close() {
		user.connection.setTerminated(true);
		UserManager.removeUser(user.getUserId());
	}

	/**
	 * process a move command
	 * @return void.
	 */
	private synchronized void processMoveCommand() {
		ServerShipManager.shipArray[user.getUserId()].accelerate(Constants.ACCELERATION);
	}
	
	/**
	 * process a turn command
	 * @return void.
	 */
	private synchronized void processTurnCommand() {
		DataInputStream in = user.connection.getInputStream();
		int result = -1;
		try {
			result = in.readInt();
		} catch (IOException e) {System.err.println("error getting input stream: " + e);}
		
		if (result == Constants.TURN_RIGHT)
			ServerShipManager.shipArray[user.getUserId()].turn(-Constants.ANGLE_TO_TURNSHIP);
		
		else if (result == Constants.TURN_LEFT) 
				ServerShipManager.shipArray[user.getUserId()].turn(Constants.ANGLE_TO_TURNSHIP);
	}
	
	/**
	 * process a shoot laser command
	 * @return void.
	 */
	private synchronized void processShootLaserCommand() {
		if (ServerShipManager.shipArray[user.getUserId()].isActive())
			ServerLaserManager.addLaser(user, ServerShipManager.shipArray[user.getUserId()].getPosition());
	}
		
}
