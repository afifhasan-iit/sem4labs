//Done
//
// Scenario:
// The `InvoicePrinter` class accesses many details of the `Invoice` class (e.g., lineItems, totals) and performs logic
// that arguably belongs to `Invoice`. This violates encapsulation and creates tight coupling.
// 
// Task for Students:
// Refactor the code by moving relevant logic (like total and tax calculation) into the `Invoice` class.
// Apply the **Move Method** refactoring technique.

import java.util.ArrayList;
import java.util.List;

class LineItem {
    private String name;
    private double price;

    public LineItem(String name, double price) {
        this.name  = name;
        this.price = price;
    }

    public String getName()  { return name;  }
    public double getPrice() { return price; }
}

class Invoice {
    private List<LineItem> items = new ArrayList<>();

    public void addItem(LineItem item) { items.add(item); }
    public List<LineItem> getItems()   { return items;    }


    public double calculateSubtotal() {
        double subtotal = 0;
        for (LineItem item : items) {
            subtotal += item.getPrice();
        }
        return subtotal;
    }

    public double calculateTax() {
        return calculateSubtotal() * 0.2;
    }

    public double calculateTotal() {
        return calculateSubtotal() + calculateTax();
    }
}

class InvoicePrinter {
    public void printInvoice(Invoice invoice) {
        System.out.println("Subtotal: " + invoice.calculateSubtotal());
        System.out.println("Tax: "      + invoice.calculateTax());
        System.out.println("Total: "    + invoice.calculateTotal());
    }
}

class Main {
    public static void main(String[] args) {
        Invoice invoice = new Invoice();
        invoice.addItem(new LineItem("Laptop",   1000));
        invoice.addItem(new LineItem("Mouse",      50));
        invoice.addItem(new LineItem("Keyboard",   80));

        InvoicePrinter printer = new InvoicePrinter();
        printer.printInvoice(invoice);
    }
}
