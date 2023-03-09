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
    public int totalTime = 0; 
    public ArrayList<SimProcess> processList = new ArrayList<SimProcess>();
    public ProcessQueue processQueue = new ProcessQueue(processList);

    //processor instance
    public Processor processor;
    

    public Scheduler () {

    }

    public Scheduler (File processFile) {
        try {
            this.readProcessFile(processFile);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public Scheduler (File processFile, Processor processor) {
        try {
            this.readProcessFile(processFile);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.processor = processor;
    }

    public ArrayList<SimProcess> readProcessFile (File processFile) throws Exception{
        //read the processFile and store into an arraylist
        Scanner fileScanner = new Scanner(processFile);
        ArrayList<SimProcess> temp = new ArrayList<SimProcess>();
        
        while(fileScanner.hasNext()) {
            String cur = fileScanner.nextLine();
            cur.split(',', 3);
            

            
        }

        return temp;
    } 

    /**
     * algorthim
     */
    public void roundRobinExecute () {
        while (processQueue.size() != 0) {
            SimProcess temp = processQueue.peek(); 
            totalTime += processor.execute(temp);
            if (processor.clockRate > temp.ticksToComplete) {
                
            }
        }
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