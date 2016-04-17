package Asteroids.Server;
	
import java.net.*;
import java.io.*;

/**
 *  This class manages the users connected to the system
 *  @author Roger Tinsley.
 */ 
	
public class UserManager {
	
	public static User userArray[] = new User[ServerConstants.MAX_CLIENTS]; //make private
	
	/**
	 * Creates new user
	 * Adds a new user to the userArray array
	 * @param socket a socket.
	 * @return void.
	 * @exception ConnectionLimitExceededException if full then throw exception.
	 */
	public static void createUser(Socket socket) throws ConnectionLimitExceededException { 
		int userId = -1;
		for (int i=0; i < userArray.length; i++) {
			if (userArray[i] == null) {
				userId = i;
				userArray[userId] = new User(userId, socket, 0);
				userArray[userId].connection.write(1); // go-ahead flag to client
				userArray[userId].connection.write(userId);
				System.out.println("User added: id: " + userId);
				ServerShipManager.addShip(userId);
				CommandDispatcher.sendInitCommand();
			    break;
			}
		}
		if (userId == -1) {
			DataOutputStream out = null;
			try {
				out = new DataOutputStream(socket.getOutputStream());
			}catch (IOException e) {
				try{
					socket.close();} 
				catch(IOException ioe) {System.out.println("Error closing socket." + ioe);}
			}
			try {
				out.writeInt(0);
				out.flush();
			}catch (IOException e){System.out.println("error writing: "+e);}
			throw new ConnectionLimitExceededException("Server Full");
		}
	}
	
	/**
	 * Remove a user 
	 * Removes a user from the userArray array
	 * @param userId the id of the user to remove.
	 * @return void.
	 */
	public synchronized static void removeUser(int userId) {
		if ( !(userId > userArray.length) ) {
			userArray[userId].close();
			userArray[userId].serverCommandHandler = null;
			userArray[userId] = null;
			ServerShipManager.removeShip(userId);
			CommandDispatcher.sendRemoveShipCommand(userId);
			System.out.println("User removed: id: " + userId);
		}
	}	
	
	/**
	 * Removes all users from the userArray array
	 * @return void.
	 */
	public synchronized static void removeAllUsers() {
		for (int i=0; i < userArray.length; i++) {
			removeUser(i);
		}
	}
	
	/**
	 * Writes to all the users
	 * @param j data to send to all the users.
	 * @return void.
	 */
	public synchronized static void writeToAllUsers(int j) {
		for (int i=0; i < userArray.length; i++)  {
			write(i, j);
		}
	}
	
	/**
	 * writes data to a single user
	 * @param i user to write too.
	 * @param j data to write.
	 * @return void.
	 */
	public synchronized static void write(int i, int j) {
		if (userArray[i] != null) 
			userArray[i].connection.write(j);
	}

	/**
	 * returns the user id
	 * @param i the user.
	 * @return integer - the user id.
	 */
	public static int getUserId(int i) {
		return userArray[i].getUserId();
	}
	
}
