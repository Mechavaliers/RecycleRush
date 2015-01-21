package ca.team4519.RecycleRush.actions;

import ca.team4519.RecycleRush.MechaRobot;
import edu.wpi.first.wpilibj.Timer;

/*
 * Action is something a robot is supposed to do in a specific time.
 */
public abstract class Action extends MechaRobot {

  public boolean shouldRun = true;

  public abstract boolean execute();

  public abstract void init();

  public abstract void done();
  Timer t = new Timer();
  double timeout = 10000000;

  public void kill() {
    done();
    shouldRun = false;
  }

  public void run() {
    t.start();
    if (shouldRun) {
      init();
    }
    while (shouldRun && !execute() && !isTimedOut()) {
      try {
        Thread.sleep(30);
      } catch (InterruptedException ex) {
      }
    }
    if (shouldRun) {
      done();
    }
  }

  public void setTimeout(double timeout) {
    this.timeout = timeout;
  }

  public boolean isTimedOut() {
    return t.get() >= timeout;
  }
}