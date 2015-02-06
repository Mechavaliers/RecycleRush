package ca.team4519.RecycleRush.auton;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import ca.team4519.lib.pid.MechaPID;
import ca.team4519.lib.pid.PIDDrive;
import ca.team4519.RecycleRush.MechaRobot;

/**
 *drives a certian distance
 */
public class DriveDist extends Command {
	
	public boolean canClaw = false;
	public float rightRatio = 13757/120;
	public float leftRatio = 13140/120;

	public boolean end = false;
	//PIDDrive chassis = new PIDDrive(
		MechaPID left =	new MechaPID(0.00020, 0.0000003, 0.0000001, MechaRobot.driveBase.leftEncoder, MechaRobot.driveBase.leftDriveA, MechaRobot.driveBase.leftDriveB);
		MechaPID right = new MechaPID(-0.00020, -0.0000003, -0.0000001, MechaRobot.driveBase.rightEncoder, MechaRobot.driveBase.rightDriveA, MechaRobot.driveBase.rightDriveB);
	//		new MechaPID(0.0, 0.0, 0.0, MechaRobot.driveBase.gyro, MechaRobot.driveBase.rightDriveA, MechaRobot.driveBase.leftDriveA));
	
    public DriveDist() {
        super("DriveDist");
        
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	left.reset();
    	right.reset();
    	MechaRobot.driveBase.resetAll(); 	
    	left.setSetpoint(120*leftRatio);
    	right.setSetpoint(120*rightRatio);
    	left.setAbsoluteTolerance(250);
    	right.setAbsoluteTolerance(250);
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
   // chassis.PIDStart();
    	left.enable();
    	right.enable();
 
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(left.onTarget() && right.onTarget()){
    		SmartDashboard.putBoolean("are we here", true);
    		return true;
    	}else {
    		SmartDashboard.putBoolean("are we here", false);
    		return false;
    	}
    	
    }
    
    

    // Called once after isFinished returns true
    protected void end() {
    //	chassis.Kill();
    
    	left.disable();
    	right.disable();
    	MechaRobot.driveBase.resetAll();
    	SmartDashboard.putBoolean("are we here", isFinished());
    	System.out.println("DriveDist Auton program has ended!");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }

}
