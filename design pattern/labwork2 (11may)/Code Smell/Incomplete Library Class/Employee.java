// Scenario:
// Java's `LocalDate` class doesn't have a method to calculate age directly from a birth date. 
// Since we can’t modify the library class, the logic ends up scattered throughout the code.
//
// Task for Students:
// Create a utility/helper class with a well-named method to encapsulate the missing functionality.
// Apply **Introduce Foreign Method** or **Introduce Extension Method** (in languages that support it).

import java.time.LocalDate;
import java.time.Period;



// Utility class — introduces foreign method to extend LocalDate behavior
class myExtention {
    public static int calculateAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}

class Employee {
    private String name;
    private LocalDate birthDate;

    public Employee(String name, LocalDate birthDate) {
        this.name      = name;
        this.birthDate = birthDate;
    }

    public String getName() { return name; }

    public int getAge() {
        return myExtention.calculateAge(birthDate); // delegates to utility
    }
}

class Main {
    public static void main(String[] args) {
        Employee emp1 = new Employee("Hasan", LocalDate.of(2000, 5, 10));
        Employee emp2 = new Employee("Sara",  LocalDate.of(1995, 3, 22));

        System.out.println(emp1.getName() + "'s age: " + emp1.getAge());
        System.out.println(emp2.getName() + "'s age: " + emp2.getAge());
    }
}