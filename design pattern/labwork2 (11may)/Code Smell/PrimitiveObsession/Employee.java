//Done


public class Employee {
    private String name;
    private Money salary;
    private HiringDetails hiringDetails;

    public Employee(String name, Money salary, HiringDetails hiringDetails) {
        this.name = name;
        this.salary = salary;
        this.hiringDetails = hiringDetails;
    }

    public String getName() {
        return name;
    }

    public Money getSalary() {
        return salary;
    }

    public HiringDetails getHiringDetails() {
        return hiringDetails;
    }

    public static void main(String[] args) {
        Money salary = new Money(500000, "BDT");
        HiringDetails hireDate = new HiringDetails(2021, 6, 15);
        Employee emp = new Employee("Rahim", salary, hireDate);

        System.out.println("Name: " + emp.getName());
        System.out.println("Salary: " + emp.getSalary().getAmount() + " " + emp.getSalary().getCurrency());
        System.out.println("Hired: " + emp.getHiringDetails().getDay() + "/" + emp.getHiringDetails().getMonth() + "/" + emp.getHiringDetails().getYear());
    }
}

class HiringDetails{
    private int year;
    private int month;
    private int day;

    public HiringDetails(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }
}

class Money {
    private int amount;       // in cents
    private String currency;

    public Money(int amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public int getAmount() { return amount; }
    public String getCurrency() { return currency; }
}