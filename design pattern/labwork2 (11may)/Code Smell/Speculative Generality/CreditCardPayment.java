//DONE

// Scenario:
// The system includes an abstract base class and an interface (`PaymentProcessor`) even though there's only one implementation. 
// This generalization was added "just in case" — but it adds unnecessary complexity and indirection without benefit.
//
// Task for Students:
// Refactor the code to remove unnecessary abstraction until it's truly needed. 
// Replace abstract classes and interfaces with concrete implementations.
// Apply the **Collapse Hierarchy** or **Inline Class** refactoring technique.

public class CreditCardPayment {

    public void process(double amount) {
        System.out.println("Processing credit card payment of $" + amount);
    }
}

class Main {
    public static void main(String[] args) {
        CreditCardPayment payment = new CreditCardPayment();
        payment.process(150.00);
    }
}