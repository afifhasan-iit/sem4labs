//Done

// Scenario:
// This `Customer` class only contains fields and getters/setters, but no behavior. It's just a passive data holder, 
// making it vulnerable to misuse. Business logic related to the customer ends up scattered elsewhere, 
// violating encapsulation.
//
// Task for Students:
// Refactor the class by moving related logic (e.g., full name formatting, validation) into the `Customer` class 
// so that it encapsulates its behavior.
// Apply the **Encapsulate Field** and **Move Method** refactoring techniques.

public class Customer {
    private String firstName;
    private String lastName;
    private String email;

    public Customer(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName  = lastName;
        setEmail(email);
    }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) {
        if (!isValidEmail(email)) {
            System.out.println("Invalid Email");
        }
        this.email = email;
    }



    public String getFullName() {
        return firstName + " " + lastName;
    }

    private boolean isValidEmail(String email) {
        return email != null && email.contains("@");
    }
}

class Main {
    public static void main(String[] args) {


        Customer customer = new Customer("Hasan", "Ali", "hasan@email.com");
        System.out.println("Full Name: " + customer.getFullName());
        System.out.println("Email: "     + customer.getEmail());


        System.out.println("\n Invalid Email Test ");
        Customer badCustomer = new Customer("afif", "hasan", "afifmail");
    }
}
