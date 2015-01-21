package ca.team4519.lib.pid;

import ca.team4519.lib.RioAcceleromiter;

public class YAxisAccelerometerPIDSource implements PIDSource{

	private final RioAcceleromiter accelerometer;
	
	public YAxisAccelerometerPIDSource (final RioAcceleromiter accelerometer) {
		this.accelerometer = accelerometer;
	}
	
	public float getValue() {
		return (float)accelerometer.getY();
	}
}
