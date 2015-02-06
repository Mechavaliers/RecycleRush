package ca.team4519.lib.pid;

import ca.team4519.RecycleRush.Constants;

public class CurveMath {

	//inner = lengthOfCurve - [(0.5*distanceBetweenWheels)*2*pi*angle]/360 
	public double inner = 0;
	public double outer = 0;
	public double angle = 0;
	public double curveLength = 0;
	
	public void calculateDistances() {
		inner = curveLength - ( (0.5 * Constants.wheelBase.getDouble()) * 2 * Math.PI * angle) / 360;
		outer = curveLength + ( (0.5 * Constants.wheelBase.getDouble()) * 2 * Math.PI * angle) / 360;
	}
	
	public double innerDistance() {
		return inner;
	}
	
	public double outerDistance() {
		return outer;
	}
}



