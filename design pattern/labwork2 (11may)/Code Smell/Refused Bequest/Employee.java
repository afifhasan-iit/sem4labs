// Scenario:
// The `Employee` class is subclassed by `Manager` and `Developer`, but the `Manager` class doesn't need all 
// the functionalities provided by the `Employee` class. The `Manager` is forced to inherit functionality 
// it doesn't use, such as handling salary adjustments. This increases the complexity of the system,
// and potentially introduces bugs, as changes to `Employee` might affect `Manager` unexpectedly.
//
// Task for Students:
// Refactor the code to ensure that `Manager` and `Developer` do not inherit unused methods.
// Move any unnecessary functionality into separate classes that can be used by the specific subclasses
// using composition, instead of inheritance.
// Apply the **Replace Inheritance with Delegation** or **Extract Class** refactoring techniques.

// Extracted class — handles all salary logic
class SalaryManager {
    private double salary;

    public SalaryManager(double salary) {
        this.salary = salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void giveRaise(double raiseAmount) {
        this.salary += raiseAmount;
    }

    public double getSalary() {
        return salary;
    }
}


public class Employee {
    private String name;

    public Employee(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

// Manager does NOT need salary adjustment — so no SalaryManager here
class Manager extends Employee {
    private int bonus;

    public Manager(String name, int bonus) {
        super(name);
        this.bonus = bonus;
    }

    public int getBonus() {
        return bonus;
    }
}

class Developer extends Employee {
    private String programmingLanguage;
    private SalaryManager salaryManager; // HAS-A SalaryManager

    public Developer(String name, double salary, String programmingLanguage) {
        super(name);
        this.salaryManager = new SalaryManager(salary);
        this.programmingLanguage = programmingLanguage;
    }

    public String getProgrammingLanguage() {
        return programmingLanguage;
    }

    public void getRaise(double amount) {
        salaryManager.giveRaise(amount); // delegates to SalaryManager
    }

    public double getSalary() {
        return salaryManager.getSalary(); // delegates to SalaryManager
    }
}

// Main class to test everything
class Main {
    public static void main(String[] args) {


        Developer dev = new Developer("Alice", 50000, "Java");
        System.out.println("Developer: " + dev.getName());
        System.out.println("Language: " + dev.getProgrammingLanguage());
        System.out.println("Salary before raise: " + dev.getSalary());
        dev.getRaise(5000);
        System.out.println("Salary after raise: " + dev.getSalary());

        System.out.println();


        Manager mgr = new Manager("Bob", 10000);
        System.out.println("Manager: " + mgr.getName());
        System.out.println("Bonus: " + mgr.getBonus());

    }
}