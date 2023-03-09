import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
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
        // TODO Auto-generated method stub
        return this.processList.size();
    }

    @Override
    public boolean isEmpty() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isEmpty'");
    }

    @Override
    public boolean contains(Object o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'contains'");
    }

    @Override
    public Iterator iterator() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'iterator'");
    }

    @Override
    public Object[] toArray() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toArray'");
    }

    @Override
    public Object[] toArray(Object[] a) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toArray'");
    }

    @Override
    public boolean remove(Object o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }
    
    @Override
    public SimProcess remove() {
        // TODO Auto-generated method stub
        return processList.remove(0);
    }

    @Override
    public boolean containsAll(Collection c) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'containsAll'");
    }

    @Override
    public boolean addAll(Collection c) {
        // TODO Auto-generated method stub
        try {
            c.forEach(item -> {this.processList.add((SimProcess) item);});
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
    }

    @Override
    public boolean removeAll(Collection c) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeAll'");
    }

    @Override
    public boolean retainAll(Collection c) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'retainAll'");
    }

    @Override
    public void clear() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'clear'");
    }

    @Override
    public boolean add(Object e) {
        // TODO Auto-generated method stub
        try {
            processList.add((SimProcess) e);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public boolean offer(Object e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'offer'");
    }


    
    @Override
    public SimProcess poll() {
        // TODO Auto-generated method stub
        SimProcess temp = processList.remove(0);
        return temp;
    }
    
    @Override
    public SimProcess element() {
        // TODO Auto-generated method stub
        return processList.get(0);
    }
    
    @Override
    public SimProcess peek() {
        // TODO Auto-generated method stub
        return (processList.size() == 0) ? null : processList.get(0);
    }
    
    public boolean shuffleToBottom() {
        try {
            processList.add(processList.remove(0));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean shuffleToBottom(int index) {
        try {
            processList.add(processList.remove(index));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
}
