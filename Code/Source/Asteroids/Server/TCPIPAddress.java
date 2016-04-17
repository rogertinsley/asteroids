package Asteroids.Server;

import java.net.*;
import java.util.*;

/**
 *  This class holds the tcp ip address.
 *  @author Roger Tinsley.
 */ 

public class TCPIPAddress {
	private String ip;
	private int port;
	
	/**
	 * Constructor for the TCPIPAddress
	 * @param address the ip address in a string format
	 */
	public TCPIPAddress(String address) {
		StringTokenizer st = new StringTokenizer(address, " ");
		try {
			ip = st.nextToken();
			port = Integer.parseInt(st.nextToken());
		}
		catch(NumberFormatException e) {}
	}
	
	/**
	 * returns the ip
	 * @return string
	 */
	public String getIP() {
		return ip;
	}
	
	/**
	 * returns the port
	 * @return integer - the port number
	 */
	public int getPort() {
		return port;
	}
}

