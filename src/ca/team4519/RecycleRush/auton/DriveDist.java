package ca.team4519.RecycleRush.auton;

import edu.wpi.first.wpilibj.command.Command;
import ca.team4519.lib.pid.MechaPID;
import ca.team4519.lib.pid.PIDDrive;

import ca.team4519.RecycleRush.MechaRobot;

/**
 *drives a certian distance
 */
public class DriveDist extends Command {

	//MechaPIDController leftPid = new MechaPIDController(0.00017, 0.0000002, 0.0, MechaRobot.driveBase.leftEncoder, MechaRobot.driveBase.leftDriveA, MechaRobot.driveBase.leftDriveB);
	//MechaPIDController rightPid = new MechaPIDController(-0.00017, -0.0000002, 0.0, MechaRobot.driveBase.rightEncoder, MechaRobot.driveBase.rightDriveA, MechaRobot.driveBase.rightDriveB);
	



	PIDDrive chassis = new PIDDrive(
			new MechaPID(0.00017, 0.0000002, 0.0, MechaRobot.driveBase.leftEncoder, MechaRobot.driveBase.leftDriveA, MechaRobot.driveBase.leftDriveB), 13460, 
			new MechaPID(-0.00017, -0.0000002, 0.0, MechaRobot.driveBase.rightEncoder, MechaRobot.driveBase.rightDriveA, MechaRobot.driveBase.rightDriveB), 13740, 
			new MechaPID(0.0, 0.0, 0.0, MechaRobot.driveBase.gyro, MechaRobot.driveBase.rightDriveA, MechaRobot.driveBase.leftDriveA), 0);
    public DriveDist() {
        super("DriveDist");
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	MechaRobot.driveBase.resetAll();
    	//MechaRobot.driveBase.configurePid(10, 2);
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    //	MechaRobot.driveBase.pidGo();
    	//leftPid.enable();
    	//rightPid.enable();
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	//if(leftPid.onTarget() && rightPid.onTarget()) {
    		return true;
    	//}else{
     //return false;
    }
    
    //}

    // Called once after isFinished returns true
    protected void end() {
    //	leftPid.disable();
    //	rightPid.disable();
    	MechaRobot.driveBase.resetAll();
    	System.out.println("DriveDist Auton program has ended!");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
