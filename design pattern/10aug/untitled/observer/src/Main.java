import java.util.ArrayList;
import java.util.List;


class Employee {
    String employeeId;
    String name;
    String designation;
    String department;
    String employeeType;

    double basicSalary;
    double contractAmount;
    double hourlyRate;
    int    hoursWorked;
    double stipend;

    public Employee(String employeeId, String name, String designation,
                    String department, String employeeType) {
        this.employeeId   = employeeId;
        this.name         = name;
        this.designation  = designation;
        this.department   = department;
        this.employeeType = employeeType;
    }
}


class Payslip {
    Employee employee;
    double basicSalary;
    double allowances;
    double deductions;
    double tax;
    double netSalary;

    public Payslip(Employee employee) {
        this.employee = employee;
    }

    public void print() {
        System.out.println("---- PAYSLIP ----");
        System.out.println("Name        : " + employee.name);
        System.out.println("Type        : " + employee.employeeType);
        System.out.println("Basic       : " + basicSalary);
        System.out.println("Allowances  : " + allowances);
        System.out.println("Deductions  : " + deductions);
        System.out.println("Tax         : " + tax);
        System.out.println("Net Salary  : " + netSalary);
        System.out.println("-----------------\n");
    }
}


abstract class EmployeeSalaryCalculator {

    protected Employee employee;
    protected Payslip  payslip;

    protected double basicSalary;
    protected double allowances;
    protected double deductions;
    protected double tax;

    public EmployeeSalaryCalculator(Employee employee) {
        this.employee = employee;
        this.payslip  = new Payslip(employee);
    }

    public final void calculateSalary() {
        loadEmployeeInfo();
        computeBasicSalary();
        computeAllowances();
        computeDeductions();
        computeTax();
        generatePayslip();
    }

    private void loadEmployeeInfo() {
        System.out.println("Loading info for: " + employee.name + " [" + employee.employeeType + "]");
    }

    private void generatePayslip() {
        payslip.basicSalary = basicSalary;
        payslip.allowances  = allowances;
        payslip.deductions  = deductions;
        payslip.tax         = tax;
        payslip.netSalary   = basicSalary + allowances - deductions - tax;
        payslip.print();
    }

    protected abstract void computeBasicSalary();
    protected abstract void computeAllowances();
    protected abstract void computeDeductions();
    protected abstract void computeTax();
}


class PermanentEmployeeSalaryCalculator extends EmployeeSalaryCalculator {

    public PermanentEmployeeSalaryCalculator(Employee e) { super(e); }

    @Override
    protected void computeBasicSalary() {
        basicSalary = employee.basicSalary;
    }

    @Override
    protected void computeAllowances() {
        allowances = basicSalary * 0.40 + basicSalary * 0.10 + basicSalary * 0.05;
    }

    @Override
    protected void computeDeductions() {
        deductions = basicSalary * 0.10;
    }

    @Override
    protected void computeTax() {
        tax = (basicSalary + allowances) * 0.15;
    }
}


class ContractEmployeeSalaryCalculator extends EmployeeSalaryCalculator {

    public ContractEmployeeSalaryCalculator(Employee e) { super(e); }

    @Override
    protected void computeBasicSalary() {
        basicSalary = employee.contractAmount;
    }

    @Override
    protected void computeAllowances() {
        allowances = 300;
    }

    @Override
    protected void computeDeductions() {
        deductions = 100;
    }

    @Override
    protected void computeTax() {
        tax = basicSalary * 0.10;
    }
}


class HourlyEmployeeSalaryCalculator extends EmployeeSalaryCalculator {

    public HourlyEmployeeSalaryCalculator(Employee e) { super(e); }

    @Override
    protected void computeBasicSalary() {
        basicSalary = employee.hoursWorked * employee.hourlyRate;
    }

    @Override
    protected void computeAllowances() {
        allowances = employee.hoursWorked > 160
                ? (employee.hoursWorked - 160) * employee.hourlyRate * 0.5
                : 0;
    }

    @Override
    protected void computeDeductions() {
        deductions = 50;
    }

    @Override
    protected void computeTax() {
        tax = basicSalary * 0.08;
    }
}


class InternEmployeeSalaryCalculator extends EmployeeSalaryCalculator {

    public InternEmployeeSalaryCalculator(Employee e) { super(e); }

    @Override
    protected void computeBasicSalary() {
        basicSalary = employee.stipend;
    }

    @Override
    protected void computeAllowances() {
        allowances = 50;
    }

    @Override
    protected void computeDeductions() {
        deductions = 0;
    }

    @Override
    protected void computeTax() {
        tax = 0;
    }
}


public class Main {

    public static void main(String[] args) {

        Employee e1 = new Employee("E01", "Afif",  "Senior Engineer", "R&D",        "Permanent");
        e1.basicSalary = 4000;

        Employee e2 = new Employee("E02", "Hasan", "UI Designer",     "Product",    "Contract");
        e2.contractAmount = 2500;

        Employee e3 = new Employee("E03", "Karim", "Lab Technician",  "Operations", "Hourly");
        e3.hourlyRate  = 15;
        e3.hoursWorked = 180;

        Employee e4 = new Employee("E04", "Sadia", "Intern Developer","Engineering","Intern");
        e4.stipend = 500;

        System.out.println("=== PERMANENT EMPLOYEE ===");
        new PermanentEmployeeSalaryCalculator(e1).calculateSalary();

        System.out.println("=== CONTRACT EMPLOYEE ===");
        new ContractEmployeeSalaryCalculator(e2).calculateSalary();

        System.out.println("=== HOURLY EMPLOYEE ===");
        new HourlyEmployeeSalaryCalculator(e3).calculateSalary();

        System.out.println("=== INTERN EMPLOYEE [extensibility demo] ===");
        new InternEmployeeSalaryCalculator(e4).calculateSalary();
    }
}