package Hospital;

public final class HospitalDatabaseManager {

    private static volatile HospitalDatabaseManager hospitalDatabaseManager;

    private String databaseConnection;
    private String connectionStatus;
    private String serverAddress;
    private int activeUserCount;

    private HospitalDatabaseManager() {
        databaseConnection = "HospitalDB_Connection";
        connectionStatus = "Disconnected";
        serverAddress = "192.168.1.100";
        activeUserCount = 0;
    }

    public static HospitalDatabaseManager getInstance() {

        if (hospitalDatabaseManager == null) {

            synchronized (HospitalDatabaseManager.class) {

                if (hospitalDatabaseManager == null) {
                    hospitalDatabaseManager = new HospitalDatabaseManager();
                }
            }
        }

        return hospitalDatabaseManager;
    }

    public void connect() {

        synchronized (this) {
            connectionStatus = "Connected";
            activeUserCount++;
        }

        System.out.println("Database connected | Server: " + serverAddress + " | Active Users: " + activeUserCount);
    }

    public void executeQuery(String query) {

        System.out.println("Executing query: " + query + " | Status: " + connectionStatus);
    }

    public void updatePatientRecord(String patientName, String details) {

        System.out.println("Updating record for patient: " + patientName +
                " | Details: " + details);
    }

    public void closeConnection() {

        synchronized (this) {
            activeUserCount--;
            if (activeUserCount == 0) {
                connectionStatus = "Disconnected";
            }
        }

        System.out.println("Connection closed | Active Users Remaining: " + activeUserCount);
    }
}