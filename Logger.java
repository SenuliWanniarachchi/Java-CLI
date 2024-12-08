import java.io.FileWriter;
import java.io.IOException;

public class Logger {
    private final String logFileName;

    public Logger(String logFileName) {
        this.logFileName = logFileName;
    }

    public void log(String message) {
        try (FileWriter writer = new FileWriter(logFileName, true)) {
            writer.write(message + "\n");
        } catch (IOException e) {
            System.out.println("Error writing to log file: " + e.getMessage());
        }
    }
}
