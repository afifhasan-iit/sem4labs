package Singleton.Database_Connection;

public class InventoryModule implements Runnable {

    private String name;
    private int id;
    private DatabaseConnectionManager db = DatabaseConnectionManager.getInstance();

    public InventoryModule(String name, int id) {
        this.name = name;
        this.id   = id;
    }

    @Override
    public void run() {
        System.out.println("[InventoryModule] " + name + " (ID: " + id + ") | Thread: " + Thread.currentThread().getName());
        db.connect();
        addBook("Book-" + id, 10);
        checkStock("Book-" + id);
        db.disconnect();
    }

    public void addBook(String title, int quantity) {
        db.beginTransaction();
        db.executeQuery("INSERT INTO inventory (title, quantity) VALUES ('" + title + "', " + quantity + ")");
        db.commitTransaction();
        System.out.println("[InventoryModule] Added " + quantity + " copies of: " + title);
    }

    public void checkStock(String title) {
        db.executeQuery("SELECT quantity FROM inventory WHERE title = '" + title + "'");
        System.out.println("[InventoryModule] Stock checked for: " + title);
    }
}