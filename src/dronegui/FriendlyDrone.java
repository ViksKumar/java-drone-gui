package dronegui;
/**
 * Contains class for friendly drone extended from drone
 * @author Vikram
 *
 */
@SuppressWarnings("serial")
public class FriendlyDrone extends Drone {

	double dAngle, dSpeed;			// angle and speed of travel
	/**
	 * 
	 */
	public FriendlyDrone() {
	}

	/** Create friendly drone at ix, iy, with size ir at angle ia with speed is
	 * @param ix
	 * @param iy
	 * @param ir
	 * @param ia
	 * @param is
	 */
	public FriendlyDrone(double ix, double iy, double ir, double ia, double is) {
		super(ix, iy, ir);
		col = 'g';
		dAngle = ia;
		dSpeed = is;
	}

	/**
	 * checkdrone - change angle of travel if hitting wall or another drone
	 * @param d   droneArena
	 */
	@Override
	protected void checkDrone(DroneArena d) {
		dAngle = d.CheckDroneAngle(x, y, rad, dAngle, droneID);
		if(d.checkFriendly(this)) {
			System.out.println("Hit");
		}
	}

	/**
	 * adjustDrone
	 * move drone depending on speed and angle
	 */
	@Override
	protected void adjustDrone() {
		double radAngle = dAngle*Math.PI/180;		// put angle in radians
		x += dSpeed * Math.cos(radAngle);		// new X position
		y += dSpeed * Math.sin(radAngle);		// new Y position
	}
	/**
	 * return string defining drone type
	 */
	protected String getStrType() {
		return "Friendly";
	}

}