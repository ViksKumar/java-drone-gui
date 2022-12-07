package dronegui;
/**
 * Contains class for arena which contains the drones
 * @author Viks
 *
 */
import java.util.ArrayList;
import java.io.Serializable;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


@SuppressWarnings("serial")
public class DroneArena implements Serializable{	
	
	static double xSize;						// x size of arena
	static double ySize;						// y size of arena 
	Random random;
	public ArrayList<Drone> allDrones;			// arraylist containing all drones
	
	DroneArena() {
		this(500, 400);			// default size
	}
	
	/**
	 * construct arena of size xS by yS
	 * @param xS
	 * @param yS
	 */
	DroneArena(double xS, double yS){
		xSize = xS;
		ySize = yS;
		allDrones = new ArrayList<Drone>();							// initially empty list of all drones
		allDrones.add(new FriendlyDrone(xS/2, yS/2, 10, 45, 2));	// add Friendly
		allDrones.add(new HazardDrone(xS/3, yS/4, 15));				// add Hazard
		allDrones.add(new HuggerDrone(xS/4, yS/4, 10, 45, 2));		 //add Hugger
	}
	
	/**
	 * return arena x size 
	 * @return
	 */
	public static double getXSize() {
		return xSize;
	}
	
	/**
	 * return arena y size
	 * @return
	 */
	public static double getYSize() {
		return ySize;
	}
	
	public void drawArena(MyCanvas mc) {
		for (Drone d : allDrones) d.drawDrone(mc);		// draw all drones from the arraylist
	}
	
	/**
	 * check all drones to see if angle of moving drones changes
	 */
	public void checkDrones() {
		for (Drone d : allDrones) d.checkDrone(this);	// check all drones
	}
	
	/**
	 * adjust drones that need to be moved
	 */
	public void adjustDrones() {
		for (Drone d : allDrones) d.adjustDrone();
	}
	
	/**
	 * list of strings defining each drone in the arraylist
	 * @return
	 */
	public ArrayList<String> describeAll() {
		ArrayList<String> ans = new ArrayList<String>();		// set up empty arraylist
		for (Drone d : allDrones) ans.add(d.toString());			// add string defining each drone
		return ans;												// return string list
	}
	/** 
	 * Check angle of drone:
	 * if hitting wall, rebound; 
	 * if hitting drone, change angle;
	 * @param x				drone x position
	 * @param y				y
	 * @param rad			radius
	 * @param ang			current angle
	 * @param notID			identify of drone not to be checked
	 * @return				new angle 
	 */
	public double CheckDroneAngle(double x, double y, double rad, double ang, int notID) {
		double ans = ang;
		if (x < rad || x > xSize - rad) ans = 180 - ans;
			// check left or right walls, set new angle as 180-angle
		if (y < rad || y > ySize - rad) ans = - ans;
			// check top or bottom walls, set mirror angle
		for (Drone d : allDrones) 
			if (d.getID() != notID && d.hitting(x, y, rad)) ans = 180*Math.atan2(y-d.getY(), x-d.getX())/Math.PI;
				// check all drone except one with given id
				// if hitting, return angle between the other drone and this one.
		
		return ans;		// return the angle
	}
	

	/**
	 * check if the friendly drone has been hit by a predator
	 * @param the Friendly Drone
	 * Remove drone if hit
	 * @return 	true if hit
	 */
	public boolean checkFriendly(Drone FriendlyDrone) {
		boolean ans = false;
		for (Drone d : allDrones)
			if (d instanceof PredatorDrone && d.hitting(FriendlyDrone)) {
				removeFriendly(FriendlyDrone);
				ans = true;
			}
		return ans;
	}
	
	
	/**
	 * check if the Hugger drone has been hit by a predator
	 * @param the Hugger Drone
	 * remove drone if hit
	 * @return 	true if hit
	 */
	public boolean checkHugger(Drone HuggerDrone) {
		boolean ans = false;
		for (Drone d : allDrones)
			if (d instanceof PredatorDrone && d.hitting(HuggerDrone)) {
				removeHugger(HuggerDrone);
				ans = true;
		}
		return ans;
	}
	
	public void removeFriendly(Drone FriendlyDrone) { 
			allDrones.remove(FriendlyDrone);			// remove friendly drone from arraylist
			//System.out.println(FriendlyDrone.getID()); used during testing/fixing
	}
	
	public void removeHugger(Drone HuggerDrone) { 
		allDrones.remove(HuggerDrone);					// remove hugger drone from arraylist
		//System.out.println(HuggerDrone.getID()); used during testing/fixing
	}
	
	/**
	 * Search to see if there is a drone at position x,y in Arena
	 * @param x		x position	
	 * @param y		y psotion
	 * @return		null if no drone, otherwise return drone
	 */
	public Drone getDroneAt(double x, double y) {
		
		for (int i = 0; i < allDrones.size(); i++) {
			if (allDrones.get(i).isHere(x, y))
				return allDrones.get(i);
		}
		return null;
	}

	/**
	 * Add Friendly drone with random X and Y position into the Arena
	 * Capacity of the Arena is set
	 * New drone is added if there is capacity otherwise drone is not added
	 * drone is added to free space
	 */
	public void addFriendlyDrone() {
		
		double randomX;
		double randomY;
		double capacity = xSize * ySize; 
		
		if (allDrones.size() < capacity) {
			do {
				randomX = ThreadLocalRandom.current().nextDouble(0, xSize);
				randomY = ThreadLocalRandom.current().nextDouble(0, ySize);
			} while (getDroneAt(randomX, randomY) != null);
			
			allDrones.add(new FriendlyDrone(randomX, randomY, 10, 60, 2));
		}
		else {
			System.out.println("Could not add drone");
		}
	}
	
	/**
	 * Add Hugger drone with random X and Y position into the Arena
	 * Capacity of the Arena is set
	 * New drone is added if there is capacity otherwise drone is not added
	 * drone is added to free space
	 */
	public void addHuggerDrone() {
		double randomX;
		double randomY;
		double capacity = xSize * ySize; 
		
		if (allDrones.size() < capacity) {
			do {
				randomX = ThreadLocalRandom.current().nextDouble(0, xSize);
				randomY = ThreadLocalRandom.current().nextDouble(0, ySize);
			} while (getDroneAt(randomX, randomY) != null);
			
			allDrones.add(new HuggerDrone(randomX, randomY, 10, 60, 1));
		}
		else {
			System.out.println("Could not add drone");
		}
	}
	
	/**
	 * Add Predator drone with random X and Y position into the Arena
	 * Capacity of the Arena is set
	 * New drone is added if there is capacity otherwise drone is not added
	 * drone is added to free space
	 */
	public void addPredator() {
		double randomX;
		double randomY;
		double capacity = xSize * ySize; 
		
		if (allDrones.size() < capacity) {
			do {
				randomX = ThreadLocalRandom.current().nextDouble(0, xSize);
				randomY = ThreadLocalRandom.current().nextDouble(0, ySize);
			} while (getDroneAt(randomX, randomY) != null);
			
			allDrones.add(new PredatorDrone(randomX, randomY, 10, 60, 1.5));
		}
		else {
			System.out.println("Could not add drone");
		}
	}
	
	// moves hazard drone to where mouse was clicked
	public void addHazard(double x, double y) {
		for (Drone d : allDrones)
			if (d instanceof HazardDrone) d.setXY(x, y);
	}
}