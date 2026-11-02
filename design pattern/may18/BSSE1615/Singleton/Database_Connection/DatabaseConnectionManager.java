package Singleton.Database_Connection;

public class DatabaseConnectionManager {

    private String connectionStatus;
    private String databaseURL;
    private String username;
    private String password;
    private int activeConnectionCount;

    private static DatabaseConnectionManager instance;

    private DatabaseConnectionManager() {
        this.databaseURL          = "jdbc:mysql://localhost:3306/bookstore";
        this.username             = "admin";
        this.password             = "password";
        this.connectionStatus     = "Disconnected";
        this.activeConnectionCount = 0;
    }

    public static DatabaseConnectionManager getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnectionManager.class) {
                if (instance == null) {
                    instance = new DatabaseConnectionManager();
                }
            }
        }
        return instance;
    }

    public void connect() {
        if (connectionStatus.equals("Connected")) {
            System.out.println("[DB] Already connected. Active connections: " + activeConnectionCount);
            return;
        }
        connectionStatus = "Connected";
        activeConnectionCount++;
        System.out.println("[DB] Connected to " + databaseURL + " | Active connections: " + activeConnectionCount);
    }

    public void disconnect() {
        if (activeConnectionCount > 0) activeConnectionCount--;
        if (activeConnectionCount == 0) connectionStatus = "Disconnected";
        System.out.println("[DB] Disconnected. Active connections: " + activeConnectionCount);
    }

    public void executeQuery(String query) {
        if (!connectionStatus.equals("Connected")) {
            System.out.println("[DB] ERROR: Cannot execute query — not connected.");
            return;
        }
        System.out.println("[DB] Executing query: " + query);
    }

    public void beginTransaction() {
        System.out.println("[DB] Transaction started.");
    }

    public void commitTransaction() {
        System.out.println("[DB] Transaction committed.");
    }

    public void rollbackTransaction() {
        System.out.println("[DB] Transaction rolled back.");
    }

    public void closeConnection() {
        connectionStatus     = "Disconnected";
        activeConnectionCount = 0;
        System.out.println("[DB] All connections closed.");
    }

    public String getConnectionStatus()   { return connectionStatus; }
    public int getActiveConnectionCount() { return activeConnectionCount; }
}