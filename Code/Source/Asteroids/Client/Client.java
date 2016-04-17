package Asteroids.Client;

import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.io.*;
import Asteroids.Server.*;

/**
 *  This class is the main Client applicatiom
 *  @see javax.swing.JApplet
 *  @author Roger Tinsley.
 */ 

public class Client extends JApplet implements Runnable {

	// Static variables
	private static int clientHeight;
	private static int clientWidth;
	private static Dimension playingArea;
	private static ClientCommandProcessor ccp;
	private static User myUser;
	private static JApplet japplet;
	public static AudioClip crashSound;
	public static AudioClip explosionSound;
  	public static AudioClip fireSound;
	public static AudioClip thrustersSound;
	public static boolean gameOver = false;
	public static int score;
	public static int shipsLeft;
	public static int topScore=0;
	public static int topScoreId=-1;
	public static boolean loseLive = false;
	public static boolean sound = true;
	public static int winner;
	public static boolean displayWinner = false;
	
	private int liveCounter = 0;
	private boolean connected = false;
	private InetAddress inetAddress = null;
	private Socket socket = null;
	private int id;
	private Dimension dimension;
    private Image image;
    private Graphics graphics;	
	private Thread c;	
	private int numStars;
    private Point[] stars;
	private boolean loaded = false;
	private Font font;
  	private FontMetrics fm;
  	private int fontWidth;
    private int fontHeight;
	
	
	/**
	 * Initialised method on applet
	 * sets up connection, draws background, etc.
	 * @return void.
	 */
	public void init() {
		playingArea = getSize();
		clientWidth = playingArea.width;
	 	clientHeight = playingArea.height;
		font = new Font("Helvetica", Font.BOLD, 12);
		Graphics g = getGraphics();
		g.setFont(font);
    	fm = g.getFontMetrics();
    	fontWidth = fm.getMaxAdvance();
    	fontHeight = fm.getHeight();
		score = 0;
		shipsLeft = Constants.NUMBER_OF_LIVES;
		japplet = this;
		addKeyListener(new ClientKeyListener());
		createStars();
		createConnection();
		connected = checkConnected();
		if (connected) { 
			if (!loaded) {
      			loadSounds();
      			loaded = true;
			}
			id = getId();
			if (id != -1) {
				myUser = new User(id, socket, 1);
				ccp = new ClientCommandProcessor(myUser);		
			}			
			c = new Thread(this);
			c.start();
		}
		else System.err.println("Server full.");
	}
	
	
	/**
	 * Creates the sounds subsystem
	 * @return void.
	 */
	private void loadSounds() {
	    crashSound = getAudioClip(getDocumentBase(), "crash.au");
	    explosionSound = getAudioClip(getDocumentBase(), "explosion.au");
	    fireSound = getAudioClip(getDocumentBase(), "fire.au");
		thrustersSound = getAudioClip(getDocumentBase(), "thrusters.au");
	
	    crashSound.play();     
		crashSound.stop();
		fireSound.play();      
		fireSound.stop();
	    explosionSound.play(); 
		explosionSound.stop();
		thrustersSound.play(); 
		thrustersSound.stop();
	}
	
	
	/**
	 * creates random points for the stars
	 * @return void.
	 */
	private void createStars() {
		numStars = 50;
    	stars = new Point[numStars];
    	for (int i = 0; i < numStars; i++)
    		stars[i] = new Point((int) (Math.random() * clientWidth), (int) (Math.random() * clientWidth));
	}

	/**
	 * Reads in the user ID from the server.
	 * This is needed to create a user
	 * @return void.
	 */
	private int getId() {
		int tmp = -1;
		try {
			DataInputStream in = new DataInputStream(socket.getInputStream());
			tmp = in.readInt();
		} catch(IOException e) {System.err.println("error");}
		return tmp;
	}

	/**
	 * check to see if connection was successful.
	 * This is needed to create a user
	 * @return void.
	 */
	private boolean checkConnected() {
		int tmp = -1;
		try {
			DataInputStream in = new DataInputStream(socket.getInputStream());
			tmp = in.readInt();
		} catch(IOException e) {System.err.println("error");}
		if (tmp == 0)
			return false;
		else return true;
	}
	
	/**
	 * creates the connection to the server
	 * Connects back to the server from where the applet was downloaded
	 * @return void.
	 */
	public void createConnection() {
		try {	
			TCPIPAddress address = new TCPIPAddress(getCodeBase().getHost() + " 3210");
			inetAddress = InetAddress.getByName(address.getIP());
			socket = new Socket(inetAddress, address.getPort() );
		}
		catch (Exception e) {System.err.println("Failed to connect to server:" + e); 
							 System.out.println("Close applet windows and try to reconnect");}
	}
	
	/**
	 * start applet
	 * @return void.
	 */
	public void start() {
	}
	
