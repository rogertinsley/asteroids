package Asteroids.Server;

import Asteroids.Server.Commands;
import Asteroids.Client.*;
import java.io.*;
import java.net.*;

/**
 *  This class processes the server commands on the client side
 *  @see Asteroids.Server.CommandHandler
 *  @author Roger Tinsley.
 */   
	
public class ClientCommandHandler implements CommandHandler {

	private User user;

	
	/**
	 * ClientCommandHandler constructor.
	 * @param aUser the user assigned to the ClientCommandHandler.
	 */
	public ClientCommandHandler(User aUser) {
		user = aUser;
	}
	
	/**
	 * determines which command to process
	 * @param commandId the id of th command.
	 * @return void.
	 */
	public synchronized void processCommand(int commandId) {
		switch(commandId) {
		
		case Commands.MOVE:
			processMoveCommand();
			break;
			
		case Commands.TURN:
			processTurnCommand();
			break;
		
		case Commands.ADDSHIP:
			processAddShipCommand();
			break;
		
		case Commands.UPDATE_ASTEROID:
			processMoveAsteroidCommand();
			break;
			
		case Commands.UPDATE_LASER:
			processLaserCommand();
			break;
			
		case Commands.CREATE_LASER:
			processCreateLaserCommand();
			break;
			
		case Commands.DELETE_LASER:
			processDeleteLaserCommand();
			break;
			
		case Commands.SPLIT_ASTEROID:
			processSplitAsteroidCommand();
			break;
			
		case Commands.CREATE_ASTEROIDS:
			processCreateAsteroidCommand();
			break;
			
		case Commands.UPDATE_SMALL_ASTEROID:
			processUpdateSmallAsteroidCommand();
			break;
			
		case Commands.REMOVE_SMALL_ASTEROID:
			processRemoveSmallAsteroidsCommand();
			break;
			
		case Commands.REFRESH:
			processRefreshCommand();
			break;
			
		case Commands.ADD_ASTEROID:
			processAddAsteroidCommand();
			break;
			
		case Commands.ADD_SMALL_ASTEROID:
			processAddSmallAsteroidCommand();
			break;
			
		case Commands.REM0VE_SHIP:
			processRemoveShipCommand();
			break;
			
		case Commands.GAME_OVER:
			processGameOverCommand();
			break;
			
		case Commands.MAKE_SHIP_INVINVIBLE:
			processMakeShipInvincibleCommand();
			break;
			
		case Commands.MAKE_NOT_INVINCIBLE:
			processMakeShipNotInvincibleCommand();
			break;
			
		case Commands.UPDATE_SCORE:
			processUpdateScoreCommand();
			break;
			
		case Commands.LOSE_LIVE:
			processLoseLiveCommand();
			break;
			
		case Commands.SUSPEND_SHIP:
			processSuspendShipCommand();
			break;
			
		case Commands.TOP_SCORE:
			processTopScoreCommand();
			break;
			
		case Commands.NEW_GAME:
			processNewGameCommand();
			break;
			
		case Commands.WAKE_SHIP:
			processWakeShipCommand();
			break;
			
		case Commands.LIVE:
			processLiveCommand();
			break;
			
		case Commands.WINNER:
			processWinnerCommand();
			break;
		
		default:
			System.out.println("Server Command not found: "+commandId);
			break;
		}
	}
	
	/**
	 * closes the connection
	 * @return void.
	 */	
	public void close() {
		user.connection.setTerminated(true);
		System.out.println("Server Shutdown.");
		System.out.println("Please close applet window.");
	}
	
	/**
	 * forces the client to do a refresh
	 * @return void.
	 */
	private void processRefreshCommand() {
		Client.refresh();
	}
	
	/**
	 * processes a turn command
	 * @return void.
	 */
	private synchronized void processTurnCommand() {
		DataInputStream in = user.connection.getInputStream();
		int uid=0,angle=0;
		try {
			uid = in.readInt();
			angle = in.readInt();
		}
		catch(IOException e) {System.err.println("error reading in turn command: "+e);}
		ClientShipManager.turnShip(uid, angle);
	}
	
	/**
	 * processes a move command
	 * @return void.
	 */
	private synchronized void processMoveCommand() {
		DataInputStream in = user.connection.getInputStream();
		int uid=0,x=0; int y=0;
		try {
			uid = in.readInt();
			x = in.readInt();
			y = in.readInt();
		}
		catch(IOException e) {System.err.println("error reading in move command: "+e);}
		ClientShipManager.moveShip(uid, x, y);
	}
	
	/**
	 * processes an add ship command
	 * @return void.
	 */
	private synchronized void processAddShipCommand() {
		DataInputStream in = user.connection.getInputStream();
		int uid = -1;
		try {
			uid = in.readInt();
		} catch (IOException e) {System.err.println("error reading in add ship command: "+e);}
		ClientShipManager.addShip(uid);
	}
	
