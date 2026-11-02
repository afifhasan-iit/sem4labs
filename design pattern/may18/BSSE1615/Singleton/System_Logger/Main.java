package Singleton.System_Logger;

public class Main {

    public static void main(String[] args) {

        for (int i = 1629; i <= 1656; i++) {

            StudentModule student = new StudentModule("Student-" + i, i);

            Thread thread = new Thread(student);

            thread.start();
        }
    }
}
