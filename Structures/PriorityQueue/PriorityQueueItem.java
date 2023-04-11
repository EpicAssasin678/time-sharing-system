package Structures.PriorityQueue;

import java.util.function.Consumer;

import SimSystem.SimProcess;

/**
 * @author Zachery Uporsky
 * @apiNote Compare action should use the a comparison that satisfies the 
 */
public class PriorityQueueItem implements Comparable<PriorityQueueItem> {

    SimProcess process;
    Consumer<PriorityQueueItem> compareAction; //This makes this Queue sort on basically any grounds
    
    public PriorityQueueItem (SimProcess process) {
        this.process = process;
    }

    public SimProcess ref () {
        return this.process;
    }

    @Override
    public int compareTo (PriorityQueueItem item) {
        //todo make this work ideally
        if (this.compareAction != null) {
            this.compareAction.accept(item);
        }
        if (this.process.priority < item.process.priority) {
            return -1;
        } else if (this.process.priority > item.process.priority) {
            return 1;
        } else {
            return 0;
        }
    }

    //Compare action must assign a result to a value within the lambda expression i.e. result, 1 -> result = 1
    public void setCompareAction (Consumer<PriorityQueueItem> compareAction) {
        this.compareAction = compareAction;
    }

}