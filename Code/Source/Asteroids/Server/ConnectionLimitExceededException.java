package Asteroids.Server;

/**
 *  Exception definition
 *  @see CommandHandler
 *  @author Roger Tinsley.
 */ 
	
public class ConnectionLimitExceededException extends Exception {

	/**
	 * Constructor for ConnectionLimitExceededException
	 */	
	ConnectionLimitExceededException(){
		super();
	}
	
	
	/**
	 * Constructor for ConnectionLimitExceededException
	 * @param reason why the exception occured
	 */
	ConnectionLimitExceededException(String reason) {
		super(reason);
	}
}
