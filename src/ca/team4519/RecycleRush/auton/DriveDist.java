package ca.team4519.RecycleRush.auton;

import edu.wpi.first.wpilibj.command.Command;

import ca.team4519.RecycleRush.MechaRobot;

/**
 *drives a certian distance
 */
public class DriveDist extends Command {

    public DriveDist() {
        super("DriveDist");
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	MechaRobot.driveBase.resetAll();
    	MechaRobot.driveBase.configurePid(10, 2);
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	MechaRobot.driveBase.pidGo();
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(MechaRobot.driveBase.pidArrived()) {
    		return true;
    	}else{
        return false;
    	}
    }

    // Called once after isFinished returns true
    protected void end() {
    	MechaRobot.driveBase.pidStop();
    	MechaRobot.driveBase.resetAll();
    	System.out.println("DriveDist Auton program has ended!");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
