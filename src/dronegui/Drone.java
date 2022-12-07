package dronegui;
/**
 * Contains class for drone
 * @author Vikram
 *
 */
import java.io.Serializable;

@SuppressWarnings("serial")

public abstract class Drone implements Serializable {
	protected double x, y, rad;						// position and size of drone
	protected char col;								// used to set colour
	static int droneCounter = 0;					// used to give each drone a unique identifier
	protected int droneID;							// unique ID for drone

	Drone() {
		this(100, 100, 10);
	}
	
	/**
	 * construct a drone of radius ir at ix,iy
	 * @param ix
	 * @param iy
	 * @param ir
	 */
	
	Drone (double ix, double iy, double ir) {
		x = ix;
		y = iy;
		rad = ir;
		droneID = droneCounter++;			// set the identifier and increment class static
		col = 'r';
	}
	
	/**
	 * return x position
	 * @return
	 */
	public double getX() { 
		return x; 
	}
	
	/**
	 * return y position
	 * @return
	 */
	public double getY() { 
		return y; 
	}
	/**
	 * return radius of drone
	 * @return
	 */
	
	public double getRad() { 
		return rad; 
	}
	
	/** 
	 * set the drone at position nx, ny
	 * @param nx
	 * @param ny
	 */
	public void setXY(double nx, double ny) {
		x = nx;
		y = ny;
	}
	
	/**
	 * return the identity of drone
	 * @return
	 */
	public int getID() {
		return droneID; 
	}
	
	/**
	 * @param sx	x position to be checked 
	 * @param sy	y position to be checked
	 * @return		true if there is a drone at sx,sy otherwise false
	 */
	public boolean isHere (double sx, double sy) {
		if (sx == this.x && sy == this.y)
			return true;
		else
			return false;
	}
	
	public void drawDrone(MyCanvas mc) {
		mc.showCircle(x, y, rad, col);		// draws drone onto interface
	}
	protected String getStrType() {
		return "Drone";						//returns string type
	}
	
	/** 
	 * return string describing the drone
	 */
	public String toString() {
		return getStrType() + " " + getID()+" at position "+Math.round(x)+", "+Math.round(y);
	}
	
	/**
	 * abstract method for checking a drone in arena b
	 * @param b
	 */
	protected abstract void checkDrone(DroneArena b);
	
	/**
	 * abstract method for adjusting a drone if it moves
	 */
	protected abstract void adjustDrone();
	
	/**
	 * is the drone at ox,oy hitting this drone
	 * @param ox
	 * @param oy
	 * @param or
	 * @return true if hitting
	 */
	public boolean hitting(double ox, double oy, double or) {
		return (ox-x)*(ox-x) + (oy-y)*(oy-y) < (or+rad)*(or+rad);
	}		// hitting if distance between drone coords is less than the size of the radii
	
	/** is drone hitting the other drone
	 * 
	 * @param oDrone - the other drone
	 * @return true if hitting
	 */
	public boolean hitting (Drone oDrone) {
		return hitting(oDrone.getX(), oDrone.getY(), oDrone.getRad());
	}
}