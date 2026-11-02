package Hospital;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // we do sum threadsafing
        for (int i = 101; i <= 105; i++) {
            Doctor doctor = new Doctor("Doctor-" + i, i, "Patient-" + (i + 10));
            Thread thread = new Thread(doctor);
            thread.start();
        }

        Receptionist receptionist = new Receptionist("Mikhail", 201);
        receptionist.registerPatient("Toofan");
        receptionist.assignRoom("Toofan", "Room-304 ;-;");

        HospitalDatabaseManager db1 = HospitalDatabaseManager.getInstance();
        HospitalDatabaseManager db2 = HospitalDatabaseManager.getInstance();
        System.out.println("\nSame DB instance: " + (db1 == db2)); // return true (claude bolse)

        HospitalLogger log1 = HospitalLogger.getInstance();
        HospitalLogger log2 = HospitalLogger.getInstance();
        System.out.println("Same Logger instance: " + (log1 == log2));

        List<String> prescriptionSections = new ArrayList<>();
        prescriptionSections.add("Die");
        prescriptionSections.add("Die more");

        List<String> medicines = new ArrayList<>();
        medicines.add("Paracetamol 500mg");
        medicines.add("Cocaine 100mg");

        PrescriptionTemplate prescriptionPrototype = new PrescriptionTemplate(
                "Rahim",
                "Dr. Karim",
                "General",
                "01 Jan 2026",
                "Shorowardy",
                prescriptionSections,
                medicines
        );

        List<String> labSections = new ArrayList<>();
        labSections.add("Fasting required before test");

        List<String> testResults = new ArrayList<>();
        testResults.add("Blood Glucose: 95 mg/dL");
        testResults.add("Hemoglobin: 13.5 g/dL");

        LabReportTemplate labPrototype = new LabReportTemplate(
                "Template Patient",
                "Dr. Template",
                "Routine Checkup",
                "01 Jan 2026",
                "== City General Hospital ==",
                labSections,
                testResults
        );

        ReportGenerator generator = new ReportGenerator();

        MedicalReport johnPrescription = generator.generatePatientReport(
                prescriptionPrototype,
                "Spiderman",
                "Heart-Broken",
                "23 May 2026"
        );

        ((PrescriptionTemplate) johnPrescription).addMedicine("Morphine 10mg");

        MedicalReport batmanLabReport = generator.generatePatientReport(
                labPrototype,
                "Batman",
                "Orphan",
                "23 May 2026"
        );

        ((LabReportTemplate) batmanLabReport).addObservation("HbA1c: 6.2% - Pre-diabetic range");

        MedicalReport spidermanFollowUp = generator.generateFollowUpReport(
                prescriptionPrototype,
                "Spiderman",
                "30 May 2026"
        );

        System.out.println("ORIGINAL PRESCRIPTION PROTOTYPE:");
        prescriptionPrototype.generateReport();

        System.out.println("SPIDERMAN'S PRESCRIPTION:");
        johnPrescription.printReport();

        System.out.println("BATMAN'S LAB REPORT:");
        batmanLabReport.printReport();

        System.out.println("SPIDERMAN'S FOLLOW-UP REPORT:");
        spidermanFollowUp.generateReport();
    }
}