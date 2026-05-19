public class TeacherModule {

    private String name;
    private UnsafeAttendanceLogger unsafeLogger;
    private SafeAttendanceLogger safeLogger;

    public TeacherModule(String name) {
        this.name = name;
        this.unsafeLogger = UnsafeAttendanceLogger.getInstance();
        this.safeLogger = SafeAttendanceLogger.getInstance();
    }

    public void updateUnsafe() {
        unsafeLogger.updateAttendance("Updated by " + name);
        unsafeLogger.logActivity("Teacher updated attendance");
    }

    public void updateSafe() {
        safeLogger.updateAttendance("Updated by " + name);
        safeLogger.logActivity("Teacher updated attendance");
    }
}