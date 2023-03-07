import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Scanner;


/**
 * 
 * 3/7/23
 * 
 */

public class Scheduler {

    //instance fields
    public ArrayList<SimProcess> processList = new ArrayList<SimProcess>();
    public ProcessQueue processQueue = new ProcessQueue(processList);

    public Scheduler () {

    }

    public Scheduler (File processFile) {

    }

    public ArrayList<SimProcess> readProcessFile (File processFile) throws Exception{
        //read the processFile and store into an arraylist
        Scanner fileScanner = new Scanner(processFile);
        ArrayList<SimProcess> temp = new ArrayList<SimProcess>();
        
        while(fileScanner.hasNext()) {
            
            temp.add(new SimProcess());
        }

        return temp;
    } 

    /**
     * algorthim
     */
    public void roundRobinExecute () {

    }

    /**
     * 
     * takes in n number of processes and trys to sort based on any given algorthim
     */
    public void loadProcesses (File processFile) {
        //hand processes off to sorting function


    }


    public static void main(String[] args) {
        //load library 
        ArrayList<Integer> processes = new ArrayList<Integer>();
        

    }

}