	/**
	 * processes a move asteroid command
	 * @return void.
	 */
	private synchronized void processMoveAsteroidCommand() {
		DataInputStream in = user.connection.getInputStream();
		int asteroidNumber=0; int x=0; int y=0;
		try {
			asteroidNumber = in.readInt();
			x = in.readInt();
			y = in.readInt();
		}
		catch(IOException e) {System.err.println("error reading in move asteroid command: "+e);}
		ClientAsteroidManager.moveAsteroid(asteroidNumber, x, y);		
	}
		
	/**
	 * processes a create laser command
	 * @return void.
	 */
	private synchronized void processCreateLaserCommand() {
		DataInputStream in = user.connection.getInputStream();
		int x=0; 
		int y=0; int angle=0;
		int laserNumber=0;
		int uid=0;
		try {
			x = in.readInt();
			y = in.readInt();
			angle = in.readInt();
			laserNumber = in.readInt();
			uid = in.readInt();
		}
		catch(IOException e) {System.err.println("error reading in create laser command: "+e);}
		ClientLaserManager.createLaser(x, y, angle, laserNumber, uid);
	}
	
	/**
	 * processes a delete laser command
	 * @return void.
	 */
	private synchronized void processDeleteLaserCommand() {
		DataInputStream in = user.connection.getInputStream();
		int laserNumber=0;
		int uid=0;
		try {
			uid = in.readInt(); 
			laserNumber = in.readInt();
		}
		catch(IOException e) {System.err.println("error reading in delete laser command: "+e);}
		ClientLaserManager.removeLaser(uid, laserNumber);
	}
	
	/**
	 * processes a process laser command
	 * @return void.
	 */
	private synchronized void processLaserCommand() {
		DataInputStream in = user.connection.getInputStream();
		int laserNumber=0; int angle=0; int x = 0; int y=0; int uid = 0;
		try {
			uid = in.readInt();
			laserNumber = in.readInt();
			x = in.readInt();
			y = in.readInt();
		}
		catch (IOException e) {System.err.println("Error reading in laser command: "+e);}
		ClientLaserManager.moveLaser(uid, laserNumber, x, y);			
	}
	
	/**
	 * processes a split asteroid command
	 * @return void.
	 */
	private synchronized void processSplitAsteroidCommand() {
		DataInputStream in = user.connection.getInputStream();
		int asteroidNumber = 0;
		try {
			asteroidNumber = in.readInt();
		}
		catch (IOException e){ System.err.println("error reading in split asteroid command: "+e);	}
		ClientAsteroidManager.splitAsteroid(asteroidNumber);
	}
	
	/**
	 * processes a create asteroid command
	 * @return void.
	 */
	private synchronized void processCreateAsteroidCommand() {
		ClientAsteroidManager.addAllAsteroids();
	}
	
	/**
	 * processes an update small asteroid command
	 * @return void.
	 */
	private synchronized void processUpdateSmallAsteroidCommand() {
		DataInputStream in = user.connection.getInputStream();
		int asteroidNumber=0; int x=0; int y=0;
		try {
			asteroidNumber = in.readInt();
			x = in.readInt();
			y = in.readInt();
		}
		catch(IOException e) {System.err.println("error updating small asteroid command: "+e);}
		ClientAsteroidManager.moveSmallAsteroid(asteroidNumber, x, y);	
	}
	
	/**
	 * processes a remove small asteroid command
	 * @return void.
	 */
	private synchronized void processRemoveSmallAsteroidsCommand() {
		DataInputStream in = user.connection.getInputStream();
		int asteroidNumber = 0;
		try {
			asteroidNumber = in.readInt();
		}
		catch (IOException e){ System.err.println("error reading in remove small asteroids command: "+e);  }
		ClientAsteroidManager.removeSmallAsteroid(asteroidNumber);
	}
	
	/**
	 * processes an add asteroid command
	 * @return void.
	 */
	private synchronized void processAddAsteroidCommand() {
		DataInputStream in = user.connection.getInputStream();
		int asteroidNumber = -1;
		try {
			asteroidNumber = in.readInt();
		}
		catch (IOException e){ System.err.println("error reading in remove small asteroids command: "+e);  }
		ClientAsteroidManager.addAsteroid(asteroidNumber);
	}
	
	/**
	 * processes an add small asteroid command
	 * @return void.
	 */
	private synchronized void processAddSmallAsteroidCommand() {
		DataInputStream in = user.connection.getInputStream();
		int asteroidNumber = -1, x = -1, y = -1;
		try {
			asteroidNumber = in.readInt();
			x = in.readInt();
			y = in.readInt();
		}
		catch (IOException e){ System.err.println("error reading in remove small asteroids command: "+e);  }
		ClientAsteroidManager.addSmallAsteroid(asteroidNumber, x, y);
	}		
	
