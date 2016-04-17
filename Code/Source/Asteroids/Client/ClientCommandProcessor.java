package Asteroids.Client;

import Asteroids.Server.*;

/**
 *  This class issues commands to the server
 *  @author Roger Tinsley.
 */ 
	
public class ClientCommandProcessor {
	
	private User user;
	private Connection connection;
	
	/**
	 * ClientCommandProcessor Constructor
	 * @param aUser user assigned to the ClientCommandProcessor
	 */
	public ClientCommandProcessor(User aUser) {
		user = aUser;
		connection = user.connection;
	}

	/**
	 * sends a command to the connection
	 * @param commandId command to send.
	 * @param data any other data to send.
	 * @return void.
	 */
	public synchronized void sendCommand(int commandId, int data) {
		connection.write(commandId);
		connection.write(data);
	}

	/**
	 * sends a command to the connection
	 * @param commandId command to send.
	 * @return void.
	 */
	public synchronized void sendCommand(int commandId) {
		connection.write(commandId);
	}

}
