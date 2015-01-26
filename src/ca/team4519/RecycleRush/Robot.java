package ca.team4519.RecycleRush;

//import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.PIDController;

import ca.team4519.lib.MechaIterativeRobot;
import ca.team4519.lib.pid.PIDDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Robot extends MechaIterativeRobot {
    	
	PIDDrive dog = new PIDDrive(MechaRobot.driveBase.leftPid, MechaRobot.driveBase.rightPid, MechaRobot.driveBase.leftDriveA, MechaRobot.driveBase.rightDriveA, 300);
	PIDController pidcontl = new PIDController(-0.001, 0.01, 0, MechaRobot.driveBase.leftEncoder, MechaRobot.driveBase.leftDriveA);
	PIDController pidcontr = new PIDController(0.001, 0.0001, 0, MechaRobot.driveBase.rightEncoder, MechaRobot.driveBase.rightDriveA);
	PIDController lowerdist = new PIDController(1, 0.001, 0, MechaRobot.elevator.lowerClawPosEncoder, MechaRobot.elevator.lowerClawSpool);
	
	public void robotInit() {
    	
    	MechaRobot.driveBase.resetEncoders();
    	//server.setQuality(50);
    	//server.startAutomaticCapture("cam0");
    	

    }
	
	public void autonomousInit() {
		MechaRobot.driveBase.resetEncoders();
	}
	
	public void autonomousPeriodic() {
		
		System.out.println("encoderout: " + MechaRobot.driveBase.getLeftEncoderDistance());
		/*System.out.println("pid out: " + MechaRobot.driveBase.leftPid.pid(12));*/
		//dog.PIDGo();
		
		pidcontl.setSetpoint(-20234);
		pidcontr.setSetpoint(-20234);
		
		pidcontl.enable();
		pidcontr.enable();
    }
    
	public void teleopInit() {
		pidcontr.disable();
		pidcontl.disable();
		MechaRobot.driveBase.resetEncoders();
	}
	
    public void teleopPeriodic() {

    	lowerdist.setSetpoint(5000);
    	
    	if (MechaRobot.elevator.lowerClawStick.getRawButton(5)) {
    		lowerdist.enable();
    	}else {
    		lowerdist.disable();
    	}
    	
    MechaRobot.elevator.clawToggle1(MechaRobot.elevator.lowerGrip(), MechaRobot.elevator.lowerClaw);
    MechaRobot.elevator.clawToggle2(MechaRobot.elevator.upperGrip(), MechaRobot.elevator.upperClaw);
    	
    MechaRobot.elevator.elevatorMovement(MechaRobot.elevator.upperClawStick.getY(), -MechaRobot.elevator.lowerClawStick.getY());	
    MechaRobot.driveBase.setLeftRightStrafePower((MechaRobot.driveBase.tankLeftAxis()), MechaRobot.driveBase.tankRightAxis(), MechaRobot.driveBase.strafeNegativeAxis(), MechaRobot.driveBase.strafePositiveAxis());
    MechaRobot.driveBase.shiftGears(MechaRobot.driveBase.getShift());
    System.out.println("GearShift: "+ MechaRobot.driveBase.getShift());
    System.out.println("DriveBase left: "+ MechaRobot.driveBase.getLeftEncoderDistance());
    System.out.println("drive right: "+ MechaRobot.driveBase.getRightEncoderDistance());
    System.out.println("elevator lower: " + MechaRobot.elevator.lowerClawPosEncoder.get());
    System.out.println("elevator high: " + MechaRobot.elevator.upperClawPosEncoder.get());
    }
   
    public void testPeriodic() {
    
    }
    
    public void allPeriodic() {
    	MechaRobot.driveBase.update();
    	MechaRobot.elevator.update();
    }
}