package ca.team4519.lib;


/**
 * An interface for controllers. Controllers run control loops, the most command
 * are PID controllers and there variants, but this includes anything that is
 * controlling an actuator in a separate thread.
 *
 * @author alex
 */
interface MechaController {
    /**
     * Allows the control loop to run.
     */
    public void enable();

    /**
     * Stops the control loop from running until explicitly re-enabled by calling
     * {@link enable()}.
     */
    public void disable();
}
