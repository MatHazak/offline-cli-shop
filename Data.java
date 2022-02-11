import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.TimeZone;

/**
 * Data center of shop. it reads from and writes to two .txt file; one as log to record important events
 * and another as database to store registered data.
 *
 * @author MatHazak
 */

public class Data {
    private static final String databasePath = "./Data/database.txt";
    private static final String logPath = "./Data/log.txt";
    private static final File database = new File(databasePath);
    private static final File log = new File(logPath);
    private static Scanner databaseScanner;
    private static Scanner logScanner;
    private static FileOutputStream logOutputStream;
    private static PrintStream databasePrintStream;
    private static PrintStream logPrintStream;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm z");
    
    
    public static void runDatabaseReader() throws IOException {
        if (! database.exists()) {
            database.getParentFile().mkdirs();
            database.createNewFile();
        }
        databaseScanner = new Scanner(database);
    }
    
    // creating out put stream when reading ended to avoid missing data (append is false).
    public static void runDatabaseWriter() throws IOException {
        FileOutputStream databaseOutputStream = new FileOutputStream(database, false);
        databasePrintStream = new PrintStream(databaseOutputStream);
    }
    
    public static void runLog() throws IOException {
        if (! log.exists()) {
            log.getParentFile().mkdirs();
            log.createNewFile();
        }
        setTimeZone();
        logScanner = new Scanner(log);
        logOutputStream = new FileOutputStream(log, true);
        logPrintStream = new PrintStream(logOutputStream);
    }
    
    // recreating scanner and print stream to store data that have been written during the execution.
    public static void refreshLog() throws FileNotFoundException {
        logScanner = new Scanner(log);
        logPrintStream = new PrintStream(logOutputStream);
    }
    
    public static void closeDatabase() {
        databaseScanner.close();
        databasePrintStream.flush();
        databasePrintStream.close();
    }
    
    public static void closeLog() {
        logScanner.close();
        logPrintStream.flush();
        logPrintStream.close();
    }
    
    public static void writeDataBase(String input) {
        databasePrintStream.println(input);
    }
    
    public static void writeDataBase(int input) {
        databasePrintStream.println(input);
    }
    
    public static boolean nonEmptyDatabase() {
        return databaseScanner.hasNext();
    }
    
    public static String readDataBase() {
        return databaseScanner.nextLine();
    }
    
    public static void writeLog(String input) {
        String time = dateFormat.format(new Date());
        input = "<" + time + "> " + input;
        logPrintStream.println(input);
    }
    
    public static void readLog() {
        while(logScanner.hasNext()) {
            System.out.println(logScanner.nextLine());
        }
    }
    
    // UTC is my preferred time zone to use; because of privacy improvement :)
    public static void setTimeZone() {
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }
}