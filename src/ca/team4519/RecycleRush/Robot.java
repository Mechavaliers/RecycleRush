package ca.team4519.RecycleRush;

//import edu.wpi.first.wpilibj.CameraServer;
import ca.team4519.lib.MechaIterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Robot extends MechaIterativeRobot {
    	
    public void robotInit() {
    	MechaRobot.driveBase.resetEncoders();
    	//server.setQuality(50);
    	//server.startAutomaticCapture("cam0");

    }
	public void autonomousPeriodic() {
		//MechaRobot.driveBase.autoDrive.DRIVE.run();
		
		//MechaRobot.driveBase.resetEncoders();

    }
    
	public void teleopInit() {
		MechaRobot.driveBase.resetEncoders();
	}
	
    public void teleopPeriodic() {

    	
    MechaRobot.elevator.elevatorStop();	
    MechaRobot.driveBase.setLeftRightStrafePower((MechaRobot.driveBase.tankLeftAxis()), MechaRobot.driveBase.tankRightAxis(), MechaRobot.driveBase.strafeNegativeAxis(), MechaRobot.driveBase.strafePositiveAxis());
    MechaRobot.driveBase.shiftGears(MechaRobot.driveBase.getShift());
    System.out.println("GearShift: "+ MechaRobot.driveBase.getShift());
   // System.out.println("DriveBase: "+ MechaRobot.driveBase.getLeftEncoderDistance());
    //System.out.println("winch: "+ MechaRobot.driveBase.getRightEncoderDistance());
    }
   
    public void testPeriodic() {
    
    }
    
    public void allPeriodic() {
    	MechaRobot.driveBase.update();
    	MechaRobot.elevator.update();
    }
}
