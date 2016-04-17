package Asteroids.Server;

import Asteroids.Client.*;

/**
 *  This class is the main processing unit on the server side
 *  It works out collisions, positions, points, who won the game, etc.
 *  @see java.lang.Thread
 *  @author Roger Tinsley.
 */ 

public class CommandDispatcher extends Thread {

	private static boolean running = false;
	private static boolean newGame = false;
	
	/**
	 * CommandDispatcher constructor
	 */
	public CommandDispatcher() {
		TopScore topScore = new TopScore(0, -1);
		running = true;
		this.start();
	}	
	/**
	 * send a remove ship command
	 * @return void.
	 */
	public static synchronized void sendRemoveShipCommand(int uid) {
		UserManager.writeToAllUsers(Commands.REM0VE_SHIP);
		UserManager.writeToAllUsers(uid);
	}

	/**
	 * Create a client laser
	 * @param p the position where the laser will be created
	 * @param laserNumber unique laser number
	 * @param uid unique user id.
	 */
	public static synchronized void createClientLaser(Position p, int laserNumber, int uid) {
		if (ServerLaserManager.laserArray[uid][laserNumber] != null) {
			UserManager.writeToAllUsers(Commands.CREATE_LASER);
			UserManager.writeToAllUsers(p.getX());
			UserManager.writeToAllUsers(p.getY());
			UserManager.writeToAllUsers(p.getAngle());
			UserManager.writeToAllUsers(laserNumber);
			UserManager.writeToAllUsers(uid);
		}
	}
	/**
	 * send a remove laser command
	 * @param uid the unique user id
	 * @param laserNumber the unique laserNumber
	 * @return void.
	 */	
	public static synchronized void sendRemoveLaserCommand(int uid, int laserNumber) {
		UserManager.writeToAllUsers(Commands.DELETE_LASER);
		UserManager.writeToAllUsers(uid);
		UserManager.writeToAllUsers(laserNumber);
	}
	
	/**
	 * send an init command
	 * Send ship and asteroid data
	 * @return void.
	 */
	public static synchronized void sendInitCommand() {
		for (int i=0; i < ServerShipManager.shipArray.length; i++) {
			if (ServerShipManager.shipArray[i] != null) {
				sendAddShipCommand(i);
				if (!ServerShipManager.shipArray[i].isActive())
					sendSuspendShipCommand(i);
			}
		}
		
		for (int i=0; i < ServerAsteroidManager.asteroidArray.length; i++) {
			if (ServerAsteroidManager.asteroidArray[i] != null) {
				sendAddAsteroidCommand(i);
				sendAsteroidData(i);
			}
		}
		
		for (int i=0; i < ServerAsteroidManager.smallAsteroidArray.length; i++) {
			if (ServerAsteroidManager.smallAsteroidArray[i] != null) {
				sendAddSmallAsteroidCommand(i);
				sendSmallAsteroidData(i);
			}
		}
		sendTopScoreCommand(TopScore.getPlayerId(), TopScore.getTopScore());
	}

	/**
	 * Send laser data to all clients.
	 * @param uid unique laser id.
	 * @param laserNumber unique laser number.
	 */
	private static synchronized void sendLaserData(int uid, int laserNumber) {
		if (ServerLaserManager.laserArray[uid][laserNumber] != null) {
			Position p = ServerLaserManager.laserArray[uid][laserNumber].getPosition();
			UserManager.writeToAllUsers(Commands.UPDATE_LASER);
			UserManager.writeToAllUsers(uid); 
			UserManager.writeToAllUsers(laserNumber);
			UserManager.writeToAllUsers(p.getX());
			UserManager.writeToAllUsers(p.getY());
		}
	}

	/**
	 * send a refresh command
	 * @return void.
	 */
	private static synchronized void sendRefreshCommand() {
		UserManager.writeToAllUsers(Commands.REFRESH);
	}
	
