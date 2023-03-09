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
        //load library 
        Scheduler scheduler = new Scheduler(new File("./inputfile.txt"), new Processor(6, 5));     
        scheduler.setCSVFlag(true);
        scheduler.roundRobinExecute();
        writeToCSV(new File("./csv_export.csv"), scheduler.CSVOutputStream);
    }
}
