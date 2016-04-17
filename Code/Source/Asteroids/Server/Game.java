package Asteroids.Server;

/**
 *  This class is the main game, adds asteroids and creates a commandDispatcher
 *  @author Roger Tinsley.
 */ 

public class Game {
	
	CommandDispatcher commandDispatcher;
	
	/**
	 * Game constructor
	 * adds asteroids to the game and creates a new commandDispatcher
	 */
	public Game() {
		ServerAsteroidManager.addAllAsteroids();
		commandDispatcher = new CommandDispatcher();
	}

}
