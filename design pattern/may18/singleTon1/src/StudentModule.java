public class StudentModule {

    private String name;
    private UnsafeAttendanceLogger unsafeLogger;
    private SafeAttendanceLogger safeLogger;

    public StudentModule(String name) {
        this.name = name;
        this.unsafeLogger = UnsafeAttendanceLogger.getInstance();
        this.safeLogger = SafeAttendanceLogger.getInstance();
    }

    public void submitUnsafe() {
        unsafeLogger.giveAttendance(name);
        unsafeLogger.logActivity(name + " submitted attendance");
    }

    public void submitSafe() {
        safeLogger.giveAttendance(name);
        safeLogger.logActivity(name + " submitted attendance");
    }
}