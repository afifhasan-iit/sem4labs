//DONE

// Scenario:
// The `Employee` class has a temporary field `tempSalary` that is only used for salary adjustments 
// during a specific operation. This temporary field causes the class to hold unnecessary state, 
// which makes the class harder to understand, and might introduce bugs if it is inadvertently accessed 
// in places where it shouldn’t be.
//
// Task for Students:
// Refactor the code to remove the temporary field. Instead, compute the value when needed, 
// rather than storing it in the object. 
// Apply the **Remove Temporary Field** refactoring technique.

public class Employee {
    private String name;
    private double salary;

    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    public double giveRaise(int raiseAmount) {
        return  salary + raiseAmount; // Temporary field used for salary adjustment
    }

    public void applyRaise(double tempSalary) {
        salary = tempSalary; // Applying the raise
    }
}

class Main {
    public static void main(String[] args) {
        Employee emp = new Employee("hasan", 5000);


        double currSalary = emp.giveRaise(100);
        emp.applyRaise(currSalary);
    }
}