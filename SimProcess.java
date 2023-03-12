public class SimProcess {
    
    public int timeSlice;
    public int PID, priority, threadID;
    public String processID;
    public int processTime;
    public int ticksToComplete;

    //for data collection
    public int timeTakenToFinish;

    public enum State {
        FINISHED,
        SUSPENDED,
        RUNNING,
        HALTED
    }
    
    public State processState; 

    public SimProcess (int PID) {
        this.PID = PID;
        this.processID = "process." + Math.random() * 10 + Math.random() * 10; 
        this.processState = State.SUSPENDED;
    }

    //Main object constructor, should be used always 
    public SimProcess (String processID) {
        this.PID = (int)( Math.random() * (10  * Math.random()) * Math.random());
        this.processID = processID;
        this.timeSlice = findOptimalTimeSlice();
        this.processState = State.SUSPENDED;
    }

    public SimProcess (int PID, int ticksToComplete, String processID) {
        this.PID = PID;
        this.processID = processID;
        this.ticksToComplete = ticksToComplete;

        this.processTime = this.ticksToComplete;
        this.processState = State.SUSPENDED;
    }

    public SimProcess (int PID, int timeSlice, int ticksToComplete, String processID) {
        this.PID = PID;
        this.processID = processID;
        this.timeSlice = timeSlice;
        this.ticksToComplete = ticksToComplete;
        
        this.processTime = this.ticksToComplete;
        this.processState = State.SUSPENDED;
    }

    public void setTicksToComplete (int cur) {
        this.ticksToComplete = cur;
    }

    /**
     * Eventually optionally implement a method for creating a time slice based off of best time. 
     * @return
     */
    public int findOptimalTimeSlice () {
        return (int) Math.random();
    }


    public static void main(String[] args) {
        
    }
    
}
