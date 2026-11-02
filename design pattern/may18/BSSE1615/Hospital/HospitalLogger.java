package Hospital;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public final class HospitalLogger {

    private static volatile HospitalLogger hospitalLogger;

    private String logFilePath;
    private int currentSessionId;

    private HospitalLogger() {
        logFilePath = "HospitalLog.txt";
        currentSessionId = 1;
    }

    public static HospitalLogger getInstance() {

        if (hospitalLogger == null) {

            synchronized (HospitalLogger.class) {

                if (hospitalLogger == null) {
                    hospitalLogger = new HospitalLogger();
                }
            }
        }

        return hospitalLogger;
    }

    public void logActivity(String message) {

        String timeStamp = generateTimestamp();

        String fullMessage = "Session " + currentSessionId + " | " + message + " | Time: " + timeStamp;

        System.out.println(fullMessage);

        synchronized (this) {
            writeToFile(fullMessage);
        }

        currentSessionId++;
    }

    private void writeToFile(String message) {

        try (PrintWriter printer = new PrintWriter(new FileWriter(logFilePath, true))) {

            printer.println(message);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String generateTimestamp() {
        return LocalDateTime.now().toString();
    }
}