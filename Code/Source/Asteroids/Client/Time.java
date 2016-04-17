package Asteroids.Client;

/**
 *  This class implements some Time functions.
 *  @author Roger Tinsley.
 */ 

public class Time {
	static long zeroTime = 0;
	
	/**
	 * time to make the thread goto sleep
	 * @param time amount of time to make thread sleep.
	 * @return void.
	 */
	public static void delay(long time) {
		try {
			Thread.sleep(time);
		}	catch (InterruptedException e) {};
	}

	/**
	 * time to delay
	 * @param time amount of time to delay.
	 * @return void.
	 */
	public static void delaySeconds(long time)
	{
		delay(time * 1000);
	}

	
	
	
}
