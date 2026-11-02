// Scenario:
// The `Employee` class is undergoing changes for two distinct reasons: salary adjustments and tax calculations. 
// However, both of these changes are handled in the same class, which violates the Single Responsibility Principle. 
// As a result, if you need to add more logic related to either salary adjustments or tax calculations, 
// you will be forced to modify the `Employee` class, increasing the risk of introducing errors and making the system 
// harder to maintain in the long term.
//
// Task for Students:
// Refactor the `Employee` class by moving salary-related logic to a `SalaryManager` class and tax-related logic 
// to a `TaxCalculator` class. This will ensure that each class has one reason to change. 
// Apply the **Extract Class** and **Move Method** refactoring techniques.

public class Employee {
    private String name;
    private int salary;
    private double taxRate;

    public Employee(String name, int salary, double taxRate) {
        this.name    = name;
        this.salary  = salary;
        this.taxRate = taxRate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }
}

class SalaryManager {
    public static void applyRaise(Employee emp, int raiseAmount) {
        emp.setSalary(emp.getSalary() + raiseAmount);
    }
}

class TaxCalculator {
    public static double calculateTax(Employee emp) {
        return emp.getSalary() * emp.getTaxRate();
    }
}

class Main {
    public static void main(String[] args) {
        Employee emp = new Employee("Hasan", 10000, 0.20);

        System.out.println("Before raise : " + emp.getSalary());     // 10000
        SalaryManager.applyRaise(emp, 1000);
        System.out.println("After raise  : " + emp.getSalary());     // 11000

        double tax = TaxCalculator.calculateTax(emp);
        System.out.println("Tax owed     : " + tax);
    }
}