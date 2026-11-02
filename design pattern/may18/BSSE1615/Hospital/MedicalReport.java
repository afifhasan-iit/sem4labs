package Hospital;

import java.util.ArrayList;
import java.util.List;

public abstract class MedicalReport {

    protected String patientName;
    protected String doctorName;
    protected String diagnosis;
    protected String reportDate;
    protected String hospitalHeader;
    protected List<String> sections;

    public MedicalReport(String patientName, String doctorName,
                         String diagnosis, String reportDate,
                         String hospitalHeader, List<String> sections) {

        this.patientName = patientName;
        this.doctorName = doctorName;
        this.diagnosis = diagnosis;
        this.reportDate = reportDate;
        this.hospitalHeader = hospitalHeader;
        this.sections = sections;
    }

    public MedicalReport(MedicalReport target) {
        if (target != null) {
            this.patientName = target.patientName;
            this.doctorName = target.doctorName;
            this.diagnosis = target.diagnosis;
            this.reportDate = target.reportDate;
            this.hospitalHeader = target.hospitalHeader;

            this.sections = new ArrayList<>(target.sections);
        }
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public void addSection(String section) {
        sections.add(section);
    }

    public void removeSection(String section) {
        sections.remove(section);
    }

    public void generateReport() {
        System.out.println("\n========================");
        System.out.println(hospitalHeader);
        System.out.println("Patient: " + patientName);
        System.out.println("Doctor: " + doctorName);
        System.out.println("Diagnosis: " + diagnosis);
        System.out.println("Report Date: " + reportDate);
        System.out.println("Sections:");
        for (String section : sections) {
            System.out.println("- " + section);
        }
        System.out.println("========================\n");
    }

    public void printReport() {
        HospitalLogger logger = HospitalLogger.getInstance();
        logger.logActivity("Report printed for patient: " + patientName +
                " | Doctor: " + doctorName);
        generateReport();
    }

    public abstract MedicalReport clone();
}