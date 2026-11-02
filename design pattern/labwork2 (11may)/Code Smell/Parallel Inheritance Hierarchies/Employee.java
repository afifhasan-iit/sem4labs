// Scenario:
// You have two inheritance hierarchies: one for `Employee` and another for `Contractor`. Each hierarchy has 
// its own specific classes for different roles (e.g., `Manager`, `Developer`, etc.). However, the hierarchies are 
// completely parallel, which causes duplication and makes the system harder to extend. 
// If you need to add a new common method to both `Employee` and `Contractor` classes, you'll need to modify both 
// hierarchies, leading to redundant changes and inconsistencies.
//
// Task for Students:
// Refactor the code by extracting common behavior between `Employee` and `Contractor` into a shared superclass, 
// or use interfaces to ensure that common functionality is shared between the two hierarchies. 
// Apply the **Consolidate Inheritance Hierarchies** refactoring technique.


class Worker {
    private String name;
    private int salary;
    public void raiseSalary(int raiseAmount) {
        salary += raiseAmount;
    }
}

interface bonusPower{
    public void assignBonus(int bonusAmount);
}

public class Employee extends Worker {

}

 class Manager extends Employee implements bonusPower {
    private int bonus;

    public void assignBonus(int bonusAmount) {
        bonus = bonusAmount;
    }
}

 class Contractor extends Worker {

}

 class ContractorManager extends Contractor implements  bonusPower {
    private int bonus;

    public void assignBonus(int bonusAmount) {
        bonus = bonusAmount;
    }
}

class Main {
    public static void main(String[] args) {

        // --- Employee ---
        Employee emp = new Employee();
        emp.raiseSalary(1000);
        System.out.println("Employee salary raised by 1000");

        // --- Manager (Employee + BonusPower) ---
        Manager mgr = new Manager();
        mgr.raiseSalary(2000);
        mgr.assignBonus(500);
        System.out.println("Manager salary raised by 2000 and bonus assigned: 500");

        // --- Contractor ---
        Contractor con = new Contractor();
        con.raiseSalary(1500);
        System.out.println("Contractor salary raised by 1500");

        // --- ContractorManager (Contractor + BonusPower) ---
        ContractorManager conMgr = new ContractorManager();
        conMgr.raiseSalary(1800);
        conMgr.assignBonus(300);
        System.out.println("ContractorManager salary raised by 1800 and bonus assigned: 300");
    }
}