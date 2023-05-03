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


    public ArrayList<PriorityQueueItem> priorityList = new ArrayList<PriorityQueueItem>();

    public Consumer<PriorityQueueItem> compareAction;
    
    public ArrayList<PriorityQueueItem> priorityListCopy;

    public PriorityQueue () {
        super();
        this.priorityList = new ArrayList<PriorityQueueItem>();
    }
    
    public PriorityQueue (ArrayList<SimProcess> processList) {
        super(processList);
        //add each process to the priority map
        for (SimProcess process : processList) {
            //this.priorityList.add(new PriorityQueueItem(process));
            this.add(process);
            
        }
    }

    
    public void insert (SimProcess process) {
        this.add(new PriorityQueueItem(process));
    }

    @Override
    public boolean add (Object process) {
        try {
            super.add(process);
            priorityList.add(new PriorityQueueItem( (SimProcess) process));
            priorityList.sort( (a, b) -> b.process.priority - a.process.priority);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Returns the process from the priority list at the specified index.
     * Note: Eventually may need to change this to the super if super ordering changes.
     * @param index
     * @return
     */
    public SimProcess get (int index) {
        return this.priorityList.get(index).process;
    }

    @Override
    public int size () {
        
        return this.priorityList.size();
    }

    @Override 
    public SimProcess element () {
        return this.priorityList.get(0).process;
    }

    @Override
    public SimProcess peek () {
        return (priorityList.size() == 0) ? null : priorityList.get(0).process;
    }

    @Override 
    public SimProcess remove() {
        super.remove();
        return this.priorityList.remove(0).process;
    }

    @Override
    public SimProcess remove(int index) {
        super.remove(index);
        return this.priorityList.remove(index).process;
    }

    @Override
    public boolean shuffleToBottom() {
        try {
            //maintain the priority by using the instance add method
            super.shuffleToBottom();
            this.add(this.remove());
            return true;   
        } catch (Exception e) {
            return false;
        }
    }


    @Override
    public boolean shuffleToBottom(int index) {
        try {
            //maintain the priority by using the instance add method
            super.shuffleToBottom(index);
            this.add(this.remove(index));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public ArrayList<PriorityQueueItem> getPriorityList () {
        return this.priorityList;
    }

    public String printPriorityList () {
        String temp = "";
        for (PriorityQueueItem item : this.priorityList) {
            temp.concat(String.format("PID: %d : Priority: %d", item.process.PID, item.process.priority));
        }
        return temp;
    }
    

    public static void main(String[] args) {
        ArrayList<SimProcess> testList = new ArrayList<SimProcess>();
        PriorityQueue test = new PriorityQueue(testList);
        
        System.out.println(test);
        System.out.println(test.printPriorityList());

        System.out.println(test.size());
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

    public ArrayList<PriorityQueueItem> priorityList;

    public Consumer<PriorityQueueItem> compareAction;
    
    public PriorityQueue () {
        super();
        this.priorityList = new ArrayList<PriorityQueueItem>();
    }
    
    public PriorityQueue (ArrayList<SimProcess> processList) {
        super(processList);
        for (SimProcess process : processList) {
            this.priorityList.add(new PriorityQueueItem(process));
        }
    }

    public void insert (SimProcess process) {
        this.add(new PriorityQueueItem(process));
    }

    @Override
    public boolean add (Object process) {
        try {
            this.add(new PriorityQueueItem( (SimProcess) process));
            priorityList.add(new PriorityQueueItem( (SimProcess) process));
            priorityList.sort( (a, b) -> a.process.priority - b.process.priority);
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