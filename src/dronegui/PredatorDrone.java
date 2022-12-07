package dronegui;
/**
 * Contains class for hazard drone
 * @author Vikram
 *
 */
@SuppressWarnings("serial")
public class PredatorDrone extends Drone {

	double dAngle, dSpeed;			// angle and speed of travel
	
	public PredatorDrone() {
		
	}

	/** Create predator drone at ix, iy, with size ir at angle ia with speed is and colour col
	 * @param ix
	 * @param iy
	 * @param ir
	 * @param ia
	 * @param is
	 */
	public PredatorDrone(double ix, double iy, double ir, double ia, double is) {
		super(ix, iy, ir);
		col = 'r';
		dAngle = ia;
		dSpeed = is;
	}

	/**
	 * checkDrone - change angle of travel if hitting wall or another drone
	 * @param d   droneArena
	 */
	@Override
	protected void checkDrone(DroneArena d) {
		dAngle = d.CheckDroneAngle(x, y, rad, dAngle, droneID);
	}

	/**
	 * adjustDrone
	 * Here, move drone depending on speed and angle
	 */
	@Override
	protected void adjustDrone() {
		double radAngle = dAngle*Math.PI/180;		// put angle in radians
		x += dSpeed * Math.cos(radAngle);		// new X position
		y += dSpeed * Math.sin(radAngle);		// new Y position
	}
	/**
	 * return string defining ball type
	 */
	protected String getStrType() {
		return "Predator Drone";
	}

}
