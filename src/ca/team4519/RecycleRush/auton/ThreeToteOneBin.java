package ca.team4519.RecycleRush.auton;

import ca.team4519.RecycleRush.MechaRobot;
import ca.team4519.lib.MechaCounter;
import ca.team4519.lib.pid.*;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ThreeToteOneBin extends Command {

	MechaCounter driveCounter = new MechaCounter("Drive Counter", 10);
	
	public float rightRatio = 13757/120;
	public float leftRatio = 13140/120;
	
	public double slowSpeed = 0.25;
    public double fastSpeed = 0.38;
	
	MechaPID left = new MechaPID(0.00020, 0.0000003, 0.0000001, MechaRobot.driveBase.leftEncoder, MechaRobot.driveBase.leftDriveA, MechaRobot.driveBase.leftDriveB);
	MechaPID right = new MechaPID(-0.00020, -0.0000003, -0.0000001, MechaRobot.driveBase.rightEncoder, MechaRobot.driveBase.rightDriveA, MechaRobot.driveBase.rightDriveB);
	GyroMechaPid turn = new GyroMechaPid(1/25.0, 0.0, 0.0, MechaRobot.driveBase.gyro, MechaRobot.driveBase.leftDriveA, MechaRobot.driveBase.rightDriveA);
	
	public boolean end = false;
	
    public ThreeToteOneBin() {
    	super("ThreeToteOneBin");
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//MechaRobot.elevator.upperClaw.set(false);
    	//MechaRobot.elevator.lowerClaw.set(false);
    	driveCounter.reset();
    	left.disable();
    	right.disable();
    	end = false;
    	MechaRobot.driveBase.resetAll();
    	left.setAbsoluteTolerance(150);
    	right.setAbsoluteTolerance(150);
    	turn.setAbsoluteTolerance(2);
		left.setOutputRange(-fastSpeed, fastSpeed);
		right.setOutputRange(-fastSpeed,fastSpeed);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	SmartDashboard.putBoolean("Are we here?", false);
        if(driveCounter.Value() == 0){// pick up bin	
                MechaRobot.elevator.upperClaw.set(!MechaRobot.elevator.upperClaw.get());//close
                MechaRobot.elevator.upperClawSpool.set(1.0);//up
                Timer.delay(1.10);
                //we need to figure out in the timing below when the can gets high enough, currently in drivecounter ==3
                driveCounter.increment();
        }else if(driveCounter.Value() == 1){
        		left.setOutputRange(-fastSpeed, fastSpeed);
        		right.setOutputRange(-fastSpeed,fastSpeed);
                left.setSetpoint(200 * leftRatio);
                left.enable();
                right.setSetpoint(200 * rightRatio);
                right.enable();
                driveCounter.increment();
        }else if(driveCounter.Value() == 2 && MechaRobot.driveBase.leftEncoder.get() / leftRatio >= 18){
                MechaRobot.elevator.lowerClaw.set(!MechaRobot.elevator.lowerClaw.get());//close
                Timer.delay(0.3);
                MechaRobot.elevator.lowerClawSpool.set(-1.0);//up
                Timer.delay(1.7); //? 1 tote high plus a bit
                MechaRobot.elevator.upperClawSpool.set(0.0);//stop, is high enough yet?
                Timer.delay(0.1);
                MechaRobot.elevator.lowerClawSpool.set(0.0);//stop
                driveCounter.increment();
        }else if(driveCounter.Value() == 3 && MechaRobot.driveBase.leftEncoder.get() / leftRatio >= (18+68)){// slow down 10 inches early
               right.setOutputRange(-slowSpeed, slowSpeed);//slow enough that we can get the fork down 1 tote height in time
               left.setOutputRange(-slowSpeed, slowSpeed);
                driveCounter.increment();
        }else if(driveCounter.Value() == 4 && MechaRobot.driveBase.leftEncoder.get() / leftRatio >= (18+71)){//note distance from same side of totes is 81 inches, i think, so 10 inches short of overlapped completely               
                MechaRobot.elevator.lowerClaw.set(!MechaRobot.elevator.lowerClaw.get());//open
                MechaRobot.elevator.lowerClawSpool.set(1.0);//down
                Timer.delay(1.4);// low enough to grab a new tote.
                MechaRobot.elevator.lowerClaw.set(!MechaRobot.elevator.lowerClaw.get());//close
                Timer.delay(0.25);
                MechaRobot.elevator.lowerClawSpool.set(-1.0);//up
                right.setOutputRange(-fastSpeed, fastSpeed);//speed back up
                left.setOutputRange(-fastSpeed, fastSpeed);
                Timer.delay(2.16);//? 1 tote high plus a bit
                MechaRobot.elevator.lowerClawSpool.set(0.0);//stop
                driveCounter.increment();
        }else if(driveCounter.Value() == 5 && MechaRobot.driveBase.leftEncoder.get() / leftRatio >= (18+71+81)){// slow down 10 inches early
                right.setOutputRange(-slowSpeed, slowSpeed);//slow enough that we can get the fork down 1 tote height in time
                left.setOutputRange(-slowSpeed, slowSpeed);
                driveCounter.increment();
        }else if(driveCounter.Value() == 6 && MechaRobot.driveBase.leftEncoder.get() / leftRatio >= (18+74+81)){
                MechaRobot.elevator.lowerClaw.set(!MechaRobot.elevator.lowerClaw.get());//open
                MechaRobot.elevator.lowerClawSpool.set(1.0);//down
                MechaRobot.elevator.upperClawSpool.set(-1.0);
                Timer.delay(0.6);// low enough to grab a new tote.
                MechaRobot.elevator.upperClawSpool.set(0.0);
                Timer.delay(1.0);
                MechaRobot.elevator.lowerClawSpool.set(0.0);
                MechaRobot.elevator.lowerClaw.set(!MechaRobot.elevator.lowerClaw.get());//close
                //MechaRobot.elevator.lowerClawSpool.set(-1.0);//up
                //Timer.delay(0.5);//? off the ground
                //MechaRobot.elevator.lowerClawSpool.set(0.0);//stop
                left.reset();
                right.reset();
                right.setOutputRange(-1.0, 1.0);//speed back up
                left.setOutputRange(-1.0, 1.0);
                driveCounter.increment();
        }else if(driveCounter.Value() == 7){
                turn.setSetpoint(-90);
                turn.enable();
                driveCounter.increment();
        }else if(driveCounter.Value() == 8 && turn.onTarget()){
                turn.reset();
                left.setSetpoint(100 * leftRatio);
                right.setSetpoint(100 * rightRatio);
                left.enable();
                right.enable();
                driveCounter.increment();
        }else if(driveCounter.Value() == 9 && (left.onTarget() || right.onTarget())){
                left.reset();
                right.reset();
                driveCounter.increment();
                MechaRobot.elevator.lowerClaw.set(!MechaRobot.elevator.lowerClaw.get());//open
                MechaRobot.driveBase.leftDriveA.set(-0.35);// backup
                MechaRobot.driveBase.rightDriveA.set(0.35);
                Timer.delay(0.3);
                MechaRobot.driveBase.leftDriveA.set(0.0);
                MechaRobot.driveBase.rightDriveA.set(0.0);
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
