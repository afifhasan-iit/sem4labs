// Scenario:
// This class uses comments to explain complex logic instead of writing self-explanatory code. 
// Overreliance on comments may indicate unclear code structure or poor naming. When code is changed, 
// comments may become outdated and misleading.
//
// Task for Students:
// Refactor the code by replacing comments with clearer method names and logic. Use Extract Method and Rename Method 
// to make the code more self-documenting so that the comments become unnecessary.


import java.util.List;
import java.util.ArrayList;

class Item {
    private String name;
    private double price;

    public Item(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public double getPrice() { return price; }
    public String getName()  { return name;  }
}

class Order {
    private List<Item> items = new ArrayList<>();

    public void addItem(Item item) {
        items.add(item);
    }

    public List<Item> getItems() { return items; }
}

class OrderProcessor {

    public void processOrder(Order order) {
        if (isOrderEmpty(order)) {
            System.out.println("Order is empty");
            return;
        }

        double total        = calculateTotalPrice(order);
        double totalWithTax = applyTax(total);

        printTotal(totalWithTax);
    }

    private boolean isOrderEmpty(Order order) {
        return order.getItems().size() == 0;
    }

    private double calculateTotalPrice(Order order) {
        double total = 0;
        for (Item item : order.getItems()) {
            total += item.getPrice();
        }
        return total;
    }

    private double applyTax(double total) {
        double tax = total * 0.2;
        return total + tax;
    }

    private void printTotal(double totalWithTax) {
        System.out.println("Total with tax: " + totalWithTax);
    }
}

class Main {
    public static void main(String[] args) {

        OrderProcessor processor = new OrderProcessor();

        // --- Empty order test ---
        Order emptyOrder = new Order();
        System.out.println(" Empty Order");
        processor.processOrder(emptyOrder);

        // --- Normal order test ---
        Order order = new Order();
        order.addItem(new Item("Laptop", 1000));
        order.addItem(new Item("Mouse",    50));
        order.addItem(new Item("Keyboard", 80));
        System.out.println(" Normal Order");
        processor.processOrder(order);
    }
}