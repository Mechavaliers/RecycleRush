package ca.team4519.RecycleRush;

import edu.wpi.first.wpilibj.command.*;
import edu.wpi.first.wpilibj.smartdashboard.*;
import ca.team4519.lib.MechaIterativeRobot;
import ca.team4519.RecycleRush.auton.*;

public class Robot extends MechaIterativeRobot {
    	
	SendableChooser autonSelect = new SendableChooser();
	
	Command autoMode;
	
	public void robotInit() {
		
		autonSelect.addDefault("driveDistance", new DriveDist());
		autonSelect.addObject("swagaroooo", new OneToteOneBin());
		SmartDashboard.putData("Autonomous Mode Selector", autonSelect);
    	
    	
    }
	
	public void autonomousInit() {
		autoMode = (Command) autonSelect.getSelected();
		autoMode.start();
		MechaRobot.driveBase.resetEncoders();
	}
	
	public void autonomousPeriodic() {
		MechaRobot.driveBase.shiftGears(true);	
		
		Scheduler.getInstance().run();
	}
    
	public void teleopInit() {
		MechaRobot.driveBase.resetEncoders();
	}
	
    public void teleopPeriodic() {
    	MechaRobot.driveBase.shiftGears(true);	
    	
    	MechaRobot.elevator.clawToggle1(MechaRobot.elevator.upperGrip(), MechaRobot.elevator.lowerClaw);	
    	MechaRobot.elevator.clawToggle2(MechaRobot.elevator.lowerGrip(), MechaRobot.elevator.upperClaw);
    	
    	MechaRobot.elevator.elevatorMovement(MechaRobot.elevator.upperClawStick.getY(), -MechaRobot.elevator.lowerClawStick.getY());	
    	MechaRobot.driveBase.setLeftRightStrafePower(MechaRobot.driveBase.forwardAxis(), MechaRobot.driveBase.turningAxis(), MechaRobot.driveBase.strafeAxis(), MechaRobot.driveBase.gamepad.getRawAxis(2) - MechaRobot.driveBase.gamepad.getRawAxis(3));
    }
   
    public void testPeriodic() {
    
    }
    
    public void allPeriodic() {
    	MechaRobot.driveBase.update();
    	MechaRobot.elevator.update();
    }
}