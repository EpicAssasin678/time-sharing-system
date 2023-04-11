package Structures.PriorityQueue;
import java.util.ArrayList;
import java.util.function.Consumer;

import SimSystem.SimProcess;
import Structures.ProcessQueue.ProcessQueue;
import Structures.PriorityQueue.PriorityQueueItem;

/**
 * @author Zachery Uporsky
 * Alternative implementation of priority system by using a Queue that compares the priority
 * of the topmost element and compares the hanging time of processes with the same priority.
 * 
 * @apiNote Implementation of said "priority" can change if you modify the compareTo Consumer param
 * of the PriorityQueueItem class.
 *
 */
public class PriorityQueue extends ProcessQueue {

    public ArrayList<PriorityQueueItem> priorityMap;

    public Consumer<PriorityQueueItem> compareAction;
    
    public PriorityQueue () {
        super();
        this.priorityMap = new ArrayList<PriorityQueueItem>();
    }
    
    public PriorityQueue (ArrayList<SimProcess> processList) {
        super(processList);
        for (SimProcess process : processList) {
            this.priorityMap.add(new PriorityQueueItem(process));
        }
    }

    public void insert (SimProcess process) {
        this.add(new PriorityQueueItem(process));
    }

    @Override
    public boolean add (Object process) {
        try {
            this.add(new PriorityQueueItem( (SimProcess) process));
            priorityMap.add(new PriorityQueueItem( (SimProcess) process));
            priorityMap.sort( (a, b) -> a.process.priority - b.process.priority);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}


/**
 * package Structures.PriorityQueue;
import java.util.ArrayList;
import java.util.function.Consumer;

import SimSystem.SimProcess;
import Structures.ProcessQueue.ProcessQueue;
import Structures.PriorityQueue.PriorityQueueItem;

/**
 * @author Zachery Uporsky
 * Alternative implementation of priority system by using a Queue that compares the priority
 * of the topmost element and compares the hanging time of processes with the same priority.
 * 
 * @apiNote Implementation of said "priority" can change if you modify the compareTo Consumer param
 * of the PriorityQueueItem class.
 *
 */
/*
public class PriorityQueue extends ProcessQueue {

    public ArrayList<PriorityQueueItem> priorityMap;

    public Consumer<PriorityQueueItem> compareAction;
    
    public PriorityQueue () {
        super();
        this.priorityMap = new ArrayList<PriorityQueueItem>();
    }
    
    public PriorityQueue (ArrayList<SimProcess> processList) {
        super(processList);
        for (SimProcess process : processList) {
            this.priorityMap.add(new PriorityQueueItem(process));
        }
    }

    public void insert (SimProcess process) {
        this.add(new PriorityQueueItem(process));
    }

    @Override
    public boolean add (Object process) {
        try {
            this.add(new PriorityQueueItem( (SimProcess) process));
            priorityMap.add(new PriorityQueueItem( (SimProcess) process));
            priorityMap.sort( (a, b) -> a.process.priority - b.process.priority);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public SimProcess peek () {
        if (this.size() == 0) {
            return null;
        } else {
            
            
        }
    }
}
 */