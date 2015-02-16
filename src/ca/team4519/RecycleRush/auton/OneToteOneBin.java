package ca.team4519.RecycleRush.auton;

import ca.team4519.RecycleRush.MechaRobot;
import ca.team4519.lib.MechaCounter;
import ca.team4519.lib.pid.MechaPID;

import edu.wpi.first.wpilibj.command.Command;

public class OneToteOneBin extends Command {

	public float rightRatio = 13757/120;
	public float leftRatio = 13140/120;
	
    public OneToteOneBin() {
    	super("OneToteOneBin");
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
