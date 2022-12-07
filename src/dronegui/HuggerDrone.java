package dronegui;

@SuppressWarnings("serial")
public class HuggerDrone extends Drone {

	double dAngle, dSpeed;			// angle and speed of travel
	
	public HuggerDrone() {
	}

	public HuggerDrone(double ix, double iy, double ir, double ia, double is) {
		super(ix, iy, ir);
		col = 'b';
		dAngle = 90*Math.floor(dAngle/90);
		dSpeed = is;
	}

	@Override
	protected void checkDrone(DroneArena d) {
		if(d.checkHugger(this)) {
			System.out.println("Hit"); //used during bugfixing as described in report
		}
		dAngle = d.CheckDroneAngle(x, y, rad, dAngle, droneID); 
		//force angle of drone travel to follow edge of Arena. 
		if (dAngle < 10 && x > DroneArena.getXSize() - rad*2) dAngle = 90;
			else if (dAngle > 80 & dAngle < 100 && y > DroneArena.getYSize() - rad*2) dAngle = 180;
			else if (dAngle > 170 && dAngle < 190 && x < rad*2) dAngle = 270;
			else if (dAngle > 260 && y < rad*2) dAngle = 0;
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
		return "Hugger Drone";
	}
}
