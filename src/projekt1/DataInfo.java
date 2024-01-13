package projekt1;

import java.io.IOException;
import projekt1.filecontent.FileContent;
import projekt1.iohelper.IOHelper;
import projekt1.logger.Logger;

public class DataInfo {

    public static void main(String[] args) throws IOException {
        Logger logger = new Logger();
        
        processOneFile("src/data_a1.txt", "src/error_log_a", "Assignment 1.1 + 1.4", logger);
        processOneFile("src/data_b1.txt", "src/error_log_b", "Assignment 1.2 + 1.4", logger);
        processOneFile("src/data_c1.txt", "src/error_log_c", "Assignment 1.3 + 1.4", logger);
        
    }

    static void processOneFile(String filePath, String logFilename, String title, Logger logger) throws IOException {
        logger.clear();
        FileContent fContent = IOHelper.readFile(filePath, logger);
        System.out.println(IOHelper.getOutputInfo(fContent, title));
        logger.dumpToLogFile(logFilename);
    }

}
