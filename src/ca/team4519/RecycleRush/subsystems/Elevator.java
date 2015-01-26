package ca.team4519.RecycleRush.subsystems;

import java.util.Hashtable;


import ca.team4519.lib.Loopable;
import ca.team4519.lib.Subsystem;
import ca.team4519.RecycleRush.Constants;
import ca.team4519.lib.pid.PID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Elevator extends Subsystem implements Loopable{

	public boolean open1 = false;
	public boolean open2 = false;
	public boolean close1 = true;
	public boolean close2 = true;
	
	public final double UPPER_CLAW_POS_TO_HEIGHT_RATIO = (Constants.wheelSize.getDouble() * Math.PI) / (12.0 * 256.0);
	public static double LOWER_CLAW_POS_TO_HEIGHT_RATIO =(Constants.wheelSize.getDouble() * Math.PI) / (12.0 * 256.0);
	
	public Victor lowerClawSpool = new Victor(Constants.lowerClawSpool.getInt());
	public Victor upperClawSpool = new Victor(Constants.upperClawSpool.getInt());
	
	public Solenoid upperClaw = new Solenoid(Constants.upperClaw.getInt());
	public Solenoid lowerClaw = new Solenoid(Constants.lowerClaw.getInt());

	public Joystick upperClawStick = new Joystick(1);
	public Joystick lowerClawStick = new Joystick(2);
	

	public Encoder upperClawPosEncoder = new Encoder(Constants.upperClawPosEncoderCHAN_A.getInt(), Constants.upperClawPosEncoderCHAN_B.getInt(), true);
	public Encoder lowerClawPosEncoder = new Encoder(Constants.lowerClawPosEncoderCHAN_A.getInt(), Constants.lowerClawPosEncoderCHAN_B.getInt(), false);
	
	
	
	public void elevatorMovement(double upperClaw, double lowerClaw) {
		lowerClawSpool.set(lowerClaw * Math.abs(lowerClaw));
		upperClawSpool.set(upperClaw * Math.abs(upperClaw));
	}
	
	public boolean upperGrip() {
		return upperClawStick.getRawButton(3);
	}
	
	public boolean lowerGrip() {
		return lowerClawStick.getRawButton(3);
		
	}
	
	public Elevator() {
		 super("Elevator");	
	 }

	 @SuppressWarnings({ "rawtypes", "unchecked" })
	public Hashtable serialize() {
		 Hashtable spools = new Hashtable();
		 Hashtable encoders = new Hashtable();
			 
			spools.put("LowerSpool", new Double(lowerClawSpool.get()));
			spools.put("UpperSpool", new Double(upperClawSpool.get()));
			
			encoders.put("upperClawPos", new Double(upperClawPosEncoder.get()));
			encoders.put("lowerClawPosEncoder",  new Double(lowerClawPosEncoder.get()));
			
			data.put("spools", spools);
			data.put("encoders", encoders);
		 return data;
	 }

	 public void elevatorStop() {
		 	 
	 lowerClawSpool.set(0);
	 upperClawSpool.set(0);
	 }
	 
	 public void clawToggle1(boolean button1, Solenoid claw1) {
		 
				 
		 if (button1 == true && open1 == true) {
			 claw1.set(true);
			 open1 = false;
			 close1 = true;
		 }else if(button1 == true && close1 == true){
			 claw1.set(false);
			 open1 = true;
			 close1 = false;
		 }
		 }
		 
	public void clawToggle2(boolean button2, Solenoid claw2) {
						 
			 if (button2 == true && open2 == true) {
				 claw2.set(true);
				 open2 = false;
				 close2 = true;
			 }else if(button2 == true && close2 == true){
				 claw2.set(false);
				 open2 = true;
				 close2 = false;
			 }

	 }
	 
	 
}