	/**
	 * Stop applet
	 * @return void.
	 */
	public void stop() {
		c = null;
	}

	/**
	 * calls update
	 * Calling update stops the "flickering" effect
	 * @param g graphics
	 * @return void.
	 */
	public void paint(Graphics g) {
		update(g);
	}

	/**
	 * draws the stars, ships, lasers, asteroids
	 * draws the background.
	 * @param g graphics.
	 * @return void.
	 */
	public void update(Graphics g) {
		if (connected) {
			
			Dimension d = getSize();
			
			if (graphics == null || clientWidth != dimension.width || clientHeight != dimension.height) {
				dimension = d;
	      		image = createImage(clientWidth, clientHeight);
	      		graphics = image.getGraphics();
	    	}
			
			graphics.setColor(Color.black);
	    	graphics.fillRect(0, 0, clientWidth, clientHeight);
			
			//draw stars
			graphics.setColor(Color.white);
	      	for (int i = 0; i < numStars; i++)
	        	graphics.drawLine(stars[i].x, stars[i].y, stars[i].x, stars[i].y);
				
			//draw ships
			for (int i=0; i < ClientShipManager.shipArray.length; i++) {
				if ( ClientShipManager.shipArray[i] != null) {
					if (ClientShipManager.shipArray[i].isActive() ) {
						ClientShipManager.shipArray[i].render();
						graphics.setColor(ClientShipManager.getColor(i));
		        		if ( ClientShipManager.shipArray[i].isInvincible() )
							graphics.setColor(ClientShipManager.flash());
						else 
							graphics.setColor(ClientShipManager.getColor(i));
						graphics.fillPolygon(ClientShipManager.shipArray[i].sprite);
						graphics.setColor(ClientShipManager.getColor(i));
		      			graphics.drawPolygon(ClientShipManager.shipArray[i].sprite);
		      			graphics.drawLine(ClientShipManager.shipArray[i].sprite.xpoints[ClientShipManager.shipArray[i].sprite.npoints - 1], 
								ClientShipManager.shipArray[i].sprite.ypoints[ClientShipManager.shipArray[i].sprite.npoints - 1],
		                          ClientShipManager.shipArray[i].sprite.xpoints[0], ClientShipManager.shipArray[i].sprite.ypoints[0]);
					}
				}
			}
			//draw asteroids
			for (int i=0; i < ClientAsteroidManager.asteroidArray.length; i++) {
				if (ClientAsteroidManager.asteroidArray[i] != null) {
					ClientAsteroidManager.asteroidArray[i].render();
					graphics.setColor(Color.white);
	        		graphics.fillPolygon(ClientAsteroidManager.asteroidArray[i].sprite);
					graphics.setColor(Color.white);
					graphics.drawPolygon(ClientAsteroidManager.asteroidArray[i].sprite);
					graphics.drawLine(ClientAsteroidManager.asteroidArray[i].sprite.xpoints[ClientAsteroidManager.asteroidArray[i].sprite.npoints - 1], 
							ClientAsteroidManager.asteroidArray[i].sprite.ypoints[ClientAsteroidManager.asteroidArray[i].sprite.npoints - 1],
	                          ClientAsteroidManager.asteroidArray[i].sprite.xpoints[0], ClientAsteroidManager.asteroidArray[i].sprite.ypoints[0]);
				}
			}
			
			//draw small asteroids if they exist
			for (int i=0; i < ClientAsteroidManager.smallAsteroidArray.length; i++) {
				if (ClientAsteroidManager.smallAsteroidArray[i] != null) {
					ClientAsteroidManager.smallAsteroidArray[i].render();
					graphics.setColor(Color.white);
					graphics.fillPolygon(ClientAsteroidManager.smallAsteroidArray[i].sprite);
					graphics.setColor(Color.white);
					graphics.drawPolygon(ClientAsteroidManager.smallAsteroidArray[i].sprite);
					graphics.drawLine(ClientAsteroidManager.smallAsteroidArray[i].sprite.xpoints[ClientAsteroidManager.smallAsteroidArray[i].sprite.npoints - 1], 
							ClientAsteroidManager.smallAsteroidArray[i].sprite.ypoints[ClientAsteroidManager.smallAsteroidArray[i].sprite.npoints - 1],
	                          ClientAsteroidManager.smallAsteroidArray[i].sprite.xpoints[0], ClientAsteroidManager.smallAsteroidArray[i].sprite.ypoints[0]);
				}
			}

			//draw lasers
			for (int i=0; i < ClientLaserManager.laserArray.length; i++) {
				for (int j=0; j < ClientLaserManager.laserArray[i].length; j++) {
					if (ClientLaserManager.laserArray[i][j] != null) {
						if (ClientLaserManager.laserArray[i][j].isInitialised() ) {
							ClientLaserManager.laserArray[i][j].render();
							graphics.setColor(ClientShipManager.getColor(i));
				    		graphics.fillPolygon(ClientLaserManager.laserArray[i][j].sprite);
							graphics.setColor(ClientShipManager.getColor(i));
							graphics.drawPolygon(ClientLaserManager.laserArray[i][j].sprite);
							graphics.drawPolygon(ClientLaserManager.laserArray[i][j].sprite);
						}
					}
				}
				
			if (!connected) {	
				graphics.setFont(new Font("Helvetica", Font.BOLD, 16));
    			graphics.setColor(Color.white);
				String s = "Connection Error";
       			graphics.drawString(s, (clientWidth - fm.stringWidth(s)) /2, clientHeight / 2);
			}

			if (gameOver) {	
				graphics.setFont(font);
    			graphics.setColor(Color.white);
				String s = "G A M E   O V E R";
       			graphics.drawString(s, (clientWidth - fm.stringWidth(s)) /2, clientHeight / 2);
			}
			
			if (loseLive) {
				graphics.setFont(font);
				graphics.setColor(ClientShipManager.flash());
				String s = "You lost a life!";
				graphics.drawString(s, (clientWidth - fm.stringWidth(s)) /2, clientHeight / 5);
			}
			
			if (displayWinner) {
				String s;
				graphics.setFont(font);
				graphics.setColor(Color.white);
				if (winner == -1) 
					s = "Game is a draw!";
				else
					s = "Winner of the round is player "+winner+"!";
				graphics.drawString(s, (clientWidth - fm.stringWidth(s)) /2, clientHeight / 7);
			}
			
			graphics.setFont(font);
    		graphics.setColor(Color.white);
			graphics.drawString("Player Number: " + myUser.getUserId(), 1, 20);
			graphics.drawString("Score: " + score, 1, 40);
   			graphics.drawString("Lifes: " + shipsLeft, 1, 60);
			graphics.drawString("Top Score (Player "+topScoreId+") : " + topScore, 1, 480);
			g.drawImage(image, 0, 0, this);
			}
		}
	}

