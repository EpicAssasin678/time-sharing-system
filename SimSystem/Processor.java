package SimSystem;
import java.util.HashMap;

public class Processor {

    
    //instance fields
    int clockRate;
    int timeSlice;
    int cycleCompletion; 
    int systemClock;

    //priority

    int contextSwitchValue = 2;


    //processor statistics 
    public HashMap<String, int[]> processStatistics = new HashMap<>();

    public Processor (int clockRate) {
        this.clockRate = clockRate;
    }

    public Processor (int clockRate, int timeSlice) {
        this.clockRate = clockRate;
        this.timeSlice = timeSlice;
    }

    public Processor (int clockRate, int timeSlice, int systemClock) {
        this.clockRate = clockRate;
        this.timeSlice = timeSlice;
        this.systemClock = systemClock;
        
    }

    /**
     * totalProcessTime, totalTimeSlices
     * processTime, timeWaiting, timeSlicesToCompleted
     * @param args
     */
    public void linkProcessorStatistics(int[] args) {

    }

    public int execute (SimProcess process) {
        

        int executionTime = 0;
        for (int i = executionTime; i < timeSlice; i++) {
            cycleCompletion = (cycleCompletion + 1)%clockRate;
            //((cycleCompletion + 1)%clockRate > 0) ? cycleCompletion += 1 : cycleCompletion = 1;
            process.ticksToComplete--;
            executionTime++;
            //if the process is already done, we break
            if (process.ticksToComplete == 0) {
                break;
            };
        }

        //timeSlice has concluded
        return executionTime;
    }

    /**
     * Switches context on a given value, for our purposes, we made it a meaningful 4 which would include
     * save registers, load registers, update various tables and lists.
     */
    public int switchContext () {
        return contextSwitchValue;
    }



}

/**
 * 
 *      int executionTime = 0;
        for (int i = executionTime; i < timeSlice; i++) {
            cycleCompletion = (cycleCompletion + 1)%clockRate;
            process.ticksToComplete--;
            //if the process is already done, we break
            if (process.ticksToComplete == 0) break;
        }
        return executionTime;
 * 
 */
