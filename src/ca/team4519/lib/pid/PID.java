package ca.team4519.lib.pid;

import edu.wpi.first.wpilibj.Encoder;

public class PID {

//	protected final PIDSource getSource();
	
	float deadband, kP, kI, kD, integralError, deltaError, previousError;
	
	protected boolean onTarget;
	
	private Encoder source;
	
	/*public PID(final PIDSource source, final float deadband, final float kP, final float kI, final float kD){
		this.source = source;
		this.deadband = deadband;
		this.kP = kP;
		this.kI = kI;
		this.kD = kD;
		this.onTarget = false;
	}
	*/
	public PID(final Encoder source, final float deadband, final float kP, final float kI, final float kD){
		this.source = source;
		this.deadband = deadband;
		this.kP = kP;
		this.kI = kI;
		this.kD = kD;
		this.onTarget = false;
	}
	public void resetPID() {
		integralError = 0.0f;
		previousError = 0.0f;
		deltaError = 0.0f;
	}
	
	public double pid(final float target) {
		float kErr;
		float pOut;
		float iOut;
		float dOut;
		double output;
		
		kErr = (float)(target - source.get());
		
		deltaError = previousError - kErr;
		previousError = kErr;
		integralError += kErr;
		
		pOut = kErr * kP;
		iOut = integralError * kI;
		dOut = deltaError * kD;
		
		if(iOut > 1.0f) iOut = 1.0f;
		
		if(Math.abs(kErr) < deadband) {
			onTarget = true;
		}else{
			onTarget = false;
		}
		output = (pOut + iOut + dOut);
		
		if(output > 1.0f) output = 1.0f;
		if(output < -1.0f) output = 1.0f;
		

		return output;	
	}

	
	public boolean onTarget() {
		return onTarget;
	}

}
