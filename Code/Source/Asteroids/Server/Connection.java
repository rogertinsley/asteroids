package Asteroids.Server;
	
import java.net.*;
import java.io.*;
	
/**
 *  This class maintains the connection between client and server
 *  @see java.lang.Thread
 *  @author Roger Tinsley.
 */ 

public class Connection extends Thread {
	
	private Socket socket;
	private DataInputStream in;
	private DataOutputStream out;
	private boolean terminated = false;
	private User user;
	public CommandHandler commandHandler;

	
	/**
	 * Constructor for connection class
	 * @param aSocket a socket.
	 * @param ch the command handler.
	 * @param aUser the user associated with the connection.
	 */
	public Connection(Socket aSocket, CommandHandler ch, User aUser) {
		socket = aSocket;
		user = aUser;
		try {
				in = new DataInputStream(socket.getInputStream());
				out = new DataOutputStream(socket.getOutputStream());
		}catch (IOException e) {
			try{socket.close();} catch(IOException ioe) {System.out.println("Error closing socket." + ioe);}
		}
		commandHandler = ch;
		this.start();
	}
	
	/**
	 * gets the input stream
	 * @return DataInputStream.
	 */
	public DataInputStream getInputStream() {
		return in;
	}
	
	/**
	 * sets the terminated flag
	 * @param aBoolean boolean, either true of false.
	 * @return void.
	 */
	public void setTerminated(boolean aBoolean) {
		terminated = aBoolean;
	}
	
	/**
	 * reads in an integer from the socket and passes it to the processCommand method
	 * @return void.
	 */
	private void read() {
		int i;
		while (!terminated) {
			try {
				i = in.readInt();
				synchronized(this) {
					if (!terminated) {
						if (i == -1) {
							System.out.println("Socket Closed at other end.");
							close();
							UserManager.removeUser(user.getUserId());
							break;
						}
					    processCommand(i);
					}
				}
			}
			catch (IOException e) {
				commandHandler.close();
			}	
		}
	}
	
	
	/**
	 * writes an integer to the socket
	 * @param i the integer to write.
	 * @return void.
	 */	
	public void write(int i) {
		if (!terminated) {
			try {
				out.writeInt(i);
				out.flush();
			}catch (IOException e){System.out.println("error writing: "+e); terminated = true;}
		}
	}

	/**
	 * closes the connection
	 * @return void.
	 * @see package.class
	 */
	public synchronized void close() {
		terminated = true;
		try {
			socket.close();
		} catch (IOException e) {System.out.println("Error closing socket." + e);}
	}
	
	/**
	 * determines which command to execute
	 * passes the commandId to the processCommand method
	 * @param commandId command to process.
	 * @return description.
	 * @see package.class
	 */
	public void processCommand (int commandId) {
		commandHandler.processCommand(commandId);
	}

	/**
	 * the main thread for the connection Class
	 * keeps reading in from the socket
	 * @return void.
	 */
	public void run() {
		read();
	}
	
	
}
