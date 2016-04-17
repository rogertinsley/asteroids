package Asteroids.Server;

/**
 *  This class maintains the top score within the game
 *  @author Roger Tinsley.
 */ 
	
public class TopScore {
	
	private static int topScore;
	private static int playerId;
	
	/**
	 * TopScore constructor
	 * @param score the top score.
	 * @param id the user who got the top score.
	 */
	public TopScore(int score, int id) {
		topScore = score;
		playerId = id;
	}
	
	
	/**
	 * returns the player who got the top score
	 * @return int 
	 */	
	public static int getPlayerId() {
		return playerId;
	}

	/**
	 * sets the player id 
	 * @param id the id of the player who got the top score.
	 * @return void.
	 */
	public static void setPlayerId(int id) {
		playerId = id;
	}
	
	/**
	 * returns the the top score
	 * @return int 
	 */	
	public static int getTopScore() {
		return topScore;
	}
	
	/**
	 * sets the top score
	 * @param score the topScore.
	 * @return void.
	 */
	public static void setTopScore(int score) {
		topScore = score;
	}
	
}
