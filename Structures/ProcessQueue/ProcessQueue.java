package Structures.ProcessQueue;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;

import SimSystem.SimProcess;

import java.util.List;
import java.util.ArrayList;

public class ProcessQueue implements Queue{

    private ArrayList<SimProcess> processList;

    public ProcessQueue () {
        this.processList = new ArrayList<SimProcess>();
    }

    public ProcessQueue (ArrayList<SimProcess> processList) {
        this.processList = processList;
    }

    public String displayQueue () {
        String tmp = "";
        this.processList.forEach( (val) -> {
            tmp.concat(String.format("Process: " , val.processID));
        });
        return tmp;
    }

    @Override
    public int size() {
        return this.processList.size();
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException("Unimplemented method 'isEmpty'");
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException("Unimplemented method 'contains'");
    }

    @Override
    public Iterator iterator() {
        return this.processList.iterator();
    }

    @Override
    public Object[] toArray() {
        return this.processList.toArray();
    }

    @Override
    public Object[] toArray(Object[] a) {
        return this.processList.toArray(a);
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    public SimProcess remove(int index) {
        return this.processList.remove(index);
    }
    
    @Override
    public SimProcess remove() {
        return processList.remove(0);
    }

    @Override
    public boolean containsAll(Collection c) {
        throw new UnsupportedOperationException("Unimplemented method 'containsAll'");
    }

    @Override
    public boolean addAll(Collection c) {
        try {
            c.forEach(item -> {this.processList.add((SimProcess) item);});
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean removeAll(Collection c) {
        throw new UnsupportedOperationException("Unimplemented method 'removeAll'");
    }

    @Override
    public boolean retainAll(Collection c) {
        throw new UnsupportedOperationException("Unimplemented method 'retainAll'");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Unimplemented method 'clear'");
    }

    @Override
    public boolean add(Object e) {
        try {
            processList.add((SimProcess) e);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public boolean offer(Object e) {
        throw new UnsupportedOperationException("Unimplemented method 'offer'");
    }


    
    @Override
    public SimProcess poll() {
        SimProcess temp = processList.remove(0);
        return temp;
    }
    
    @Override
    public SimProcess element() {
        return processList.get(0);
    }
    
    @Override
    public SimProcess peek() {
        return (processList.size() == 0) ? null : processList.get(0);
    }
    
    /**
     * Removes the head of the queue and adds that element to the end.
     * @return true if successful, false if not
     */
    public boolean shuffleToBottom() {
        try {
            processList.add(processList.remove(0));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Removes an element at a specified position and appends it to the end of the queue.
     * @return true if successful, false if not
     */
    public boolean shuffleToBottom(int index) {
        try {
            processList.add(processList.remove(index));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    

    /**
     * Sorts the queue with priority 
     * @apiNote This method isn't supposed to be used, instead use the priorityQueue class instead.
     */
    public void sortWithPriority () {
        this.processList.sort((a, b) -> a.priority - b.priority);
    }

    public void SRTSort () {
        this.processList.sort((a, b) -> b.ticksToComplete - a.ticksToComplete);
    }

  
    
}
