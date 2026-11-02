package Hospital;

public class Receptionist {

    private String name;
    private int id;

    public Receptionist(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public void registerPatient(String patientName) {

        AuthenticationManager auth = AuthenticationManager.getInstance();

        if (!auth.validateUser(name, "Receptionist")) {
            return;
        }

        HospitalDatabaseManager db = HospitalDatabaseManager.getInstance();
        db.connect();
        db.executeQuery("INSERT INTO patients WHERE name = " + patientName);

        HospitalLogger logger = HospitalLogger.getInstance();
        logger.logActivity("Receptionist " + name + " with ID " + id + " registered patient: " + patientName);

        db.closeConnection();
    }

    public void assignRoom(String patientName, String roomNumber) {

        AuthenticationManager auth = AuthenticationManager.getInstance();

        if (!auth.validateUser(name, "Receptionist")) {
            return;
        }

        HospitalDatabaseManager db = HospitalDatabaseManager.getInstance();
        db.connect();
        db.updatePatientRecord(patientName, "Assigned Room: " + roomNumber);

        HospitalLogger logger = HospitalLogger.getInstance();
        logger.logActivity("Receptionist " + name + " with ID " + id + " assigned room " + roomNumber + " to patient: " + patientName);

        db.closeConnection();
    }
}