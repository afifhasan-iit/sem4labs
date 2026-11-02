package Singleton.System_Logger;

public class AdminModule {

    public void generateReport() {

        AttendanceLogger logger =
                AttendanceLogger.getInstance();

        logger.logActivity(
                "Administrator generated attendance report");
    }
}