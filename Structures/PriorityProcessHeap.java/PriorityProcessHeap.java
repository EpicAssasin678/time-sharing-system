import java.util.*;

import SimSystem.SimProcess;

class PriorityProcessHeapNode {

    PriorityProcessHeapNode child, left, right, parent;
    SimProcess process;
    //creates a fibonacci heap node
    public PriorityProcessHeapNode (SimProcess process) {
        this.right = this;
        this.left = this;
        this.process = process;

    }

}

/**
 * @author  Zachery Uporsky 
 * @apiNote  Implements a fibonacci heap for the priority process queue 
 */
public class PriorityProcessHeap  {
    
    private PriorityProcessHeapNode root;
    private int size;

    public PriorityProcessHeap () {
        this.root = null;
        this.size = 0;
    }
    
    public boolean isEmpty () {
        return this.size == 0;
    }

    public void clear () {
        this.root = null;
        this.size = 0;

    }

    public void insert (SimProcess process) {
        PriorityProcessHeapNode node = new PriorityProcessHeapNode(process);
        if (this.root == null) {
            this.root = node;
        } else {
            this.root.left.right = node;
            node.left = this.root.left;
            this.root.left = node;
            node.right = this.root;
            if (node.process.priority < this.root.process.priority) {
                this.root = node;
            }
        }
        this.size++;
    }

    public void display () {
        PriorityProcessHeapNode tmp = this.root;
        if (tmp == null) {
            System.out.println("Empty heap");
            return;
        }
        do {
            System.out.println(tmp.process.processID);
            tmp = tmp.right;
        } while (tmp != this.root);
    }

    public SimProcess getItem () {
        return this.root.process;
    }

    public SimProcess getRoot () {
        return this.root.process;
    }
    
}
