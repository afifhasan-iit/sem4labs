package Singleton.Database_Connection;

public class OrderModule implements Runnable {

    private String name;
    private int id;
    private DatabaseConnectionManager db = DatabaseConnectionManager.getInstance();

    public OrderModule(String name, int id) {
        this.name = name;
        this.id   = id;
    }

    @Override
    public void run() {
        System.out.println("[OrderModule] " + name + " (ID: " + id + ") | Thread: " + Thread.currentThread().getName());
        db.connect();
        createOrder("ORD-" + id, "Customer-" + id, "Book-" + id);
        updateOrderStatus("ORD-" + id, "Shipped");
        db.disconnect();
    }

    public void createOrder(String orderId, String customer, String book) {
        db.beginTransaction();
        db.executeQuery("INSERT INTO orders (id, customer, book) VALUES ('" + orderId + "', '" + customer + "', '" + book + "')");
        db.commitTransaction();
        System.out.println("[OrderModule] Order created: " + orderId + " for " + customer);
    }

    public void updateOrderStatus(String orderId, String status) {
        db.beginTransaction();
        db.executeQuery("UPDATE orders SET status = '" + status + "' WHERE id = '" + orderId + "'");
        db.commitTransaction();
        System.out.println("[OrderModule] Order " + orderId + " updated to: " + status);
    }
}