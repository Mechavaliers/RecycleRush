package ca.team4519.lib.util;

import java.util.Vector;

public class ConstantsBase {

	private static final Vector<Constant> constants = new Vector();
	
	public static class Constant {
		private String name;
		private double value;
	
	
	public Constant(String name, double value) {
		this.name = name;
		this.value = value;
		constants.addElement(this);
		
	}	
	
	public String getName() {
		return name;
	}
	
	public double getDouble() {
		return value;
	}
	
	public int getInt() {
		return (int) value;
	}
  }
}