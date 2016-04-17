package Asteroids.Server;
	
import Asteroids.Server.*;	
import concurrent.*;

/**
 *  The main class on the server side, starts listener.
 *  @author Roger Tinsley.
 */ 

public class Server
{
	public static TCPIPListener tcpipListener;
	
	/**
	 * Main for server
	 * starts listener, creates game.
	 */
	public static void main(String[] args) {
		System.out.println();
		System.out.println("Initialising...");
		System.out.println();
		Game game = new Game();	
		System.out.println();
		System.out.println("Game Created.");System.out.println();
		TCPIPAddress address = new TCPIPAddress("localhost 3210");
		tcpipListener = new TCPIPListener(address);
		tcpipListener.start();
		System.out.println("TCP/IP Listener Initialised.");
		System.out.println("Server started... Waiting for connections...");System.out.println();
		System.out.println("To shutdown server hit: control-c");
	}

	
	/**
	 * closes the TCP/IP Listener
	 * @return void.
	 */
	public static void closeTcpipListener() {
		tcpipListener.close();
	}
}

