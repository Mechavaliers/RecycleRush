 package ca.team4519.lib;



public abstract class Controller implements Loopable {
  protected boolean enabled = false;
  
  public abstract void update();
  public abstract void reset();
  public abstract double getGoal();
  
  public void enable() {
    enabled = true;
  }
  
  public void disable() {
    enabled = false;
  }

  public boolean enabled() {
    return enabled;
  }
}