// Scenario:
// The `OrderHelper` class accesses and modifies internal fields of the `Order` class directly.
// This makes `OrderHelper` too familiar with the internals of `Order`, violating encapsulation.
//
// Task for Students:
// Refactor the code to reduce coupling by using public methods in `Order` instead of accessing fields directly.
// Apply **Encapsulate Field**, **Move Method**, or **Hide Delegate** as needed.

import java.util.ArrayList;
import java.util.List;

class Item {
    private String name;
    private double price;

    public Item(String name, double price) {
        this.name  = name;
        this.price = price;
    }

    public String getName()  { return name;  }
    public double getPrice() { return price; }
}

class Order {
    private List<Item> items    = new ArrayList<>();
    private double discount     = 0.0;

    public void addItem(Item item) { items.add(item); }

    public double calculateTotal() {
        double total = 0;
        for (Item item : items) {
            total += item.getPrice();
        }
        return total;
    }

    public void applyDiscount(double rate) {
        discount = calculateTotal() * rate;
    }

    public double getFinalTotal() {
        return calculateTotal() - discount;
    }

    public double getDiscount() { return discount; }
}


class OrderHelper {
    public void applyDiscount(Order order, double rate) {
        order.applyDiscount(rate);
    }
}

class Main {
    public static void main(String[] args) {
        Order order = new Order();
        order.addItem(new Item("Laptop",   1000));
        order.addItem(new Item("Mouse",      50));
        order.addItem(new Item("Keyboard",   80));

        OrderHelper helper = new OrderHelper();
        helper.applyDiscount(order, 0.1); // 10% discount

        System.out.println("Total:    $" + order.calculateTotal());
        System.out.println("Discount: $" + order.getDiscount());
        System.out.println("Final:    $" + order.getFinalTotal());
    }
}