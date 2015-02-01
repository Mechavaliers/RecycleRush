package ca.team4519.RecycleRush;

import ca.team4519.RecycleRush.subsystems.DriveBase;
import ca.team4519.RecycleRush.subsystems.Elevator;
import ca.team4519.lib.MultiLooper;

public class MechaRobot {

	public static final DriveBase driveBase = new DriveBase();
	public static final Elevator elevator = new Elevator();
	
	public static MultiLooper subsystemUpdater100Hz = new MultiLooper(1.0 / 100.0);

		//public static void initRobot() {
	    /// Add all subsystems to a 100Hz Looper		
		//	subsystemUpdater100Hz.addLoopable(driveBase);
		//	subsystemUpdater100Hz.addLoopable(elevator);
		//}

	public static void update() {
		driveBase.update();
		elevator.update();
	}
}
