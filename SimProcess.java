public class SimProcess {
    
    public int timeSlice;
    public int PID, priority, threadID;
    public String processID;
    public int ticksToComplete;
    
    public SimProcess (int PID) {
        this.PID = PID;
        this.processID = "process." + Math.random() * 10 + Math.random() * 10; 
    }

    //Main object constructor, should be used always 
    public SimProcess (String processID) {
        this.PID = (int)( Math.random() * (10  * Math.random()) * Math.random());
        this.processID = processID;
        this.timeSlice = findOptimalTimeSlice();
    }

    public SimProcess (int PID, int timeSlice, String processID) {
        this.PID = PID;
        this.processID = processID;
        this.timeSlice = timeSlice;
    }

    public SimProcess (int PID, int timeSlice, int ticksToComplete, String processID) {
        this.PID = PID;
        this.processID = processID;
        this.timeSlice = timeSlice;
        this.ticksToComplete = ticksToComplete;
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