	/**
	 * send a command
	 * The command can be either move or turn
	 * @param c the commandId
	 * @param uid the user id to send the command to
	 * @return void.
	 */
	private static synchronized void sendCommand(int c, int uid) {
		Position p = null;
		if (c == 1) {
			if (ServerShipManager.shipArray[uid] != null) {
				UserManager.writeToAllUsers(Commands.MOVE);
				p = ServerShipManager.shipArray[uid].getPosition();
				UserManager.writeToAllUsers(uid);
				UserManager.writeToAllUsers(p.getX());
				UserManager.writeToAllUsers(p.getY());
			}
		}
		else if (c == 2) {
			if (ServerShipManager.shipArray[uid] != null) {
				UserManager.writeToAllUsers(Commands.TURN);
				p = ServerShipManager.shipArray[uid].getPosition();
				UserManager.writeToAllUsers(uid);
				UserManager.writeToAllUsers(p.getAngle());
			}
		}
	}	
	/**
	 * send a remove ship command
	 * @param asteroidNumber the asteroid to extract the position from
	 * @return void.
	 */
	private static synchronized void sendAsteroidData(int asteroidNumber) {
		if (ServerAsteroidManager.asteroidArray[asteroidNumber] != null) {
			UserManager.writeToAllUsers(Commands.UPDATE_ASTEROID);
			UserManager.writeToAllUsers(asteroidNumber);
			Position p = ServerAsteroidManager.asteroidArray[asteroidNumber].getPosition();
			UserManager.writeToAllUsers(p.getX());
			UserManager.writeToAllUsers(p.getY());
		}
	}	
	
	/**
	 * send a remove ship command
	 * @param asteroidNumber the asteroid to split
	 * @return void.
	 */
	private static synchronized void sendSplitAsteroidCommand(int asteroidNumber) {
		UserManager.writeToAllUsers(Commands.SPLIT_ASTEROID);
		UserManager.writeToAllUsers(asteroidNumber);		
	}
	
	/**
	 * send a create asteroid command
	 * @return void.
	 */
	private static synchronized void sendCreateAsteroidsCommand() {
		UserManager.writeToAllUsers(Commands.CREATE_ASTEROIDS);
	}
	
	/**
	 * send small asteroid data
	 * @param asteroidNumber the asteroid to extract the position
	 * @return void.
	 */
	private static synchronized void sendSmallAsteroidData(int asteroidNumber) {
		if (ServerAsteroidManager.smallAsteroidArray[asteroidNumber] != null) {
			UserManager.writeToAllUsers(Commands.UPDATE_SMALL_ASTEROID);
			UserManager.writeToAllUsers(asteroidNumber);
			Position p = ServerAsteroidManager.smallAsteroidArray[asteroidNumber].getPosition();
			UserManager.writeToAllUsers(p.getX());
			UserManager.writeToAllUsers(p.getY());
		}
	}
	
	/**
	 * send add small asteroid command
	 * @param asteroidNumber the number of the asteroid to add
	 * @return void.
	 */
	private static synchronized void sendAddSmallAsteroidCommand(int asteroidNumber) {
		UserManager.writeToAllUsers(Commands.ADD_SMALL_ASTEROID);
		UserManager.writeToAllUsers(asteroidNumber);
		Position p = ServerAsteroidManager.smallAsteroidArray[asteroidNumber].getPosition();
		UserManager.writeToAllUsers(p.getX());
		UserManager.writeToAllUsers(p.getY());
	}
	
	/**
	 * send send add asteroid command
	 * @param asteroidNumber the number of the asteroid to add
	 * @return void.
	 */
	private static synchronized void sendAddAsteroidCommand(int asteroidNumber) {
		UserManager.writeToAllUsers(Commands.ADD_ASTEROID);
		UserManager.writeToAllUsers(asteroidNumber);
	}
	
	/**
	 * send add ship command
	 * @param shipNumber the number of the ship to add
	 * @return void.
	 */
	private static synchronized void sendAddShipCommand(int shipNumber) {
		UserManager.writeToAllUsers(Commands.ADDSHIP);
		UserManager.writeToAllUsers(shipNumber);
	}
	
	/**
	 * send remove small asteroid command
	 * @param asteroidId the Id of the asteroid to remove
	 * @return void.
	 */
	private static synchronized void sendRemoveSmallAsteroidCommand(int asteroidId) {
		if (ServerAsteroidManager.smallAsteroidArray[asteroidId] != null) {
			UserManager.writeToAllUsers(Commands.REMOVE_SMALL_ASTEROID);
			UserManager.writeToAllUsers(asteroidId);
		}
	}
	
	/**
	 * send game over asteroid command
	 * @param uid the user id of who won the game
	 * @return void.
	 */
	private static synchronized void sendGameOverCommand(int uid) {
		ServerShipManager.suspend(uid);
		sendSuspendShipCommand(uid);
		UserManager.write(uid, Commands.GAME_OVER);
	}
	
