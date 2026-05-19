import java.util.ArrayList;

class UnsafeAttendanceLogger {

    private static UnsafeAttendanceLogger instance;

    private ArrayList<String> attendance = new ArrayList<>();
    private ArrayList<String> logFilePath = new ArrayList<>();
    private ArrayList<String> currentSessionID = new ArrayList<>();
    private ArrayList<String> logLevel = new ArrayList<>();

    private UnsafeAttendanceLogger() {
        System.out.println("New unsafe instance created" + this);
    }

    public static UnsafeAttendanceLogger getInstance() {

        if (instance == null) {
            instance = new UnsafeAttendanceLogger();
        }

        return instance;
    }

    public void logActivity(String log) {
        System.out.println("LOG: " + log);
    }

    public void writeToFile() {
        System.out.println("Writing logs to file...");
    }

    public void generateTimeStamp() {
        System.out.println("Timestamp generated");
    }

    public void giveAttendance(String name) {
        attendance.add(name);
    }

    public void updateAttendance(String name) {
        attendance.remove(name);
    }
}



class SafeAttendanceLogger {

    private static volatile SafeAttendanceLogger instance;

    private ArrayList<String> attendance = new ArrayList<>();
    private ArrayList<String> logFilePath = new ArrayList<>();
    private ArrayList<String> currentSessionID = new ArrayList<>();
    private ArrayList<String> logLevel = new ArrayList<>();

    private SafeAttendanceLogger() {
        System.out.println("new Safe instance created: " + this);
    }

    public static SafeAttendanceLogger getInstance() {

        if (instance == null) {
            synchronized (SafeAttendanceLogger.class) {
                if (instance == null) {
                    instance = new SafeAttendanceLogger();
                }
            }
        }

        return instance;
    }

    public void logActivity(String log) {
        System.out.println("LOG: " + log);
    }

    public void writeToFile() {
        System.out.println("Writing logs to file...");
    }

    public void generateTimeStamp() {
        System.out.println("Timestamp generated");
    }

    public synchronized void giveAttendance(String name) {
        attendance.add(name);
    }

    public synchronized void updateAttendance(String name) {
        attendance.remove(name);
    }
}












