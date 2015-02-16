package ca.team4519.RecycleRush.auton;

import ca.team4519.RecycleRush.MechaRobot;
import ca.team4519.lib.MechaCounter;
import ca.team4519.lib.pid.MechaPID;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class ThreeToteOneBin extends Command {

	MechaCounter driveCounter = new MechaCounter("Drive Counter", 10);
	
	public float rightRatio = 13757/120;
	public float leftRatio = 13140/120;
	
	MechaPID left = new MechaPID(0.0, 0.0, 0.0, MechaRobot.driveBase.leftEncoder, MechaRobot.driveBase.leftDriveA, MechaRobot.driveBase.leftDriveB);
	MechaPID right = new MechaPID(0.0, 0.0, 0.0, MechaRobot.driveBase.rightEncoder, MechaRobot.driveBase.rightDriveA, MechaRobot.driveBase.rightDriveB);
	MechaPID turn = new MechaPID(0.0, 0.0, 0.0, MechaRobot.driveBase.gyro, MechaRobot.driveBase.leftDriveA, MechaRobot.driveBase.rightDriveA);
	
	public boolean end = false;
	
    public ThreeToteOneBin() {
    	super("ThreeToteOneBin");
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	MechaRobot.elevator.upperClaw.set(false);
    	MechaRobot.elevator.lowerClaw.set(false);
    	driveCounter.reset();
    	left.disable();
    	right.disable();
    	end = false;
    	MechaRobot.driveBase.resetAll();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double slowSpeed = 0.3;
        double fastSpeed = 0.75;
        if(driveCounter.Value() == 0){// pick up bin
                MechaRobot.elevator.upperClaw.set(true);//close
                MechaRobot.elevator.upperClawSpool.set(1.0);//up
                //timer.delay(1)?
                //we need to figure out in the timing below when the can gets high enough, currently in drivecounter ==3
                driveCounter.increment();
        }else if(driveCounter.Value() == 1){
                left.setSetpoint(200 * leftRatio);
                left.enable();
                right.setSetpoint(200 * rightRatio);
                right.enable();
                driveCounter.increment();
        }else if(driveCounter.Value() == 2 && MechaRobot.driveBase.leftEncoder.get() / leftRatio >= 18){
                MechaRobot.elevator.lowerClaw.set(false);//close
                MechaRobot.elevator.lowerClawSpool.set(-1.0);//up
                Timer.delay(1.0); //? 1 tote high plus a bit
                MechaRobot.elevator.lowerClawSpool.set(0.0);//stop
                driveCounter.increment();
        }else if(driveCounter.Value() == 3 && MechaRobot.driveBase.leftEncoder.get() / leftRatio >= (18+61)){// slow down 10 inches early
                right.setOutputRange(-slowSpeed, slowSpeed);//slow enough that we can get the fork down 1 tote height in time
                left.setOutputRange(-slowSpeed, slowSpeed);
                driveCounter.increment();
        }else if(driveCounter.Value() == 4 && MechaRobot.driveBase.leftEncoder.get() / leftRatio >= (18+71)){//note distance from same side of totes is 81 inches, i think, so 10 inches short of overlapped completely
                MechaRobot.elevator.upperClawSpool.set(0.0);//stop, is high enough yet?
                MechaRobot.elevator.lowerClaw.set(true);//open
                MechaRobot.elevator.lowerClawSpool.set(1.0);//down
                right.setOutputRange(-0.3, 0.3);//slow enough that we can get the fork down 1 tote height in time
                left.setOutputRange(-0.3, 0.3);
                Timer.delay(1.0);// low enough to grab a new tote.
                MechaRobot.elevator.lowerClaw.set(false);//close
                MechaRobot.elevator.lowerClawSpool.set(-1.0);//up
                right.setOutputRange(-fastSpeed, fastSpeed);//speed back up
                left.setOutputRange(-fastSpeed, fastSpeed);
                Timer.delay(1.0);//? 1 tote high plus a bit
                MechaRobot.elevator.lowerClawSpool.set(0.0);//stop
                driveCounter.increment();
        }else if(driveCounter.Value() == 6 && MechaRobot.driveBase.leftEncoder.get() / leftRatio >= (18+61+81)){// slow down 10 inches early
                right.setOutputRange(-slowSpeed, slowSpeed);//slow enough that we can get the fork down 1 tote height in time
                left.setOutputRange(-slowSpeed, slowSpeed);
        }else if(driveCounter.Value() == 6 && MechaRobot.driveBase.leftEncoder.get() / leftRatio >= (18+71+81)){
                MechaRobot.elevator.lowerClaw.set(true);//open
                MechaRobot.elevator.lowerClawSpool.set(1.0);//down
                Timer.delay(1.0);// low enough to grab a new tote.
                MechaRobot.elevator.lowerClaw.set(false);//close
                MechaRobot.elevator.lowerClawSpool.set(-1.0);//up
                Timer.delay(0.2);//? off the ground
                MechaRobot.elevator.lowerClawSpool.set(0.0);//stop
                left.reset();
                right.reset();
                right.setOutputRange(-fastSpeed, fastSpeed);//speed back up
                left.setOutputRange(-fastSpeed, fastSpeed);
                driveCounter.increment();
        }else if(driveCounter.Value() == 7){
                turn.setSetpoint(-90);
                turn.enable();
                driveCounter.increment();
        }else if(driveCounter.Value() == 8 && turn.onTarget()){
                turn.reset();
                left.setSetpoint(107 * leftRatio);
                right.setSetpoint(107 * rightRatio);
                left.enable();
                right.enable();
                driveCounter.increment();
        }else if(driveCounter.Value() == 9 && (left.onTarget() || right.onTarget())){
                left.reset();
                right.reset();
                driveCounter.increment();
                MechaRobot.elevator.lowerClaw.set(true);//open
                MechaRobot.driveBase.leftDriveA.set(0.5);// backup
                MechaRobot.driveBase.rightDriveA.set(-0.5);
                Timer.delay(0.1);
                MechaRobot.driveBase.leftDriveA.set(0.0);
                MechaRobot.driveBase.rightDriveA.set(-0.0);
                end = true;
        }
 
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return end;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