	/**
	 * send suspend ship command
	 * @param uid the ship to suspend
	 * @return void.
	 */
	private static synchronized void sendSuspendShipCommand(int uid) {
		UserManager.writeToAllUsers(Commands.SUSPEND_SHIP);
		UserManager.writeToAllUsers(uid);
	}
	
	/**
	 * send wake ship command
	 * @param uid the id of the ship to wake
	 * @return void.
	 */
	private static synchronized void sendWakeShipCommand(int uid) {
		UserManager.writeToAllUsers(Commands.WAKE_SHIP);
		UserManager.writeToAllUsers(uid);
	}
	
	/**
	 * send invincible ship command
	 * @param uid the id of the ship to make invincible
	 * @return void.
	 */
	private static synchronized void sendInvincibleShipCommand(int uid) {
		ServerShipManager.shipArray[uid].resetInvincibleCounter();
		UserManager.writeToAllUsers(Commands.MAKE_SHIP_INVINVIBLE);
		UserManager.writeToAllUsers(uid);
	}
	
	/**
	 * send not invincible ship command
	 * @param uid the id of the ship to make not invincible
	 * @return void.
	 */
	private static synchronized void sendNotInvincibleShipCommand(int uid) {
		UserManager.writeToAllUsers(Commands.MAKE_NOT_INVINCIBLE);
		UserManager.writeToAllUsers(uid);
	}

	/**
	 * send update points command
	 * @param uid the user whose points are to be updated
	 * @param points the amount of points to be updated
	 * @return void.
	 */
	private static synchronized void sendUpdatePointsCommand(int points, int uid) {
		UserManager.write(uid, Commands.UPDATE_SCORE);
		UserManager.write(uid, points);
	}
	
	/**
	 * send lose live command
	 * @param uid the id of the user to lose a live
	 * @return void.
	 */
	private static synchronized void sendLoseLiveCommand(int uid) {
		UserManager.write(uid,Commands.LOSE_LIVE);
		ServerShipManager.newPosition(uid);
	}
	
	/**
	 * send top score command
	 * @param uid the id of the user who has the top score
	 * @param topScore the value of the top score
	 * @return void.
	 */
	private static synchronized void sendTopScoreCommand(int uid, int topScore) {
		UserManager.writeToAllUsers(Commands.TOP_SCORE);
		UserManager.writeToAllUsers(uid);
		UserManager.writeToAllUsers(topScore);
	}
	
	/**
	 * send start new game command
	 * Checks to see who won the game
	 * @return void.
	 */
	private static synchronized void sendStartNewGameCommand() {
		int winner = -1;
		int tempScore = 0;
		for (int i=0; i < UserManager.userArray.length; i++) {
			if (UserManager.userArray[i] != null) {
				if (UserManager.userArray[i].score > tempScore) {
					winner = i;
				}
			}
		}
		UserManager.writeToAllUsers(Commands.WINNER);
		UserManager.writeToAllUsers(winner);	
	}

