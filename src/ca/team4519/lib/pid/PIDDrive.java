package ca.team4519.lib.pid;

import edu.wpi.first.wpilibj.Talon;

public class PIDDrive {
	
	MechaPID leftPid, rightPid, gyroPid;
	Talon leftDrive1, leftDrive2, rightDrive1, rightDrive2 ;
	float targetLeft, targetRight, targetAngle;

	public PIDDrive(MechaPID leftPid, float targetLeft, MechaPID rightPid, float targetRight, MechaPID gyroPid, float targetAngle){
		this.leftPid = leftPid;
		this.rightPid = rightPid;
		this.targetLeft = targetLeft;
		this.targetRight = targetRight;
		this.targetAngle = targetAngle;
	}

	
	public boolean turnFirst(boolean whatAmIDoingWithMyLife) {
		return whatAmIDoingWithMyLife;
	}
	
	public void PIDStart() {
		if(turnFirst(true)){
			gyroPid.enable();
			
			if(gyroPid.onTarget()){
				gyroPid.disable();
				leftPid.enable();
				rightPid.enable();	
			}
			
		}else{
		leftPid.enable();
		rightPid.enable();
		
		if(leftPid.onTarget() && rightPid.onTarget()){
			leftPid.disable();
			rightPid.disable();
			gyroPid.enable();
		}else{
			chillFam();
		}
		}
	}

	public void PIDKill() {
		leftPid.reset();
		rightPid.reset();
		gyroPid.reset();
	}
	
	public void setTolerance(double tuleranceTicks, double tuleranceAngle) {
		leftPid.setAbsoluteTolerance(tuleranceTicks);
		rightPid.setAbsoluteTolerance(tuleranceTicks);
		gyroPid.setAbsoluteTolerance(tuleranceAngle);
		
	}
	
	public void changeTargets(double left, double right, double angle) {
		leftPid.setSetpoint(left);
		rightPid.setSetpoint(right);
		gyroPid.setSetpoint(angle);
	}
	
	public void chillFam() {
		System.out.println("Chill Fam");		
	}
}
