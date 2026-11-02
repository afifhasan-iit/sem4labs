//Done
//
// Scenario:
// The code accesses multiple objects in a chain (e.g., `order.getCustomer().getAddress().getZipCode()`).
// This makes the code fragile — any change in the structure requires updates in many places.
//
// Task for Students:
// Refactor the code to hide the internal structure by adding delegation methods in the parent classes.
// Apply **Hide Delegate** or **Move Method** to simplify calls.

import java.util.ArrayList;
import java.util.List;

class Address {
    private String zipCode;

    public Address(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getZipCode() { return zipCode; }
}

class Customer {
    private Address address;

    public Customer(Address address) {
        this.address = address;
    }

    // Delegate ,hides Address from outside
    public String getZipCode() {
        return address.getZipCode();
    }
}

class Order {
    private Customer customer;

    public Order(Customer customer) {
        this.customer = customer;
    }

    // Delegate, hides Customer and Address from outside
    public String getZipCode() {
        return customer.getZipCode();
    }
}

class Main {
    public static void main(String[] args) {
        Address  address  = new Address("1234");
        Customer customer = new Customer(address);
        Order    order    = new Order(customer);

        // Before
        // String zip = order.getCustomer().getAddress().getZipCode();

        // After
        String zip = order.getZipCode();
        System.out.println("Zip Code: " + zip);
    }
}