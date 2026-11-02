package Singleton.System_Logger;

public class TeacherModule {

    private String teacherName;

    public TeacherModule(String teacherName) {
        this.teacherName = teacherName;
    }

    public void updateAttendance() {

        AttendanceLogger logger =
                AttendanceLogger.getInstance();

        logger.logActivity(
                "Teacher " + teacherName +
                " updated attendance");
    }

    public void approveRequest() {

        AttendanceLogger logger =
                AttendanceLogger.getInstance();

        logger.logActivity(
                "Teacher " + teacherName +
                " approved attendance request");
    }
}