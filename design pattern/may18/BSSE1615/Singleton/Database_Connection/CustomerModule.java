package Singleton.Database_Connection;

public class CustomerModule implements Runnable {

    private String name;
    private int id;
    private DatabaseConnectionManager db = DatabaseConnectionManager.getInstance();

    public CustomerModule(String name, int id) {
        this.name = name;
        this.id   = id;
    }

    @Override
    public void run() {
        System.out.println("[CustomerModule] " + name + " (ID: " + id + ") | Thread: " + Thread.currentThread().getName());
        db.connect();
        createCustomer(name, name.toLowerCase() + "@email.com");
        searchCustomer(name);
        db.disconnect();
    }

    public void createCustomer(String name, String email) {
        db.beginTransaction();
        db.executeQuery("INSERT INTO customers (name, email) VALUES ('" + name + "', '" + email + "')");
        db.commitTransaction();
        System.out.println("[CustomerModule] Customer created: " + name);
    }

    public void searchCustomer(String name) {
        db.executeQuery("SELECT * FROM customers WHERE name = '" + name + "'");
        System.out.println("[CustomerModule] Search completed for: " + name);
    }
}