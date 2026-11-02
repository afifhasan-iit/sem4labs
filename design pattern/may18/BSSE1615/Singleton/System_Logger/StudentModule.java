package Singleton.System_Logger;

public class StudentModule implements Runnable {

    private String name;
    private int id;

    public StudentModule(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public void login() {

        AttendanceLogger logger = AttendanceLogger.getInstance();

        logger.logActivity("Student " + name + " with ID " + id + " logged in");
    }

    public void submitAttendance() {

        AttendanceLogger logger = AttendanceLogger.getInstance();

        logger.logActivity("Student " + name + " with ID " + id + " submitted attendance");
    }

    @Override
    public void run() {

        login();

        try {
            Thread.sleep(500);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        submitAttendance();
    }
}
