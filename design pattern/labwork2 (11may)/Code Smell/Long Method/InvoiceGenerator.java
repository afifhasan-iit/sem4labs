import java.util.ArrayList;
import java.util.List;

public class InvoiceGenerator {

    public void generateInvoice(Order order) {
        double total = calculateTotal(order);
        double discount = calculateDiscount(total);
        double tax = calculateTax(total);
        double finalAmount = total - discount + tax;
        printInvoice(order, total, discount, tax, finalAmount);
    }

    private double calculateTotal(Order order) {
        double total = 0;
        for (Item item : order.getItems()) {
            total += item.getPrice();
        }
        return total;
    }

    private double calculateDiscount(double total) {
        return total * 0.1;
    }

    private double calculateTax(double total) {
        return total * 0.2;
    }

    private void printInvoice(Order order, double total, double discount, double tax, double finalAmount) {
        System.out.println("Invoice for Order ID: " + order.getId());
        System.out.println("Items:");
        for (Item item : order.getItems()) {
            System.out.println(item.getName() + ": " + item.getPrice());
        }
        System.out.println("Total: " + total);
        System.out.println("Discount: " + discount);
        System.out.println("Tax: " + tax);
        System.out.println("Final Amount: " + finalAmount);
    }

    public static void main(String[] args) {
        Order order = new Order(101);
        order.addItem(new Item("Laptop", 50000));
        order.addItem(new Item("Mouse", 1200));
        order.addItem(new Item("Keyboard", 2500));

        InvoiceGenerator generator = new InvoiceGenerator();
        generator.generateInvoice(order);
    }
}

class Item {
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