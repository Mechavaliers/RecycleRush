package ca.team4519.RecycleRush.auton;

import ca.team4519.RecycleRush.MechaRobot;
import ca.team4519.lib.MechaCounter;
import ca.team4519.lib.pid.GyroMechaPid;
import ca.team4519.lib.pid.MechaPID;
import ca.team4519.lib.pid.GyroMechaPid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class OneToteOneBin extends Command {
	
	MechaCounter driveCounter = new MechaCounter("Drive Counter", 10);
	
	public float rightRatio = 13757/120;
	public float leftRatio = 13140/120;
	public boolean end = false;
	
	MechaPID left = new MechaPID(0.00020, 0.000003, 0.0000001, MechaRobot.driveBase.leftEncoder, MechaRobot.driveBase.leftDriveA, MechaRobot.driveBase.leftDriveB);
	MechaPID right = new MechaPID(-0.00020, -0.000003, -0.0000001, MechaRobot.driveBase.rightEncoder, MechaRobot.driveBase.rightDriveA, MechaRobot.driveBase.rightDriveB);
	GyroMechaPid turn = new GyroMechaPid(1/45.0, 0.1, 0.1, MechaRobot.driveBase.gyro, MechaRobot.driveBase.rightDriveA, MechaRobot.driveBase.leftDriveA);
	
    public OneToteOneBin() {
    	super("OneToteOneBin");
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//MechaRobot.elevator.upperClaw.set(false);
    	//MechaRobot.elevator.lowerClaw.set(true);
    	end = false;
    	left.disable();
    	right.disable();
    	MechaRobot.driveBase.resetAll();
    	driveCounter.reset();
    	left.setAbsoluteTolerance(150);
    	right.setAbsoluteTolerance(150);
    	turn.setAbsoluteTolerance(10);
    	turn.setOutputRange(-0.35, 0.35);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	SmartDashboard.putBoolean("Are we here?", end);
    	if(driveCounter.Value() == 0){
            MechaRobot.elevator.upperClaw.set(!MechaRobot.elevator.upperClaw.get());//close
            MechaRobot.elevator.upperClawSpool.set(1.0);//up
            Timer.delay(1.56);
            MechaRobot.elevator.upperClawSpool.set(0.0);//stop
            driveCounter.increment();
    }else if(driveCounter.Value() == 1){
            left.setSetpoint(26*leftRatio); //18 inches?
            left.enable();
            right.setSetpoint(26*rightRatio);
            right.enable();
            driveCounter.increment();
    }else if(driveCounter.Value() == 2 &&(left.onTarget()||right.onTarget())){
            left.disable();
            right.disable();
           
            left.setAbsoluteTolerance(150);
        	right.setAbsoluteTolerance(150);
            MechaRobot.elevator.lowerClaw.set(!MechaRobot.elevator.lowerClaw.get());//close
            Timer.delay(0.3);
            MechaRobot.elevator.lowerClawSpool.set(-1.0);//up
            Timer.delay(0.5);
            MechaRobot.elevator.lowerClawSpool.set(0.0);//stop
            driveCounter.increment();
    }else if(driveCounter.Value() == 3){
            turn.setSetpoint(-90);
            turn.enable();
            driveCounter.increment();
    }else if(driveCounter.Value() == 4 && turn.onTarget()){
            turn.disable();
            Timer.delay(0.1);
            left.setSetpoint(73*leftRatio);
            right.setSetpoint(73*rightRatio);
            left.enable();
            right.enable();
            driveCounter.increment();
    }else if(driveCounter.Value() == 5 &&(left.onTarget()||right.onTarget())){
            left.disable();
            right.disable();
            driveCounter.increment();
            end = true;
    }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return end;
    }

    // Called once after isFinished returns true
    protected void end() {
    	SmartDashboard.putBoolean("Are we here?", end);
    	left.disable();
    	right.disable();
    	turn.disable();
    	MechaRobot.driveBase.resetAll();
    	driveCounter.reset();    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	SmartDashboard.putBoolean("Are we here?", end);
    	left.disable();
    	right.disable();
    	turn.disable();
    	MechaRobot.driveBase.resetAll();
    	driveCounter.reset();
    }
}
