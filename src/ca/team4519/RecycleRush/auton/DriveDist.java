package ca.team4519.RecycleRush.auton;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.PIDController;

import ca.team4519.RecycleRush.MechaRobot;

/**
 *drives a certian distance
 */
public class DriveDist extends Command {

	PIDController leftPid = new PIDController(0.00017, 0.0000002, 0.0, MechaRobot.driveBase.leftEncoder, MechaRobot.driveBase.leftDriveB);
	PIDController rightPid = new PIDController(-0.00017, -0.0000002, 0.0, MechaRobot.driveBase.rightEncoder, MechaRobot.driveBase.rightDriveB);
	
    public DriveDist() {
        super("DriveDist");
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	leftPid.setSetpoint(13460);
    	rightPid.setSetpoint(13740);
    	leftPid.setAbsoluteTolerance(100);
    	rightPid.setAbsoluteTolerance(100);
    	
    	MechaRobot.driveBase.resetAll();
    	//MechaRobot.driveBase.configurePid(10, 2);
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    //	MechaRobot.driveBase.pidGo();
    	leftPid.enable();
    	rightPid.enable();
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(leftPid.onTarget() && rightPid.onTarget()) {
    		return true;
    	}else{
     return false;
    }
    
    }

    // Called once after isFinished returns true
    protected void end() {
    	leftPid.disable();
    	rightPid.disable();
    	MechaRobot.driveBase.resetAll();
    	System.out.println("DriveDist Auton program has ended!");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
