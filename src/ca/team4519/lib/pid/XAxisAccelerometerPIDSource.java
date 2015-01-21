package ca.team4519.lib.pid;

import ca.team4519.lib.RioAcceleromiter;

public class XAxisAccelerometerPIDSource implements PIDSource{

	private final RioAcceleromiter accelerometer;
	
	public XAxisAccelerometerPIDSource (final RioAcceleromiter accelerometer) {
		this.accelerometer = accelerometer;
	}
	
	public float getValue() {
		return (float)accelerometer.getX();
	}
}
