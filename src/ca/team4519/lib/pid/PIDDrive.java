package ca.team4519.lib.pid;

import edu.wpi.first.wpilibj.Talon;

public class PIDDrive {
	
	PID leftPid;
	PID rightPid;
	Talon leftDrive;
	Talon rightDrive; 
	float target;

	public PIDDrive(PID leftPid, PID rightPid, Talon leftDrive, Talon rightDrive, float target){
		this.leftPid = leftPid;
		this.rightPid = rightPid;
		this.leftDrive = leftDrive;
		this.rightDrive = rightDrive;
		this.target = target;	
	}
	
	public void PIDGo() {
		leftDrive.set(leftPid.pid(target));
		rightDrive.set(rightPid.pid(target));
	}
	
}
