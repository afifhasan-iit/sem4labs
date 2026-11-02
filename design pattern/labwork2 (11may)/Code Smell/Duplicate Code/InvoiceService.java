//DONE

// Scenario:
// The logic for calculating and printing invoice totals is duplicated across two classes (`InvoiceService` and `ReceiptService`). 
// If you need to change the tax rate or formatting, you'll need to update it in both places, risking inconsistencies.
//
// Task for Students:
// Refactor the code to extract the duplicated logic into a shared method or utility class.
// Apply Extract Method and Pull Up Method (or use composition via a helper class) to eliminate redundancy.

import java.util.ArrayList;
import java.util.List;

public class InvoiceService {
    public void printInvoice(Order order) {

        double total = calculation.calculateTotal(order);
        System.out.println("Invoice Total: " + total);
    }
}

 class ReceiptService {
    public void printReceipt(Order order) {
        double total = calculation.calculateTotal(order);
        System.out.println("Receipt Total: " + (total));
    }
}

class calculation{
    public static double calculateTotal(Order order){
        double total = 0;
        for (Item item : order.getItems()) {
            total += item.getPrice();
        }
        double tax = total * 0.2;
        return  total + tax;
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

class Main {
    public static void main(String[] args) {
        Order order = new Order(1);
        order.addItem(new Item("Laptop", 1000));
        order.addItem(new Item("Mouse", 50));

        InvoiceService invoice = new InvoiceService();
        ReceiptService receipt = new ReceiptService();

        invoice.printInvoice(order);
        receipt.printReceipt(order);
    }
}