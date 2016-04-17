package Asteroids.Server;
	
import java.net.*;
import java.io.*;

/**
 *  This class maintains the TCP/IP Listener
 *  @see java.lang.Thread
 *  @author Roger Tinsley.
 */ 

public class TCPIPListener extends Thread {
	
	private TCPIPAddress tcpipAddress;
	private ServerSocket serverSocket;
	private boolean terminated = false;

	/**
	 * Constructor for the TCPIPListener
	 * @param address the TCPIPAddress to bind the listener too.
	 */
	public TCPIPListener(TCPIPAddress address) {
		tcpipAddress = address;
		InetAddress inetAddress;
		try {
			inetAddress = InetAddress.getByName(tcpipAddress.getIP());
		}
		catch (UnknownHostException e) {System.out.println("Error creating InetAddress: " +e );}
		try {
			serverSocket = new ServerSocket(tcpipAddress.getPort());
		}
		catch (IOException e) {System.out.println("Error creating ServerSocket: " +e );}		
	}
	
	
	/**
	 * Closes the listener
	 * @return void.
	 */
	public synchronized void close() {
		try {
			terminated = true;
			serverSocket.close();
		}
		catch (IOException e) {System.out.println("Error closing ServerSocket: " +e );}
	}
	
	
	/**
	 * main thread for the TCPIPListener
	 * adds an incoming connectiong to the usermanager
	 * @return void.
	 */
	public void run() {
		System.out.println("TCPIPListener started on " + tcpipAddress.getIP() + ":" + tcpipAddress.getPort() );
		Socket socket = null;
		while (!terminated) {
			try {
				socket = serverSocket.accept();
				System.out.println("Connection accepted: " + socket.getInetAddress());
				synchronized(this) {
					if (!terminated) {
						try {
							UserManager.createUser(socket);
						}
						catch (ConnectionLimitExceededException e) {System.out.println("connection rejected: "+e+" "+ socket.getInetAddress());
																		socket.close();}
					}
				}
			}
			catch (IOException e) {System.out.println("Error accepting socket: " +e );}
		}
		System.out.println("TCPIPListener stopped on " + tcpipAddress.getIP() + ":" + tcpipAddress.getPort() );
	}
}

