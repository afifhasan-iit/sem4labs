package Singleton.Database_Connection;

public class PaymentModule implements Runnable {

    private String name;
    private int id;
    private DatabaseConnectionManager db = DatabaseConnectionManager.getInstance();

    public PaymentModule(String name, int id) {
        this.name = name;
        this.id   = id;
    }

    @Override
    public void run() {
        System.out.println("[PaymentModule] " + name + " (ID: " + id + ") | Thread: " + Thread.currentThread().getName());
        db.connect();
        processPayment("ORD-" + id, 9.99 + id);
        refundPayment("ORD-" + (id - 1), 9.99);
        db.disconnect();
    }

    public void processPayment(String orderId, double amount) {
        db.beginTransaction();
        db.executeQuery("INSERT INTO payments (order_id, amount, status) VALUES ('" + orderId + "', " + amount + ", 'SUCCESS')");
        db.commitTransaction();
        System.out.println("[PaymentModule] Payment of $" + amount + " processed for: " + orderId);
    }

    public void refundPayment(String orderId, double amount) {
        db.beginTransaction();
        db.executeQuery("UPDATE payments SET status = 'REFUNDED' WHERE order_id = '" + orderId + "'");
        db.commitTransaction();
        System.out.println("[PaymentModule] Refund of $" + amount + " issued for: " + orderId);
    }
}