//Done
//
// Scenario:
// The `CustomerManager` class just delegates every method to the `Customer` object without adding logic.
// This is unnecessary indirection and makes the design more complex.
//
// Task for Students:
// Refactor the code to remove the middle man. Use the `Customer` object directly where needed.
// Apply **Remove Middle Man** refactoring.


class Customer {
    private String name;
    private String email;

    public Customer(String name, String email) {
        this.name  = name;
        this.email = email;
    }

    public String getName()  { return name;  }
    public String getEmail() { return email; }
}



class Main {
    public static void main(String[] args) {

        Customer customer = new Customer("Hasan", "hasan@email.com");
        System.out.println("Name:  " + customer.getName());
        System.out.println("Email: " + customer.getEmail());
    }
}
