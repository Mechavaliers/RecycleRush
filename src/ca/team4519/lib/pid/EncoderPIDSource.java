package ca.team4519.lib.pid;

import edu.wpi.first.wpilibj.Encoder;

class EncoderPIDSource implements PIDSource {
	
	private final Encoder encoder;
	
	public EncoderPIDSource(final Encoder encoder) {
		this.encoder = encoder;
	}
	
	public Encoder getSensor() {
		return encoder;
	}
	
	public float getValue() {
		return encoder.get();
	}

}
