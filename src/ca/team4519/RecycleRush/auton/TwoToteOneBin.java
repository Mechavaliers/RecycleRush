package ca.team4519.RecycleRush.auton;

import ca.team4519.RecycleRush.MechaRobot;
import ca.team4519.lib.MechaCounter;
import ca.team4519.lib.pid.GyroMechaPid;
import ca.team4519.lib.pid.MechaPID;
import ca.team4519.lib.pid.GyroMechaPid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/**
 *
 */
public class TwoToteOneBin extends Command {

MechaCounter driveCounter = new MechaCounter("Drive Counter", 10);
	
	public float rightRatio = 13757/120;
	public float leftRatio = 13140/120;
	public boolean end = false;
	public double slowSpeed = 0.25;
    public double fastSpeed = 0.38;
	
	MechaPID left = new MechaPID(0.00020, 0.000003, 0.0000001, MechaRobot.driveBase.leftEncoder, MechaRobot.driveBase.leftDriveA, MechaRobot.driveBase.leftDriveB);
	MechaPID right = new MechaPID(-0.00020, -0.000003, -0.0000001, MechaRobot.driveBase.rightEncoder, MechaRobot.driveBase.rightDriveA, MechaRobot.driveBase.rightDriveB);
	GyroMechaPid turn = new GyroMechaPid(1/45.0, 0.1, 0.1, MechaRobot.driveBase.gyro, MechaRobot.driveBase.rightDriveA, MechaRobot.driveBase.leftDriveA);
	
    public TwoToteOneBin() {
    	super("TwoToteOneBin");
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
    	turn.setAbsoluteTolerance(20);
    	turn.setOutputRange(-0.4, 0.4);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	SmartDashboard.putBoolean("Are we here?", end);
    	SmartDashboard.putBoolean("Are we here?", false);
        if(driveCounter.Value() == 0){// pick up bin	
                MechaRobot.elevator.upperClaw.set(!MechaRobot.elevator.upperClaw.get());//close
                MechaRobot.elevator.upperClawSpool.set(1.0);//up
                Timer.delay(1.5);
                //we need to figure out in the timing below when the can gets high enough, currently in drivecounter ==3
                driveCounter.increment();
        }else if(driveCounter.Value() == 1){
            right.setOutputRange(-fastSpeed, fastSpeed);//slow enough that we can get the fork down 1 tote height in time
            left.setOutputRange(-fastSpeed, fastSpeed);  
                left.setSetpoint(200 * leftRatio);
                left.enable();
                right.setSetpoint(200 * rightRatio);
                right.enable();
                driveCounter.increment();
        }else if(driveCounter.Value() == 2 && MechaRobot.driveBase.leftEncoder.get() / leftRatio >= 18){
      	
                MechaRobot.elevator.lowerClaw.set(!MechaRobot.elevator.lowerClaw.get());//close
                Timer.delay(0.3);
                MechaRobot.elevator.lowerClawSpool.set(-1.0);//up
                Timer.delay(0.8); //? 1 tote high plus a bit
                MechaRobot.elevator.upperClawSpool.set(0.0);//stop, is high enough yet?
                Timer.delay(1.2);
                MechaRobot.elevator.lowerClawSpool.set(0.0);//stop
                driveCounter.increment();
        }else if(driveCounter.Value() == 3 && MechaRobot.driveBase.leftEncoder.get() / leftRatio >= (18+68)){// slow down 10 inches early
               right.setOutputRange(-slowSpeed, slowSpeed);//slow enough that we can get the fork down 1 tote height in time
               left.setOutputRange(-slowSpeed, slowSpeed);
                driveCounter.increment();
        }else if(driveCounter.Value() == 4 && MechaRobot.driveBase.leftEncoder.get() / leftRatio >= (18+71)){//note distance from same side of totes is 81 inches, i think, so 10 inches short of overlapped completely               
                MechaRobot.elevator.lowerClaw.set(!MechaRobot.elevator.lowerClaw.get());//open
                MechaRobot.elevator.lowerClawSpool.set(1.0);//down
                Timer.delay(1.65);// low enough to grab a new tote.
                MechaRobot.elevator.lowerClaw.set(!MechaRobot.elevator.lowerClaw.get());//close
                right.disable();
                left.disable();
                //Timer.delay(0.5);//? 1 tote high plus a bit
                
                driveCounter.increment();
        }else if(driveCounter.Value() == 5){
        	 turn.setSetpoint(-90);
             turn.enable();
             Timer.delay(0.25);
             MechaRobot.elevator.lowerClawSpool.set(-1.0);//up
             driveCounter.increment();
        }else if(driveCounter.Value() == 6 && turn.onTarget()){
        	MechaRobot.elevator.lowerClawSpool.set(0.0);//stop
        	left.setOutputRange(-0.6, 0.6);
    		right.setOutputRange(-0.6,0.6);
             turn.disable();
             Timer.delay(0.2);
             left.setSetpoint(85*leftRatio);
             right.setSetpoint(85*rightRatio);
             left.enable();
             right.enable();
             driveCounter.increment();
        }else if(driveCounter.Value() == 7 &&(left.onTarget()||right.onTarget())){
             left.disable();
             right.disable();
             Timer.delay(0.45);
             turn.setSetpoint(90);
             turn.setAbsoluteTolerance(20);
             turn.enable();
             driveCounter.increment();
        }else if(driveCounter.Value() == 8 && turn.onTarget()){
     		turn.disable();
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
