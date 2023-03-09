public class Processor {

    int clockRate;
    
    public Processor (int clockRate) {
        this.clockRate = clockRate;
    }

    public int execute (SimProcess process) {
        process.ticksToComplete -= this.clockRate;
        return this.clockRate;
    }

    //Don't use
    public int switchContext (SimProcess process) {
        return this.clockRate;
    }

}
