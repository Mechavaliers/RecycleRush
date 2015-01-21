package ca.team4519.RecycleRush.subsystems;

import java.util.Hashtable;

import ca.team4519.lib.Loopable;
import ca.team4519.lib.Subsystem;
import ca.team4519.RecycleRush.Constants;
import ca.team4519.lib.pid.PID;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Elevator extends Subsystem implements Loopable{

	public final double UPPER_CLAW_POS_TO_HEIGHT_RATIO = (Constants.wheelSize.getDouble() * Math.PI) / (12.0 * 256.0);
	public static double LOWER_CLAW_POS_TO_HEIGHT_RATIO =(Constants.wheelSize.getDouble() * Math.PI) / (12.0 * 256.0);
	
	public Victor lowerClawSpool = new Victor(Constants.lowerClawSpool.getInt());
	public Victor upperClawSpool = new Victor(Constants.upperClawSpool.getInt());
	
	public Solenoid upperClawGrab = new Solenoid(Constants.upperClawGrab.getInt());
	public Solenoid upperClawRelease = new Solenoid(Constants.upperClawRelease.getInt());
	public Solenoid lowerClawGrab = new Solenoid(Constants.lowerClawGrab.getInt());
	public Solenoid lowerClawRelease = new Solenoid(Constants.lowerClawRelease.getInt());
	

	private Encoder upperClawPosEncoder = new Encoder(Constants.upperClawPosEncoderCHAN_A.getInt(), Constants.upperClawPosEncoderCHAN_B.getInt(), false);
	private Encoder loweClawPosEncoder = new Encoder(Constants.lowerClawPosEncoderCHAN_A.getInt(), Constants.lowerClawPosEncoderCHAN_B.getInt(), false);
	
	//private PID upperClawPID = new PID(upperClawPosEncoder, 1.0, 1.0, 1.0);
	
	 public Elevator() {
		 super("Elevator");	
	 }

	 @SuppressWarnings({ "rawtypes", "unchecked" })
		public Hashtable serialize() {
		 return data;
	 }

	 public void elevatorStop() {
		 	 
	 lowerClawSpool.set(0);
	 upperClawSpool.set(0);
	 }
	 
	 
	 
}
