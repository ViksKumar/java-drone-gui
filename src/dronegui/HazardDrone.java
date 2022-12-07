package dronegui;
/**
 * Contains class for hazard drone
 * @author Vikram
 *
 */
@SuppressWarnings("serial")
public class HazardDrone extends Drone {

	/**
	 * 
	 */
	public HazardDrone() {
	
	}

	public HazardDrone(double ix, double iy, double ir) {
		super(ix, iy, ir);
		col = 'o';
	}

	@Override
	protected void checkDrone(DroneArena d) {
		// nothing to check for

	}

	@Override
	protected void adjustDrone() {
		// hazard does not move
		
	}
	protected String getStrType() {
		return "Hazard Drone";
	}	

}