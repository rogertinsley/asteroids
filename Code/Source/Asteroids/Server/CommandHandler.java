package Asteroids.Server;

/**
 *  This interface is for the CommandHandler
 *  @author Roger Tinsley.
 */ 
	
public interface CommandHandler {
	
	/**
	 * processCommand
	 * @param commandId command to process.
	 * @return void.
	 */
	public void processCommand (int commandId);
	
	/**
	 * close
	 * @return void.
	 */
	public void close();
	
}
