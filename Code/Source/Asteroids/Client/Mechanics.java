package Asteroids.Client;

/**
 *  This abstract class is inherited by Position and Velocity classes
 *  @see CommandHandler
 *  @author Roger Tinsley.
 */ 

public abstract class Mechanics {
	
	private int y;
   	private int x;
	private int minx;
	private int miny;
	private int maxx;
	private int maxy;
	private boolean wrap;
	
	/**
	 * Constructor for the mechanics abstract class
	 * @param aWrap wrapper.
	 * @param aX x co-ordinate.
	 * @param aY y co-ordinate.
	 * @param mindx min x value.
	 * @param maxdx max x value.
	 * @param mindy min y value.
	 * @param maxdy max y value.
	 */
	public Mechanics(boolean aWrap, int aX, int aY, int mindx, int maxdx, int mindy, int maxdy) {
		wrap = aWrap;
		x = aX;
		y = aY;
		minx = mindx;
		miny = mindy;
		maxx = maxdx;
		maxy = maxdy;		
	}

	/**
	 * converts the object to a string
	 * used for debugging, but keep it in for future use.
	 * @return string.
	 */
	public String toString() {
		return "x="+x+"   y="+y;
	}
	
	/**
	 * increase the value i, depending whether its a position or a velocity
	 * if its a velocity then wrap...
	 * @param i value to increase.
	 * @param di delta i.
	 * @param min min value of i.
	 * @param max max value of i.
	 * @return integer - the increased value of i.
	 * @see package.class
	 */
	protected int increase(int i, int di, int min, int max) {
		i = i + di;
		if (wrap) {
			if (i < min) 
	      		i = i + max - min;
	    	if (i >= max)
		      	i = i - max + min;
		}	
		else {
			if (i < min) 
	      		i = min;
	    	if (i > max)
		      	i = max;
		}
		return i;
	}
	
	/**
	 * increase the x value
	 * @param dx delta X.
	 * @return void.
	 */
	public void increaseX(int dx) {
		x = increase(x, dx, minx, maxx);
	}
	
	/**
	 * increase the y value
	 * @param dx delta y.
	 * @return void.
	 */
	public void increaseY(int dy) {
		y = increase(y, dy, miny, maxy);
	}
	
	/**
	 * gets the x value.
	 * @return integer - the x value.
	 */
	public int getX() {
		return x;
	}

	/**
	 * gets the y value.
	 * @return integer - the y value.
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the x value
	 * @param aX value to set x to.
	 * @return void.
	 */
	public void setX(int aX) {
		x = aX;
	}

	/**
	 * Sets the y value
	 * @param aY value to set y to.
	 * @return void.
	 */
	public void setY(int aY) {
		y = aY;
	}	

}

