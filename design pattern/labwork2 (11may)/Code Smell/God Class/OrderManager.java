//Done

// Scenario:
// The `OrderManager` class is responsible for too many tasks — from order creation to shipping, 
// payment processing, and email notification. If a bug occurs in the `createOrder()` method, 
// there’s a risk that it might inadvertently affect other methods like `processPayment()` or `shipOrder()`. 
// As the system grows, maintaining and testing this class will become increasingly difficult.
//
// Task for Students:
// Refactor the `OrderManager` class by extracting responsibility into separate classes 
// (e.g., `PaymentProcessor`, `ShippingService`, `EmailService`, etc.).
// Apply the Extract Class refactoring technique.

import java.util.ArrayList;
import java.util.List;

public class OrderManager {
    private List<Order> orders = new  ArrayList<>();
    private PaymentDetails paymentDetails;
    private Address shippingAddress;
    private List<Item> items = new ArrayList<>();

    OrderCreator oc = new OrderCreator();
    PaymentProcessor pp = new PaymentProcessor();
    OrderDeliver od = new OrderDeliver();
    ConfirmationMessage cm = new ConfirmationMessage();
    Invoice inv = new Invoice();


    public void processOrder(Order order) {
        oc.createOrder(orders,order);
        pp.processPayment(order);
        od.shipOrder(order);
        cm.sendConfirmationEmail(order);
        inv.generateInvoice(order);
    }
}


class OrderCreator{
    public void createOrder(List<Order> orders,Order order) {
        orders.add(order);
    }
}

class PaymentProcessor {
    public void processPayment(Order order) {
        // Payment processing logic
    }
}

class OrderDeliver{
    public void shipOrder(Order order) {
        // Shipping logic
    }
}

class ConfirmationMessage{
    public void sendConfirmationEmail(Order order) {
        // Email logic
    }
}

class Invoice{

    public double calculateInvoice(Order order) {
        double total = 0;
        for (Item item : order.getItems()) {
            total += item.getPrice();
        }
        return total;
    }

    public void generateInvoice(Order order) {
        double total = calculateInvoice(order);
        System.out.println("Total for Order: " + total);

    }
}

class Item{
    private String name;
    private double price;

    public Item(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
}

class Order {
    private int id;
    private List<Item> items = new ArrayList<>();

    public Order(int id) { this.id = id; }

    public int getId() { return id; }
    public List<Item> getItems() { return items; }

    public void addItem(Item item) { items.add(item); }
}

class PaymentDetails{

}

class Address{

}

class Main {
    public static void main(String[] args) {
        Order order = new Order(101);
        order.addItem(new Item("Laptop", 50000));
        order.addItem(new Item("Mouse", 1200));

        OrderManager manager = new OrderManager();
        manager.processOrder(order);
    }
}