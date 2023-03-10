import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;


public class Main {
      
    public static void writeToCSV (File CSVFile, ArrayList<String> toWrite) {
        try {
            PrintWriter pw = new PrintWriter(CSVFile);
            toWrite.forEach( (line) -> {
                pw.println(line.trim().concat(","));    
            });
            pw.close();
        } catch (Exception e ) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        Scheduler scheduler;
        int[] timeSlices = {1, 2, 3, 5, 10, 15, 25, 50};

        for (int timeSlice : timeSlices) {
            scheduler = new Scheduler(new File("./input/inputfile_unsorted.csv"), new Processor(timeSlice, timeSlice));     
            scheduler.setCSVFlag(true);
            scheduler.roundRobinExecute();
            writeToCSV(new File("./output/unsorted_" + timeSlice + ".csv"), scheduler.CSVOutputStream);
        }

        for (int timeSlice : timeSlices) {
            scheduler = new Scheduler(new File("./input/inputfile_sorted.csv"), new Processor(timeSlice, timeSlice));     
            scheduler.setCSVFlag(true);
            scheduler.roundRobinExecute();
            writeToCSV(new File("./output/sorted_" + timeSlice + ".csv"), scheduler.CSVOutputStream);
        }

    }
}