package ca.team4519.RecycleRush;

import edu.wpi.first.wpilibj.command.*;
import edu.wpi.first.wpilibj.smartdashboard.*;
import ca.team4519.lib.MechaIterativeRobot;
import ca.team4519.RecycleRush.auton.*;

public class Robot extends MechaIterativeRobot {
    	
	SendableChooser autonSelect = new SendableChooser();
	
	Command autoMode;
	
	public void robotInit() {
		
		MechaRobot.driveBase.gyro.initGyro();
		
		autonSelect.addDefault("Drive Distance", new DriveDist());
		autonSelect.addObject("One Tote One Bin", new OneToteOneBin());
		autonSelect.addObject("Three Tote One Bin", new ThreeToteOneBin());
		SmartDashboard.putData("Autonomous Mode Selector", autonSelect);
    	
    	
    }
	
	public void autonomousInit() {
		autoMode = (Command) autonSelect.getSelected();
		autoMode.start();
		MechaRobot.driveBase.resetAll();
		MechaRobot.elevator.lowerClaw.set(true);
	}
	
	public void autonomousPeriodic() {
		MechaRobot.driveBase.shiftGears(true);	
		
		Scheduler.getInstance().run();
	}
    
	public void teleopInit() {
		MechaAuton.threeToteOneBin.cancel();
		MechaAuton.oneToteOneBin.cancel();
		MechaRobot.driveBase.resetEncoders();
		MechaRobot.elevator.lowerClaw.set(true);
	}
	
    public void teleopPeriodic() {
    	MechaRobot.driveBase.shiftGears(true);	
    	
    	MechaRobot.elevator.clawToggle1(MechaRobot.elevator.upperGrip(), MechaRobot.elevator.upperClaw);	
    	MechaRobot.elevator.clawToggle2(MechaRobot.elevator.lowerGrip(), MechaRobot.elevator.lowerClaw);
    	
    	MechaRobot.elevator.elevatorMovement(-MechaRobot.elevator.upperClawStick.getY(), MechaRobot.elevator.lowerClawStick.getY());	
    	MechaRobot.driveBase.setLeftRightStrafePower(MechaRobot.driveBase.forwardAxis(), MechaRobot.driveBase.turningAxis(),MechaRobot.driveBase.gamepad.getRawAxis(3), MechaRobot.driveBase.gamepad.getRawAxis(2), MechaRobot.driveBase.gamepad.getRawButton(6));
    }
   
    public void testPeriodic() {
    	MechaRobot.driveBase.gyro.initGyro();
    }
    
    public void allPeriodic() {
    	MechaRobot.driveBase.update();
    	MechaRobot.elevator.update();
    }
}