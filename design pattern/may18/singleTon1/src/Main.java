

public class Main {

    public static void main(String[] args) throws Exception {

        System.out.println("##-- UNSAFE SINGLETON TEST --##");

        Runnable unsafeTask1 = () -> {
            StudentModule s1 = new StudentModule("Afif");
            s1.submitUnsafe();

            UnsafeAttendanceLogger logger = UnsafeAttendanceLogger.getInstance();

            System.out.println(Thread.currentThread().getName()
                    + " -> " + logger);
        };

        Runnable unsafeTask2 = () -> {
            TeacherModule t1 = new TeacherModule("MNF");
            t1.updateUnsafe();

            UnsafeAttendanceLogger logger = UnsafeAttendanceLogger.getInstance();

            System.out.println(Thread.currentThread().getName()
                    + " -> " + logger);
        };

        Thread t1 = new Thread(unsafeTask1);
        Thread t2 = new Thread(unsafeTask2);

        t1.start();
        t2.start();

        t1.join();
        t2.join();


        System.out.println("\n##-- SAFE SINGLETON TEST --##");

        Runnable safeTask1 = () -> {
            StudentModule s2 = new StudentModule("Hasan");
            s2.submitSafe();

            SafeAttendanceLogger logger =
                    SafeAttendanceLogger.getInstance();

            System.out.println(Thread.currentThread().getName()
                    + " -> " + logger);
        };

        Runnable safeTask2 = () -> {
            TeacherModule t2Teacher = new TeacherModule("Karim");
            t2Teacher.updateSafe();

            SafeAttendanceLogger logger =
                    SafeAttendanceLogger.getInstance();

            System.out.println(Thread.currentThread().getName()
                    + " -> " + logger);
        };

        Thread t3 = new Thread(safeTask1);
        Thread t4 = new Thread(safeTask2);

        t3.start();
        t4.start();

        t3.join();
        t4.join();


    }
}
