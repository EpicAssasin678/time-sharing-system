import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Scanner;
import java.io.FileWriter;
import java.util.HashMap;

/**
 * 
 * 3/7/23
 * 
 */

public class Scheduler {

    //instance fields
    public int totalTime = 0; 
    public int totalProcesses = 0;
    public ArrayList<SimProcess> processList = new ArrayList<SimProcess>();
    public ProcessQueue processQueue = new ProcessQueue(processList);
    //processor instance
    public Processor processor;
    
    //data collection    
    public File auditLog = new File("./auditLog.txt");
    public FileWriter logWriter;
    public boolean CSVFlag = false;
    public ArrayList<String> CSVOutputStream = new ArrayList<String>();


    public Scheduler () {

    }

    public Scheduler (File processFile){
        try {
            this.logWriter = new FileWriter(auditLog);
            processList = this.readProcessFile(processFile);
            this.processor = new Processor(totalTime, totalTime);
            logWriter.write(String.format("[INITIALIZING] Scheduler created, using processor with following properties. \nClockrate: %d\nQuantum: %d", 
                                processor.clockRate, processor.timeSlice));
        } catch (Exception e) {
            // TODO: handle exception
        }
        
    }

    public Scheduler (File processFile, Processor processor) {
        try {
            this.logWriter = new FileWriter(auditLog);
            processList = this.readProcessFile(processFile);
            this.processor = processor;
            this.processor.systemClock = totalTime;
            logWriter.write(String.format("\n[INITIALIZING] Scheduler created, using processor with following properties. \nClockrate: %d\nQuantum: %d", processor.clockRate, processor.timeSlice));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void setCSVFlag (boolean value) {
        this.CSVFlag = value;
    }

    public ArrayList<SimProcess> readProcessFile (File processFile) throws Exception{
        logWriter.write("\n[INITIALIZING] Processing input file.");
        //read the processFile and store into an arraylist
        Scanner fileScanner = new Scanner(processFile);
        ArrayList<SimProcess> temp = new ArrayList<SimProcess>();
        //seperate our file and enqueue our objects 
        while(fileScanner.hasNext()) {

            String cur = fileScanner.nextLine();
            String[] values = cur.split(",");
            processQueue.add( new SimProcess(totalProcesses+1, Integer.parseInt(values[1]), values[0]) );

            logWriter.write("\n[INITIALIZING] Loaded process to queue. PID: " + totalProcesses+1 + " alias: " 
                                + values[0] + " processingTicks: " + values[1]);   

            totalProcesses++;
            //shouldn't need this
            temp.add( new SimProcess(totalProcesses+1, Integer.parseInt(values[1]), values[0]));
        }
        fileScanner.close();
        return temp;
    } 

    /**
     * algorthim
     */
    public void roundRobinExecute () {

        if (CSVFlag) {
            CSVOutputStream.add("totalRuntime,timeSliceValue,contextSwitchValue"); 
            CSVOutputStream.add("processID,timeTaken,timeCompleted");
        };
        HashMap <String, ArrayList<Integer> > stats = new HashMap<>(); 
        
        try {
            while (processQueue.size() != 0) {
                SimProcess temp = processQueue.element();              
                int timeTaken = processor.execute(temp);
                //Check if we already, recorded the time the SimProcess already started
                totalTime += timeTaken;
                if (!( stats.containsKey(temp.processID))) {
                    stats.put(temp.processID, new ArrayList<>(2));
                    stats.get(temp.processID).add(totalTime - timeTaken);
                }
                
                logWriter.write(String.format("\n[EXECUTION][TIME:%d] Executed %s in %d", totalTime, temp.processID, timeTaken));
                //check if the process is finished 
                if (temp.ticksToComplete == 0) {

                    processQueue.remove();
                    
                    if (CSVFlag) CSVOutputStream.add(String.format("%s, %d, %d", 
                                temp.processID, 
                                totalTime - stats.get(temp.processID).get(0), 
                                totalTime)
                            );
                    
                    logWriter.write(String.format("\n[EXECUTION][TIME:%d] Completed following process, removed from queue.", totalTime));
                }

                //or if it needs to be requeued
                else if (temp.ticksToComplete > 0) {

                    processQueue.shuffleToBottom();

                    logWriter.write(String.format("\n[EXECUTION][TIME:%d] Process has  %d remaining ticks to complete, shuffled to bottom of queue.", totalTime, temp.ticksToComplete));

                    totalTime += processor.switchContext();

                    logWriter.write(String.format("\n[EXECUTION][TIME:%d] Context switched to head of queue.", totalTime));
                } 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (CSVFlag) this.CSVOutputStream.add(1, String.format("%d,%d,%d", totalTime, processor.timeSlice, processor.contextSwitchValue));
        System.out.println(stats);
        System.out.println(CSVOutputStream);
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
        Scheduler scheduler = new Scheduler(new File("./inputfile.csv"), new Processor(6, 5));        
        scheduler.roundRobinExecute();
    }
}