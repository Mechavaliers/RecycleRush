package ca.team4519.lib.pid;

import edu.wpi.first.wpilibj.Talon;
import ca.team4519.lib.MechaPIDController;

public class PIDDrive {
	
	MechaPIDController leftPid, rightPid, gyroPid;
	Talon leftDrive1, leftDrive2, rightDrive1, rightDrive2 ;
	float targetLeft, targetRight, targetAngle;

	public PIDDrive(MechaPIDController leftPid, float targetLeft, MechaPIDController rightPid, float targetRight, MechaPIDController gyroPid, float targetAngle){
		this.leftPid = leftPid;
		this.rightPid = rightPid;
		this.targetLeft = targetLeft;
		this.targetRight = targetRight;
		this.targetAngle = targetAngle;
	}

	public void PIDStart() {
		leftPid.enable();
		rightPid.enable();
		
		if(leftPid.onTarget() && rightPid.onTarget()){
			gyroPid.enable();
		}else{
			chillFam();
		}
	}
	
	public void setTolerance(double tuleranceTicks, double tuleranceAngle) {
		leftPid.setAbsoluteTolerance(tuleranceTicks);
		rightPid.setAbsoluteTolerance(tuleranceTicks);
		gyroPid.setAbsoluteTolerance(tuleranceAngle);
		
	}
	
	public void chillFam() {
		System.out.println("Chill Fam");		
	}
}
