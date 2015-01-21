package ca.team4519.lib.pid;

import ca.team4519.lib.MechaGyro;

public class GyroPIDSource implements PIDSource {
	
	private final MechaGyro gyro;

	public GyroPIDSource(final MechaGyro gyro) {
		this.gyro = gyro;
		}
	public MechaGyro getSensor() {
		return gyro;
	}
	
	public float getValue() {
		return (float)gyro.getAngle();
	}
	
	public float getMinAngle() {
		return 0.0f;
	}
	
	public float getMaxAngle() {
		return 360.0f;
	}
}