	/**
	 * gets the client's height
	 * @return clients height as an integer
	 */
	public static int getClientHeight() {
		return clientHeight;
	}
	
	/**
	 * gets the client's width
	 * @return clients width as an integer
	 */
	public static int getClientWidth() {
		return clientWidth;
	}
	
	/**
	 * Forces the JApplet to repaint
	 * @return void.
	 */
	public static void refresh() {
		japplet.repaint();
	}

	/**
	 * sets the game over flag
	 * removes the ship from the game
	 * @return void.
	 */
	public static void showGameOver() {
		gameOver = true;	
		ClientShipManager.removeShip(myUser.getUserId());
	}

	/**
	 * Needed to stop the "jumpy" ships.. could be hardware related?  Not sure
	 * Command Id 1000 is ignored anyway. V.weird problem, see documentation.
	 * @return description.
	 * @see package.class
	 */
	public void run() {
		boolean running = true;
		while (true) {
			if (running) ccp.sendCommand(1000); 
			try {
				Thread.sleep(60);
			}catch (InterruptedException e) {running = false; break;}
		}
	}

	private class ClientKeyListener implements KeyListener {

		/**
		 * Reads in what key is pressed
		 * @param e keyEvent.
		 * @return void.
		 */
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {				
				case 38:
					if (!gameOver) {
						ccp.sendCommand(Commands.MOVE);
						if (sound) thrustersSound.loop();
					}
					break;
				case 39:
					if (!gameOver)
						ccp.sendCommand(Commands.TURN, Constants.TURN_RIGHT);
					break;
				case 37:
					if (!gameOver)
						ccp.sendCommand(Commands.TURN, Constants.TURN_LEFT);
					break;
				case 32:
					if (!gameOver)
						ccp.sendCommand(Commands.SHOOT_LASER);
					break;
				case 83: // "s"
      				if (sound) {
        				crashSound.stop();
        				explosionSound.stop();
        				fireSound.stop();
        				thrustersSound.stop();
						sound = false;
      				}
					else sound = true;
					break;
				default: break;
			}
	   	}
		
		/**
		 * Reads in what key is released
		 * @param e keyEvent.
		 * @return void.
		 */
	   	public void keyReleased(KeyEvent e) {
			switch(e.getKeyCode()) {				
				case 38:
					if (sound) thrustersSound.stop();
					break;
				case 40:
					if (sound) thrustersSound.stop();
					break;
				default: break;
			}
	   	}
		/**
		 * Not implemented
		 * @param e keyEvent.
		 * @return void.
		 */
	  	public void keyTyped(KeyEvent e) {
	   	}
		} //end ClientKeyListener Class
	
} // end Client Class

