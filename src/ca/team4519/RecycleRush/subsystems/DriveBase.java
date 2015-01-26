package ca.team4519.RecycleRush.subsystems;

import java.util.Hashtable;

import ca.team4519.lib.Loopable;
import ca.team4519.lib.Subsystem;
import ca.team4519.lib.MechaGyro;
import ca.team4519.lib.RioAcceleromiter;
import ca.team4519.lib.pid.PID;
//import ca.team4519.lib.pid.PIDDrive;
//import ca.team4519.lib.pid.PIDSource;
import ca.team4519.RecycleRush.Constants;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class DriveBase extends Subsystem implements Loopable {
	
		//THIS NEEDS TO BE TWEAKED AND ADJUSTED AND ALL SORTS OF BUM
		//^rt
	  	public final double RIGHT_ENCOCDER_TO_DISTANCE_RATIO = (Math.PI*6)/2176 /*(Constants.wheelSize.getDouble() * Math.PI) / (13.0)*/;
	  	public final double LEFT_ENCOCDER_TO_DISTANCE_RATIO = (Math.PI*6)/2176 /*(Constants.wheelSize.getDouble() * Math.PI) / (13.0)*/;
	  
	  	//Speed Controllers
	  	public Talon leftDriveA = new Talon(Constants.leftDriveA.getInt());
	  	public Talon leftDriveB = new Talon(Constants.leftDriveB.getInt());
	  	public Talon rightDriveA = new Talon(Constants.rightDriveA.getInt());
	  	public Talon rightDriveB = new Talon(Constants.rightDriveB.getInt());
	  	public Talon strafeA = new Talon(Constants.strafeA.getInt());
	  	public Joystick gamepad = new Joystick(0);
	  
	  	//Encoders
	  	public Encoder leftEncoder = new Encoder(Constants.leftDriveEncoderCHAN_A.getInt(), Constants.leftDriveEncoderCHAN_B.getInt(), false);
	  	public Encoder rightEncoder = new Encoder(Constants.rightDriveEncoderCHAN_A.getInt(), Constants.rightDriveEncoderCHAN_B.getInt(), true);
	  
	    public PID leftPid = new PID(leftEncoder, 0.0f, 0.0f, 0.0f, 0.0f);
	    public PID rightPid = new PID(rightEncoder, 0.0f, 0.0f, 0.0f, 0.0f);
	  	
	  	public MechaGyro gyro = new MechaGyro(Constants.gyro.getInt());
	  	
	  	//Solenoids 
	  	private Solenoid shift = new Solenoid(Constants.chassisShift.getInt());

	  	
	  	public RioAcceleromiter accelerometer = new RioAcceleromiter();
	  	
	    public void setLeftRightStrafePower(double leftPower, double rightPower, double strafePositive, double strafeNegative) {
	    	
	    	double strafeInput = strafePositive - strafeNegative;
	    	double strafeOut = 0;
	    	
	    	if(0.09 > leftPower && leftPower > -0.09) leftPower = 0;
	    	if(0.09 > rightPower && rightPower > -0.09) rightPower = 0;
	    	if(0.09 > strafeInput && strafeInput > -0.09) strafeInput = 0;
	    	
	    	if(strafeInput > strafeOut) {
	    		strafeOut += Constants.rampingConstant.getDouble();
	    	}else if(strafeInput < strafeOut) {
	    		strafeOut -= Constants.rampingConstant.getDouble();
	    	}
	    			
	    	
	    leftDriveA.set(-leftPower);
	    leftDriveB.set(-leftPower);
	    rightDriveA.set(rightPower);
	    rightDriveB.set(rightPower);
	   strafeA.set(strafeOut);
	    
	    }

	    public double tankLeftAxis() {
	    	return gamepad.getRawAxis(Constants.tankLeftAxis.getInt());
	    }
	    
	    public double tankRightAxis() {
	    	return gamepad.getRawAxis(Constants.tankRightAxis.getInt());
	    }
	    
	    public double strafePositiveAxis() {
	    	return gamepad.getRawAxis(Constants.strafePositiveAxis.getInt());
	    }
	    
	    public double strafeNegativeAxis() {
	    	return gamepad.getRawAxis(Constants.strafeNegativeAxis.getInt());
	    }
	    
	    public boolean getShift() {
	    	return gamepad.getRawButton(6);
	    }
	    
	    public void shiftGears(boolean state) {
	    	if(state){
	    		shift.set(false);
	    	}else{
	    		shift.set(true);
	    	}
	    }
	    
	    public DriveBase() {
	    	super("DriveBase");	
	    }

	
	    @SuppressWarnings({ "rawtypes", "unchecked" })
		public Hashtable serialize() {
		
	    Hashtable leftDrive = new Hashtable();
	    Hashtable rightDrive = new Hashtable();
	    Hashtable encoders = new Hashtable();

	    leftDrive.put("leftDriveA", new Double(leftDriveA.get()));
	    leftDrive.put("leftDriveB", new Double(leftDriveB.get()));

	    rightDrive.put("rightDriveA", new Double(rightDriveA.get()));
	    rightDrive.put("rightDriveB", new Double(rightDriveB.get()));

	    encoders.put("leftEncoder", new Double(leftEncoder.get()));
	    encoders.put("rightEncoder", new Double(rightEncoder.get()));    
	    data.put("leftDrive", leftDrive);
	    data.put("rightDrive", rightDrive);
	    data.put("encoders", encoders);
	    data.put("gyro", new Double(getGyroAngle()));
	    return data;
		}
	
	    public Encoder getLeftEncoder() {
		    return leftEncoder;
	   }

	   	public double getLeftEncoderDistance() { // in feet
		    return leftEncoder.get() * LEFT_ENCOCDER_TO_DISTANCE_RATIO;
	   }

	   	public double getLeftEncoderDistanceInMeters() {
		    return getLeftEncoderDistance() * 0.3048;
	   }

	   	public Encoder getRightEncoder() {
		    return rightEncoder;
	   }

	   	public double getRightEncoderDistance() {
		    return rightEncoder.get() * RIGHT_ENCOCDER_TO_DISTANCE_RATIO;
	   }

	   	public double getRightEncoderDistanceInMeters() {
		    return getRightEncoderDistance() * 0.3048;
	   }

	   	public double getGyroAngle(){
		      return gyro.getAngle();
	   }
		  
	   	public double getGyroRate(){
		      return gyro.getRate();
	   }  
		  
	   	public double nonContAngle(){
		   double angle = gyro.getAngle();
		   if(gyro.getAngle() > 0){
			   angle = gyro.getAngle()%360;
		          return angle;
		   }else if(gyro.getAngle() < 0){
			   angle = gyro.getAngle()%-360;
		          return angle;
		       }
		     return angle;
		  }
		  
	   	public double smartAngle(){ // swag420blazeit
		      double degRot = 0;
		        if(3 >= nonContAngle() && nonContAngle() <= -3){
		           degRot = 0;
		           return degRot;
		        }else{
		            degRot = nonContAngle();
		            return degRot;
		        }
	   	}
	   	
	   	public double XAcceleration() {
	   		return accelerometer.getX();
	   	}
	   	
	   	public double YAcceleration() {
	   		return accelerometer.getY();
	   	}
		  
	   	public void resetGyro(){
		      gyro.reset();
	   	}
		  
	   	public double getAverageDistance() {
		    return (getRightEncoderDistance() + getLeftEncoderDistance()) / 2.0;
	   	}
		  
	   	public void resetEncoders() {
		    leftEncoder.reset();
		    rightEncoder.reset();
	   	}
	   	
	   	public void update(){
		      SmartDashboard.putNumber("Left Drive Distance", getLeftEncoderDistance());
		      SmartDashboard.putNumber("Right Drive Distance", getRightEncoderDistance());
		      SmartDashboard.putNumber("Both Encoders, Average Distance", getAverageDistance());
		      SmartDashboard.putNumber("gyro", smartAngle());
		      super.update(); 
	   	}
}