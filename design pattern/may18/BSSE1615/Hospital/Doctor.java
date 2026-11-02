package Hospital;

public class Doctor implements Runnable {

    private String name;
    private int id;
    private String patientName;

    public Doctor(String name, int id, String patientName) {
        this.name = name;
        this.id = id;
        this.patientName = patientName;
    }

    public void updateDiagnosis(String diagnosis) {

        AuthenticationManager auth = AuthenticationManager.getInstance();

        if (!auth.validateUser(name, "Doctor")) {
            return;
        }

        HospitalDatabaseManager db = HospitalDatabaseManager.getInstance();
        db.connect();
        db.updatePatientRecord(patientName, "Diagnosis: " + diagnosis);

        HospitalLogger logger = HospitalLogger.getInstance();
        logger.logActivity("Doctor " + name + " with ID " + id + " updated diagnosis for patient: " + patientName);

        db.closeConnection();
    }

    public void createPrescription(String medicine) {

        AuthenticationManager auth = AuthenticationManager.getInstance();

        if (!auth.validateUser(name, "Doctor")) {
            return;
        }

        HospitalDatabaseManager db = HospitalDatabaseManager.getInstance();
        db.connect();
        db.executeQuery("INSERT INTO prescriptions WHERE patient = " + patientName +
                " AND medicine = " + medicine);

        HospitalLogger logger = HospitalLogger.getInstance();
        logger.logActivity("Doctor " + name + " with ID " + id + " created prescription for patient: " + patientName + " | Medicine: " + medicine);

        db.closeConnection();
    }

    @Override
    public void run() {

        updateDiagnosis("Chronic Hypertension");

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        createPrescription("Amlodipine 5mg");
    }
}