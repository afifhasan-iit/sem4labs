public class AdminModule {

    private UnsafeAttendanceLogger unsafeLogger;
    private SafeAttendanceLogger safeLogger;

    public AdminModule() {
        this.unsafeLogger = UnsafeAttendanceLogger.getInstance();
        this.safeLogger = SafeAttendanceLogger.getInstance();
    }

    public void show() {
        System.out.println("Admin viewing system logs...");
    }
}