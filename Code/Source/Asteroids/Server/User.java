package Asteroids.Server;
	
import java.net.*;
import Asteroids.Client.*;

/**
 *  This class maintains any users connected to the system
 *  @author Roger Tinsley.
 */ 
	
public class User {
	
	private int userId;
	private int numberOfLives = Constants.NUMBER_OF_LIVES;
	public Connection connection;
	public int score = 0;
	public ServerCommandHandler serverCommandHandler;
	public ClientCommandHandler clientCommandHandler;
	
	
	/**
	 * Constructor for User
	 * @param uid unique uid assigned to the user.
	 * @param socket a socket.
	 * @param flag flag for the command handler.
	 */
	public User(int uid, Socket socket, int flag) {
		userId = uid;
		if (flag == 0) {
			serverCommandHandler = new ServerCommandHandler(this);
			connection = new Connection(socket, serverCommandHandler, this);
		}
		else { 
			clientCommandHandler = new ClientCommandHandler(this);
			connection = new Connection(socket, clientCommandHandler, this);
		}
	}
	
	/**
	 * returns the number of lives
	 * @return integer - the number of lives.
	 */
	public int getNumberOfLives() {
		return numberOfLives;
	}

	/**
	 * sets the number of lives to a new value
	 * @param newValue the new amount of lives.
	 * @return void.
	 */	
	public void setNumberOfLives(int newValue) {
		numberOfLives = newValue;
	}
	
	/**
	 * updates the score by a delta value
	 * @param value the amount to update the score by
	 * @return void.
	 */
	public void updateScore(int value) {
		score = score + value;
	}
	
	/**
	 * close teh connection
	 * @return void.
	 */
	public void close() {
		connection.close();
	}
	
	/**
	 * returns the user Id
	 * @return integer - the user id
	 */
	public int getUserId() {
		return userId;
	}
}

