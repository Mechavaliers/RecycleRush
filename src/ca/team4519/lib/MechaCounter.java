package ca.team4519.lib;

public class MechaCounter {

    private final String name;
    private final int maxCount;
    private int count;
    
    public MechaCounter(String id, int max){
        this.name = id;
        maxCount = max;
        count = 0;
        
    }
        
    public void increment() {
        if(count < maxCount) count++;
    }
    
    public void decrement() {
        if(count > 0) count--;
    }
       
    public void reset() {
        count = 0;    
    }
    
    public int Value(){
        return count;
    }
    
    public String toString(){
        return name + ": " + count;
    }
}
