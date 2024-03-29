package SimSystem;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Scanner;

import Structures.ProcessQueue.ProcessQueue;
import Structures.PriorityQueue.PriorityQueue;

import java.io.FileWriter;
import java.util.HashMap;

/**
 * @author Zachery Uporsky
 * 3/7/23
 * 
 */
public class Scheduler {

    //instance fields
    public int totalTime = 0;
    public int totalProcesses = 0;
    public ArrayList<SimProcess> processList = new ArrayList<SimProcess>();
    //public ProcessQueue processQueue = new ProcessQueue(processList);
    public PriorityQueue processQueue = new PriorityQueue(processList);
    public PriorityQueue dummy = new PriorityQueue();
    //processor instance
    public Processor processor;

    //data collection
    public File auditLog = new File("./auditLog.txt");
    public FileWriter logWriter;
    public boolean CSVFlag = false;
    public ArrayList<String> CSVOutputStream = new ArrayList<String>();

    //statistics by process: processTime, timeWaiting, timeSlicesToCompleted
    public HashMap<String, int[]> processStatistics = new HashMap<>();


    public Scheduler () {

    }

    public Scheduler (File processFile) {

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
            if (values.length  >  2) processQueue.add( new SimProcess(totalProcesses+1, Integer.parseInt(values[1]), values[0], Integer.parseInt(values[2]) ) ); //added priority

            logWriter.write("\n[INITIALIZING] Loaded process to queue. PID: " + totalProcesses+1 + " alias: " 
                                + values[0] + " processingTicks: " + values[1] + " priority: " + values[2]);

            totalProcesses++;
            //shouldn't need this
            temp.add( new SimProcess(totalProcesses+1, Integer.parseInt(values[1]), values[0], Integer.parseInt(values[2]) ));
        }

