package Singleton.Database_Connection;

public class Main {

    public static void main(String[] args) {

        System.out.println("========== BOOKSTORE SYSTEM STARTING ==========\n");

        String[] modules = {"CustomerModule", "OrderModule", "InventoryModule", "PaymentModule"};

        for (int i = 1; i <= 28; i++) {

            Runnable module;

            // Distribute threads across the 4 modules
            switch (i % 4) {
                case 1:  module = new CustomerModule("Customer-"  + i, i); break;
                case 2:  module = new OrderModule("Order-"        + i, i); break;
                case 3:  module = new InventoryModule("Inventory-" + i, i); break;
                default: module = new PaymentModule("Payment-"    + i, i); break;
            }

            Thread thread = new Thread(module);
            thread.start();
        }

        // Singleton verification after all threads launch
        System.out.println("\n--- Singleton Verification ---");
        DatabaseConnectionManager a = DatabaseConnectionManager.getInstance();
        DatabaseConnectionManager b = DatabaseConnectionManager.getInstance();
        System.out.println("Same instance? " + (a == b));
    }
}