	/**
	 * Main thread
	 * the central processing method. works out collisions,score,positions etc.
	 * @return void.
	 */
	public void run() {
		synchronized(this) {
			while (running) {
				sendRefreshCommand();			
				// send ship data and then check if ship hits asteroid or another ship
				for (int i = 0; i < ServerShipManager.shipArray.length; i++) {
						if (ServerShipManager.shipArray[i] != null)  {
							if (ServerShipManager.shipArray[i].isActive() ) {
								if (!ServerShipManager.shipArray[i].isInvincible())
									sendNotInvincibleShipCommand(i);
								sendCommand(Commands.MOVE, i);
								sendCommand(Commands.TURN, i);
							}
						}
						
						for (int j = 0; j < ServerShipManager.shipArray.length; j++) {
							if (ServerShipManager.shipArray[i] != null) {
								if (ServerShipManager.shipArray[j] != null) {
									if (i != j) {
										if (ServerShipManager.shipArray[i].isActive()) {
											if (ServerShipManager.shipArray[j].isActive()) {
												if (ServerShipManager.shipArray[i].isColliding(ServerShipManager.shipArray[j].getPolygon())) {
													if (!ServerShipManager.shipArray[i].isInvincible() || !ServerShipManager.shipArray[j].isInvincible() ) {
														ServerShipManager.shipArray[i].resetInvincibleCounter();
														ServerShipManager.shipArray[j].resetInvincibleCounter();
														System.out.println("Ship "+i+" has collided with Ship "+j+".  Both lose a live.");
														UserManager.userArray[i].setNumberOfLives(UserManager.userArray[i].getNumberOfLives() - 1);
														UserManager.userArray[j].setNumberOfLives(UserManager.userArray[j].getNumberOfLives() - 1);
														if (UserManager.userArray[i].getNumberOfLives() == 0) {
															sendGameOverCommand(i);
														}
														if (UserManager.userArray[j].getNumberOfLives() == 0) {
															sendGameOverCommand(j);
														}
													}
												}
											}
										}
									}
								}
							}
						}
						// now checking does ship i hit asteroid j
						if (ServerShipManager.shipArray[i] != null) {
							for (int j = 0; j < ServerAsteroidManager.asteroidArray.length; j++) {
								if (ServerAsteroidManager.asteroidArray[j] != null) {
									if (ServerShipManager.shipArray[i].isColliding(ServerAsteroidManager.asteroidArray[j].getPolygon() )) {
										if (!ServerShipManager.shipArray[i].isInvincible()) {
											if (ServerShipManager.shipArray[i].isActive()) {
												ServerShipManager.shipArray[i].resetInvincibleCounter();
												System.out.println("Ship "+i+" has hit asteroid "+j+".  Player "+i+" loses a life.");
												UserManager.userArray[i].setNumberOfLives(UserManager.userArray[i].getNumberOfLives() - 1);
												sendLoseLiveCommand(i);
												if (UserManager.userArray[i].getNumberOfLives() == 0) {
														sendGameOverCommand(i);
												}
												sendInvincibleShipCommand(i);
											}
										}
									}
								}
							}
						}
						//does ship hit small asteroid?
						if (ServerShipManager.shipArray[i] != null) {
							for (int j = 0; j < ServerAsteroidManager.smallAsteroidArray.length; j++) {
								if (ServerAsteroidManager.smallAsteroidArray[j] != null) {
									if (ServerShipManager.shipArray[i].isColliding(ServerAsteroidManager.smallAsteroidArray[j].getPolygon() )) {
										if (!ServerShipManager.shipArray[i].isInvincible()) {
											if (ServerShipManager.shipArray[i].isActive()) {
												ServerShipManager.shipArray[i].resetInvincibleCounter();
												System.out.println("Ship "+i+" has hit asteroid "+j+".  Player "+i+" loses a life.");
												UserManager.userArray[i].setNumberOfLives(UserManager.userArray[i].getNumberOfLives() - 1);
												sendLoseLiveCommand(i);
												if (UserManager.userArray[i].getNumberOfLives() == 0) {
														sendGameOverCommand(i);
												}
												sendInvincibleShipCommand(i);
											}
										}
									}
								}
							}
						}
				}
				//send asteroid data then check if laser hit asteroid & small asteroid.
				for (int i = 0; i < ServerAsteroidManager.asteroidArray.length; i++) {
					sendRefreshCommand();
					if (ServerAsteroidManager.asteroidArray != null) {
						sendAsteroidData(i);
						for (int k=0; k < ServerLaserManager.laserArray.length; k++) {
							for (int j = 0; j < ServerLaserManager.laserArray[k].length; j++) {
								if (ServerLaserManager.laserArray[k][j] != null) {
									if (ServerAsteroidManager.asteroidArray[i] != null) {
		          						if (ServerAsteroidManager.asteroidArray[i].isColliding(ServerLaserManager.laserArray[k][j].getPolygon() )) {
		            						ServerAsteroidManager.asteroidArray[i].setActive(false);
											ServerLaserManager.laserArray[k][j].setInitialised(false);
											ServerLaserManager.removeLaser(k, j);
											if (!ServerAsteroidManager.asteroidArray[i].isSmall()) {
												System.out.println("Asteroid "+i+" has been split into two.");
												ServerAsteroidManager.splitAsteroid(i);
												sendSplitAsteroidCommand(i);
												UserManager.userArray[k].updateScore(Constants.POINTS_BIG_ASTEROID);
												sendUpdatePointsCommand(Constants.POINTS_BIG_ASTEROID, k);
											}
										}
									}
								}
							}
						}
					}
				}				
				for (int i = 0; i < ServerAsteroidManager.smallAsteroidArray.length; i++) {
					if (ServerAsteroidManager.smallAsteroidArray[i] != null) {
						sendSmallAsteroidData(i);
						for (int k=0; k < ServerLaserManager.laserArray.length; k++) {
							for (int j = 0; j < ServerLaserManager.laserArray[k].length; j++) {
								if (ServerLaserManager.laserArray[k][j] != null) {
									if (ServerAsteroidManager.smallAsteroidArray[i] != null) {
		          						if (ServerAsteroidManager.smallAsteroidArray[i].isColliding(ServerLaserManager.laserArray[k][j].getPolygon() )) {
		            						ServerAsteroidManager.smallAsteroidArray[i].setActive(false);
											ServerLaserManager.laserArray[k][j].setInitialised(false);
											ServerLaserManager.removeLaser(k, j);
											sendRemoveSmallAsteroidCommand(i);
											ServerAsteroidManager.removeSmallAsteroid(i);
											System.out.println("Asteroid "+i+" has been removed.");
											UserManager.userArray[k].updateScore(Constants.POINTS_SMALL_ASTEROID);
											sendUpdatePointsCommand(Constants.POINTS_SMALL_ASTEROID, k);
										}
									}
								}
							}
						}
					}
				}
				sendRefreshCommand();
				for (int j =0; j < ServerLaserManager.laserArray.length; j++) {
					for (int k=0; k < ServerLaserManager.laserArray[j].length; k++) {
						if (ServerLaserManager.laserArray[j][k] != null) {
							if (ServerLaserManager.laserArray[j][k].isInitialised() ) 
								sendLaserData(j, k); 
							else ServerLaserManager.removeLaser(j,k);
							//does laser hit a ship?
							for (int i=0; i < ServerShipManager.shipArray.length; i++) {
								if (i != j) { //cant shoot yourself
									if (ServerLaserManager.laserArray[j][k] != null) {
										if (ServerShipManager.shipArray[i] != null) {
											if (ServerLaserManager.laserArray[j][k].isColliding(ServerShipManager.shipArray[i].getPolygon() )) {
												ServerShipManager.shipArray[i].resetInvincibleCounter();
												System.out.println("Ship "+i+" has been shot by ship "+j);
												UserManager.userArray[i].updateScore(Constants.POINTS_SHIP);
												UserManager.userArray[i].setNumberOfLives(UserManager.userArray[i].getNumberOfLives() - 1);
												sendLoseLiveCommand(i);
												if (UserManager.userArray[i].getNumberOfLives() == 0) {
														sendGameOverCommand(i);
												}
												sendInvincibleShipCommand(i);
											}
										}
									}
								}
							}
						}
					}
				}
				if (ServerAsteroidManager.isEmpty(ServerAsteroidManager.asteroidArray)  ) {
					if (ServerAsteroidManager.isEmpty(ServerAsteroidManager.smallAsteroidArray)  ) {					
						sendCreateAsteroidsCommand();
						ServerAsteroidManager.addAllAsteroids();
						System.out.println("new game...");
						System.out.println("Creating new batch of asteroids...");
					}
				}
				sendRefreshCommand();	

				for (int i=0; i<ServerShipManager.shipArray.length; i++) {
					if (ServerShipManager.shipArray[i] != null) {
						if (ServerShipManager.shipArray[i].isActive()) {
							newGame = false;
							break;
						}			
						else newGame = true;
					}
				}
				if (newGame) {
					sendStartNewGameCommand();
					newGame = false;
				}

				// check to see who has the top score.
				int dummyScore = 0;
				int dummyPlayerId = 0;
				for (int i = 0; i < UserManager.userArray.length; i++) {
					if (UserManager.userArray[i] != null) {
						if (UserManager.userArray[i].score > dummyScore) {
							dummyScore = UserManager.userArray[i].score;
							dummyPlayerId = i;
						}
					}
				}					
				TopScore.setPlayerId(dummyPlayerId);
				TopScore.setTopScore(dummyScore);
				sendTopScoreCommand(TopScore.getPlayerId(), TopScore.getTopScore());
				try {
					wait(50);
				} catch (InterruptedException e) {break;}
			}// end synch
		}
	}
			
}
