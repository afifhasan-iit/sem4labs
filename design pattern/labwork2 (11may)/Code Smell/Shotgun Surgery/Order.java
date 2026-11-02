// Scenario:
// The `Order` class is responsible for several operations, including validating the order, processing payment, 
// and handling shipping. If you need to add a new feature to any of these operations (for example, updating payment 
// processing), you will have to touch multiple parts of the code in several different classes. This shotgun approach 
// increases the risk of errors and makes it harder to maintain and extend the system.
//
// Task for Students:
// Refactor the code by moving each responsibility into its own class. Create classes like `OrderValidator`, `PaymentProcessor`, 
// and `ShippingHandler`, and delegate responsibility for these operations to those classes. 
// Apply the **Extract Class** and **Move Method** refactoring techniques.

import java.util.List;

class Item {
    private String itemName;

    public Item(String itemName) {
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }
}

class OrderValidator {
    public void validate() {
        // Order validation logic
        System.out.println("Validating order...");
    }
}

class PaymentProcessor {
    public void process() {
        // Payment processing logic
        System.out.println("Processing payment...");
    }
}

class ShippingHandler {
    public void ship() {
        // Shipping logic
        System.out.println("Shipping order...");
    }
}

public class Order {
    private String customerName;
    private List<Item> items;

    private OrderValidator validator       = new OrderValidator();
    private PaymentProcessor paymentProc   = new PaymentProcessor();
    private ShippingHandler shippingHandler = new ShippingHandler();

    public Order(String customerName, List<Item> items) {
        this.customerName = customerName;
        this.items = items;
    }

    public void validateOrder() {
        // Order validation logic
        validator.validate();
    }

    public void processPayment() {
        // Payment processing logic
        paymentProc.process();
    }

    public void shipOrder() {
        // Shipping logic
        shippingHandler.ship();
    }
}

class Main {
    public static void main(String[] args) {

        List<Item> items = List.of(
                new Item("Laptop"),
                new Item("Mouse")
        );

        Order order = new Order("Hasan", items);

        System.out.println("Order placed by: Hasan");
        System.out.println("Items: Laptop, Mouse");
        System.out.println("--------------------");
        order.validateOrder();
        order.processPayment();
        order.shipOrder();
    }
}