        fileScanner.close();
        return temp;
    }

    /**
     * algorthim
     */
    public void roundRobinExecute () {

        if (CSVFlag) {
            CSVOutputStream.add("totalRuntime,totalProcessTime,timeSliceValue,contextSwitchValue,slicesRequired,");
            CSVOutputStream.add("processID,processTime,timeCompleted,timeWaiting,slicesTaken,TAT,NTAT");

        };

        HashMap <String, ArrayList<Integer> > stats = new HashMap<>();
        int timeSlices = 0;
        int totalProcessTime = 0;
        
        try {
            while (processQueue.size() != 0) {
                timeSlices++;
                //Add the value of context switching each time after loading the process
                totalTime += processor.switchContext();

                SimProcess temp = processQueue.element();
                int timeTaken = processor.execute(temp);
                //Check if we already, recorded the time the SimProcess already started
                totalTime += timeTaken;
                if (!( stats.containsKey(temp.processID))) {
                    stats.put(temp.processID, new ArrayList<>(2));
                    stats.get(temp.processID).add(totalTime - timeTaken);
                    totalProcessTime += temp.processTime; 
                }
                
                logWriter.write(String.format("\n[EXECUTION][TIME:%d] Executed %s in %d", totalTime, temp.processID, timeTaken));
                //check if the process is finished
                if (temp.ticksToComplete == 0) {
                    
                    processQueue.remove();
                    int timeCompleted = totalTime - stats.get(temp.processID).get(0);
                    //processID, , totalTime, timeTaken, timeSlicesRequired
                    double TAT = (totalTime * 1.0); //turnaround time 
                    double NTAT = TAT / (temp.processTime * 1.0000); //normalized turnaround time
                    //NTAT = TAT / processor.timeSlice;
                    

                    //processID, processTime, timeCompleted, timeWaiting, slicesTaken, TAT, NTAT
                    if (CSVFlag) CSVOutputStream.add(String.format("%s,%d,%d,%d,%d,%.2f,%.2f", 
                                temp.processID, 
                                temp.processTime,
                                totalTime, 
                                stats.get(temp.processID).get(0),
                                timeSlices,
                                TAT,
                                NTAT)
                            );
                    

                    logWriter.write(String.format("\n[EXECUTION][TIME:%d] Completed following process, removed from queue.", totalTime)); 
                }
                
                //or if it needs to be requeued
                else if (temp.ticksToComplete > 0) {
                    
                    processQueue.shuffleToBottom();

                    logWriter.write(String.format("\n[EXECUTION][TIME:%d] Process has %d remaining ticks to complete, shuffled to bottom of queue.", totalTime, temp.ticksToComplete));
                    logWriter.write(String.format("\n[EXECUTION][TIME:%d] Context switched to head of queue.", totalTime));
                }
            }
            logWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

        
        //totalRuntime,totalProcessTime,timeSliceValue,contextSwitchValue,slicesRequired
        if (CSVFlag) this.CSVOutputStream.add(1, String.format("%d,%d,%d,%d,%d", 
                totalTime, 
                totalProcessTime,
                processor.timeSlice, 
                processor.contextSwitchValue, 
                timeSlices));
        
        //System.out.println(stats);
        System.out.println(CSVOutputStream);
    }

    /**
     * 
     * 
     */
    public void priorityExecute () {
        if (CSVFlag) {
            CSVOutputStream.add("totalRuntime,totalProcessTime,timeSliceValue,contextSwitchValue,slicesRequired,");
            CSVOutputStream.add("processID,processTime,timeCompleted,timeWaiting,slicesTaken,TAT,NTAT");
        };

        HashMap <String, ArrayList<Integer> > stats = new HashMap<>();
        int timeSlices = 0;
        int totalProcessTime = 0;
        
        try {
            while (processQueue.size() != 0) {
                processQueue.sort( (a, b) -> a.ref().priority - b.ref().priority);
                timeSlices++;
                //Add the value of context switching each time after loading the process
                totalTime += processor.switchContext();

                SimProcess temp = processQueue.element();
                int timeTaken = processor.execute(temp);
                //Check if we already, recorded the time the SimProcess already started
                totalTime += timeTaken;
                if (!( stats.containsKey(temp.processID))) {
                    stats.put(temp.processID, new ArrayList<>(2));
                    stats.get(temp.processID).add(totalTime - timeTaken);
                    totalProcessTime += temp.processTime;
                }
                
                logWriter.write(String.format("\n[EXECUTION][TIME:%d] Executed %s in %d", totalTime, temp.processID, timeTaken));
                //check if the process is finished
                if (temp.ticksToComplete == 0) {
                    
                    processQueue.remove();
                    int timeCompleted = totalTime - stats.get(temp.processID).get(0);
                    //processID, , totalTime, timeTaken, timeSlicesRequired
                    double TAT = (totalTime * 1.0); //turnaround time 
                    double NTAT = TAT / (temp.processTime * 1.0000); //normalized turnaround time
                    //NTAT = TAT / processor.timeSlice;
                    

                    //processID, processTime, timeCompleted, timeWaiting, slicesTaken, TAT, NTAT
                    if (CSVFlag) CSVOutputStream.add(String.format("%s,%d,%d,%d,%d,%.2f,%.2f", 
                                temp.processID, 
                                temp.processTime,
                                totalTime, 
                                stats.get(temp.processID).get(0),
                                timeSlices,
                                TAT,
                                NTAT)
                            );
                    

                    logWriter.write(String.format("\n[EXECUTION][TIME:%d] Completed following process, removed from queue.", totalTime)); 
                }
                
                //or if it needs to be requeued
                else if (temp.ticksToComplete > 0) {
                    
                    processQueue.shuffleToBottom();

                    logWriter.write(String.format("\n[EXECUTION][TIME:%d] Process has %d remaining ticks to complete, shuffled to bottom of queue.", totalTime, temp.ticksToComplete));
                    logWriter.write(String.format("\n[EXECUTION][TIME:%d] Context switched to head of queue.", totalTime));
                }
            }
            logWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

                
        //totalRuntime,totalProcessTime,timeSliceValue,contextSwitchValue,slicesRequired
        if (CSVFlag) this.CSVOutputStream.add(1, String.format("%d,%d,%d,%d,%d", 
                totalTime, 
                totalProcessTime,
                processor.timeSlice, 
                processor.contextSwitchValue, 
                timeSlices));
        
        //System.out.println(stats);
        
        System.out.println(CSVOutputStream);

    }


    public void SRTExecute () {
        //add TAT and NTAT
        if (CSVFlag) {
            CSVOutputStream.add("totalRuntime,totalProcessTime,timeSliceValue,contextSwitchValue,slicesRequired,");
            CSVOutputStream.add("processID,processTime,timeCompleted,timeWaiting,slicesTaken,TAT,NTAT");
        };

        HashMap < String, ArrayList<Integer> > stats = new HashMap<>();
        int timeSlices = 0;
        int totalProcessTime = 0;
        
        try {
            while (processQueue.size() != 0) {
                processQueue.sort((a, b) -> a.ref().ticksToComplete - b.ref().ticksToComplete);
                
                timeSlices++;
                //Add the value of context switching each time after loading the process
                totalTime += processor.switchContext();

                SimProcess temp = processQueue.element();
                int timeTaken = processor.execute(temp); 

                //Check if we already, recorded the time the SimProcess already started
                totalTime += timeTaken;
                
                if (!( stats.containsKey(temp.processID))) {
                    stats.put(temp.processID, new ArrayList<>(2));
                    stats.get(temp.processID).add(totalTime - timeTaken);
                    totalProcessTime += temp.processTime;
                }
                
                logWriter.write(String.format("\n[EXECUTION][TIME:%d] Executed %s in %d", totalTime, temp.processID, timeTaken));
                //check if the process is finished
                if (temp.ticksToComplete == 0) {
                    
                    processQueue.remove();

                    double TAT = (totalTime * 1.0); //turnaround time 
                    double NTAT = TAT / (temp.processTime * 1.0000); //normalized turnaround time
                    //NTAT = TAT / processor.timeSlice;
                    

                    //processID, processTime, timeCompleted, timeWaiting, slicesTaken, TAT, NTAT
                    if (CSVFlag) CSVOutputStream.add(String.format("%s,%d,%d,%d,%d,%.2f,%.2f", 
                                temp.processID, 
                                temp.processTime,
                                totalTime, 
                                stats.get(temp.processID).get(0),
                                timeSlices,
                                TAT,
                                NTAT)
                            );
                    

                    logWriter.write(String.format("\n[EXECUTION][TIME:%d] Completed following process, removed from queue.", totalTime)); 
                }
                
                //or if it needs to be requeued
                else if (temp.ticksToComplete > 0) {
                    
                    processQueue.shuffleToBottom();

                    logWriter.write(String.format("\n[EXECUTION][TIME:%d] Process has %d remaining ticks to complete, shuffled to bottom of queue.", totalTime, temp.ticksToComplete));
                    logWriter.write(String.format("\n[EXECUTION][TIME:%d] Context switched to head of queue.", totalTime));
                }
            }
            logWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

                
        //totalRuntime,totalProcessTime,timeSliceValue,contextSwitchValue,slicesRequired
        if (CSVFlag) this.CSVOutputStream.add(1, String.format("%d,%d,%d,%d,%d", 
                totalTime, 
                totalProcessTime,
                processor.timeSlice, 
                processor.contextSwitchValue, 
                timeSlices));
        
        //System.out.println(stats);
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