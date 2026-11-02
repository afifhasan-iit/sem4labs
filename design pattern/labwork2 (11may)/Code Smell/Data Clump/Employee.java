//Done

// Scenario:
// The `Employee` class holds several pieces of related data (name, address, phone number, department, salary), 
// and this data is passed around in various parts of the codebase. Whenever an employee is updated, 
// all of these pieces of data are passed together. If, in the future, the company needs to store a new set of 
// related information (e.g., `ContactInfo`), the existing structure will quickly become cumbersome and hard to maintain.
//
// Task for Students:
// Refactor the `Employee` class by extracting the data clumps into appropriate classes 
// (e.g., create a `ContactInfo` class for the address and phone number).
// Apply the Extract Class refactoring technique.

public class Employee {
    private String name;
    ContactInfo contactInfo;
    JobDetails jobDetails;


    public void updateEmployee(String name, ContactInfo contactInfo, JobDetails jobDetails) {

        this.name = name;
        this.contactInfo = contactInfo;
        this.jobDetails = jobDetails;

        System.out.println("update done");
    }

}


class ContactInfo{
    private String address;
    private String phoneNumber;

    public ContactInfo(String address, String phoneNumber) {
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

class JobDetails{
    private String department;
    private int salary;

    public JobDetails(String department, int salary) {
        this.department = department;
        this.salary = salary;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}

class Main {
    public static void main(String[] args) {
        ContactInfo contactInfo = new ContactInfo("Dhaka, Bangladesh", "01711111111");
        JobDetails jobDetails = new JobDetails("Engineering", 50000);
        Employee emp = new Employee();
        emp.updateEmployee("Rahim", contactInfo, jobDetails);
    }
}