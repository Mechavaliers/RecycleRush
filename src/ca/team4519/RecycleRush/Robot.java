package ca.team4519.RecycleRush;

import edu.wpi.first.wpilibj.command.*;
import edu.wpi.first.wpilibj.smartdashboard.*;
import ca.team4519.lib.MechaIterativeRobot;
import ca.team4519.RecycleRush.auton.*;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class Robot extends MechaIterativeRobot {
    	
	SendableChooser autonSelect = new SendableChooser();
	//CameraServer server;
	Command autoMode;
	PowerDistributionPanel pdp = new PowerDistributionPanel();
	
	
	public void robotInit() {

/*		server = CameraServer.getInstance();
		server.startAutomaticCapture("cam0");
		server.setQuality(50);
	*/	
		MechaRobot.driveBase.gyro.initGyro();
		
		autonSelect.addObject("Drive Distance", new DriveDist());
		autonSelect.addDefault("One Tote One Bin", new OneToteOneBin());
		autonSelect.addObject("Three Tote One Bin", new ThreeToteOneBin());
		autonSelect.addObject("Two Tote One Bin", new TwoToteOneBin());
		autonSelect.addObject("Just Bin", new JustBin());
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
		//MechaRobot.elevator.lowerClaw.set(true);
	}
	
    public void teleopPeriodic() {
    	MechaRobot.driveBase.shiftGears(true);	
    	
    	MechaRobot.elevator.clawToggle1(MechaRobot.elevator.upperGrip(), MechaRobot.elevator.upperClaw);	
    	MechaRobot.elevator.clawToggle2(MechaRobot.elevator.lowerGrip(), MechaRobot.elevator.lowerClaw);
    	
    	MechaRobot.elevator.elevatorMovement(-MechaRobot.elevator.upperClawStick.getY(), MechaRobot.elevator.lowerClawStick.getY());	
    	MechaRobot.driveBase.setLeftRightStrafePower(
    			MechaRobot.driveBase.forwardAxis(), 
    			MechaRobot.driveBase.turningAxis(), 
    			MechaRobot.driveBase.gamepad.getRawAxis(3), 
    			MechaRobot.driveBase.gamepad.getRawAxis(2), 
    			MechaRobot.driveBase.gamepad.getRawButton(6), 
    			MechaRobot.driveBase.gamepad.getRawButton(5));
    }
    public void disabledInit(){
    	MechaRobot.driveBase.gamepad.setRumble(Joystick.RumbleType.kLeftRumble, 0.0f);
    	MechaRobot.driveBase.gamepad.setRumble(Joystick.RumbleType.kRightRumble, 0.0f);
    }
    public void testPeriodic() {
    	MechaRobot.driveBase.gyro.initGyro();
    }
    
    public void allPeriodic() {
		SmartDashboard.putNumber("PDP Total Current (Amps)", pdp.getTotalCurrent());
		SmartDashboard.putNumber("PDP Total Energy (Watts)", pdp.getTotalPower());
		SmartDashboard.putNumber("PDP Total Voltage (Volts)", pdp.getVoltage());
		SmartDashboard.putNumber("PDP Temp",pdp.getTemperature());
    	MechaRobot.driveBase.update();
    	MechaRobot.elevator.update();
    }
}