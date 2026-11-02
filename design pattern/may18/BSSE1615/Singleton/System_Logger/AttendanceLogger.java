package Singleton.System_Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public final class AttendanceLogger {

    private static volatile AttendanceLogger attendanceLogger;

    private String logFilePath;
    private int currentSessionId;

    private AttendanceLogger() {
        logFilePath = "Attendance.txt";
        currentSessionId = 1;
    }

    public static AttendanceLogger getInstance() {

        if (attendanceLogger == null) {

            synchronized (AttendanceLogger.class) {

                if (attendanceLogger == null) {
                    attendanceLogger = new AttendanceLogger();
                }
            }
        }

        return attendanceLogger;
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