	/**
	 * processes a remove ship command
	 * @return void.
	 */
	private synchronized void processRemoveShipCommand() {
		DataInputStream in = user.connection.getInputStream();
		int uid = -1;
		try {
			uid = in.readInt();
		}
		catch (IOException e){ System.err.println("error reading in remove ship command: "+e);  }
		ClientShipManager.removeShip(uid);
	}	
	
	/**
	 * processes a game over command
	 * @return void.
	 */
	private synchronized void processGameOverCommand() {
		Client.showGameOver();	
	}
	
	/**
	 * processes a make ship invincible command
	 * @return void.
	 */
	private synchronized void processMakeShipInvincibleCommand() {
		DataInputStream in = user.connection.getInputStream();
		int uid = -1;
		try {
			uid = in.readInt();
		}
		catch (IOException e){ System.err.println("error reading in invincible command: "+e);  }
		if (ClientShipManager.shipArray[uid] != null ) {
			ClientShipManager.shipArray[uid].setInvincible(true);
		}
	}
	
	/**
	 * processes a make ship not invincible command
	 * @return void.
	 */
	private synchronized void processMakeShipNotInvincibleCommand() {
		DataInputStream in = user.connection.getInputStream();
		int uid = -1;
		try {
			uid = in.readInt();
		}
		catch (IOException e){ System.err.println("error reading in invincible command: "+e);  }
		if (ClientShipManager.shipArray[uid] != null) ClientShipManager.shipArray[uid].setInvincible(false);
		Client.loseLive = false;
	}
	
	/**
	 * processes an update score command
	 * @return void.
	 */
	private synchronized void processUpdateScoreCommand() {
		DataInputStream in = user.connection.getInputStream();
		int points = -1;
		try {
			points = in.readInt();
		}
		catch (IOException e){ System.err.println("error reading in update points command: "+e);  }
		Client.score = Client.score + points;
	}
	
	/**
	 * processes a lose live command
	 * @return void.
	 */
	private synchronized void processLoseLiveCommand() {
		Client.shipsLeft = Client.shipsLeft - 1;
		Client.loseLive = true;
		if (Client.sound) Client.crashSound.play();
	}
	
	/**
	 * processes a suspend ship command
	 * @return void.
	 */
	private synchronized void processSuspendShipCommand() {
		DataInputStream in = user.connection.getInputStream();
		int uid = -1;
		try {
			uid = in.readInt();
		}
		catch (IOException e){ System.err.println("error reading in invincible command: "+e);  }
		ClientShipManager.suspend(uid);
	}
	
	/**
	 * processes a top score command
	 * @return void.
	 */
	private synchronized void processTopScoreCommand() {
		DataInputStream in = user.connection.getInputStream();
		int uid = -1, topScore = -1;
		try {
			uid = in.readInt();
			topScore = in.readInt();
		}
		catch (IOException e){ System.err.println("error reading in top score command: "+e);  }
		Client.topScoreId = uid;
		Client.topScore = topScore;
	}
	
	/**
	 * processes a new game command
	 * @return void.
	 */
	private synchronized void processNewGameCommand() {
		Client.gameOver = false;
		for (int i=0; i < ClientShipManager.shipArray.length; i++) {
			if (ClientShipManager.shipArray[i] != null) {
				System.out.println("trying to wake: "+i);
				ClientShipManager.wake(i);
			}
		}
	}
	
	/**
	 * processes a wake ship command
	 * @return void.
	 */
	private synchronized void processWakeShipCommand() {
		DataInputStream in = user.connection.getInputStream();
		int uid = -1;
		try {
			uid = in.readInt();
		}
		catch (IOException e){ System.err.println("error reading in wake ship command: "+e);  }
		System.out.println("waking ship: "+uid);
		ClientShipManager.wake(uid);
	}
	
	/**
	 * processes a Live command
	 * @return void.
	 */
	private synchronized void processLiveCommand() {
		DataInputStream in = user.connection.getInputStream();
		int numberOfLives = -1;
		try {
			numberOfLives = in.readInt();
		}
		catch (IOException e){ System.err.println("error reading in number of lives command: "+e);}
		Client.shipsLeft=numberOfLives;
	}
	
	/**
	 * processes a winner command
	 * @return void.
	 */
	private synchronized void processWinnerCommand() {
		DataInputStream in = user.connection.getInputStream();
		int winner = -1;
		try {
			winner = in.readInt();
		}
		catch (IOException e){ System.err.println("error reading in winner command: "+e);}
		Client.winner = winner;
		Client.displayWinner = true;
	}	
		
}
