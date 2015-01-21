package ca.team4519.lib;

import edu.wpi.first.wpilibj.Joystick;
import ca.team4519.lib.Loopable;

public class Gamepad extends Joystick implements Loopable {

	public Gamepad(int port){
		super(port);
	}
	
	public double leftTank(){
		return getRawAxis(2);
		
	}
	
	public double rightTank(){
		return getRawAxis(5);
	}

	public double strafeLeft(){
		return getRawAxis(3);
	}
	
	@Override
	public void update() {
		
	}

}