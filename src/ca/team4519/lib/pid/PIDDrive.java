package ca.team4519.lib.pid;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Timer;

public class PIDDrive implements MechaController{
	
	Talon left1, left2, right1, right2 ;
	double kPL, kIL, kDL, kPR, kIR, kDR, kPA, kIA, kDA;
	PIDSource leftEncoder, rightEncoder, gyro;
	private double leftError = 0.0;
	private double leftPrevError = 0.0;
	private double leftTotalError = 0.0;
	private double leftOut;
	private double rightError = 0.0;
	private double rightPrevError = 0.0;
	private double rightTotalError = 0.0;
	private double rightout;
	private double gyroError = 0.0;
	private double gyroPrevError = 0.0;
	private double gyroTotalError = 0.0;
	private double gyroOut;
	boolean enabled;

	public PIDDrive(double kPL, double kIL, double kDL, PIDSource leftEncoder, Talon left1, Talon left2,
					double kPR, double kIR, double kDR, PIDSource rightEncoder, Talon right1, Talon right2,
					double kPA, double kIA, double kDA, PIDSource gyro){
		this.kPL = kPL;
		this.kIL = kIL;
		this.kDL = kDL;
		this.leftEncoder = leftEncoder;
		this.left1 = left1;
		this.left2 = left2;
		
		this.kPR = kPR;
		this.kIR = kIR;
		this.kDR = kDR;
		this.rightEncoder = rightEncoder;
		this.right1 = right1;
		this.right2 = right2;
		
		this.kPA = kPA;
		this.kIA = kIA;
		this.kDA = kDA;
		this.gyro = gyro;
		

	}
	
	public void doMath() {
		
		while(enabled){
			leftTotalError += leftError;
			leftPrevError = leftError;
			leftOut = kPL * leftError + kIL * leftTotalError + kDL * (leftError - leftPrevError);
		
			rightTotalError += rightError;
			rightPrevError = rightError;
			rightout = kPR * rightError + kIR * rightTotalError + kDR * (rightError - rightPrevError);
		
			gyroTotalError += gyroError;
			gyroPrevError = gyroError;
			gyroOut = (kPA * gyroError + kIA * gyroTotalError + kDA * (gyroError - gyroPrevError));
			Timer.delay(.02);	
		}
		 
         
	}

	
	public boolean turnFirst(boolean whatAmIDoingWithMyLife) {
		return whatAmIDoingWithMyLife;
	}
	
	
	
	public void PIDStart() {
		enabled = true;
	}
	
	public void Kill() {
		
	}
	
	public void chillFam() {
		System.out.println("Chill Fam");		
	}

	@Override
	public synchronized void enable() {
		enabled = true;
		
		
	}

	@Override
	public synchronized void disable() {
		enabled = false;
		
	}

}
