package Hospital;

import java.util.ArrayList;
import java.util.List;

public class LabReportTemplate extends MedicalReport {

    private List<String> testResults;

    public LabReportTemplate(String patientName, String doctorName,
                              String diagnosis, String reportDate,
                              String hospitalHeader, List<String> sections,
                              List<String> testResults) {

        super(patientName, doctorName, diagnosis, reportDate, hospitalHeader, sections);
        this.testResults = testResults;
    }

    public LabReportTemplate(LabReportTemplate target) {
        super(target);
        if (target != null) {
            this.testResults = new ArrayList<>(target.testResults);
        }
    }

    @Override
    public MedicalReport clone() {
        return new LabReportTemplate(this);
    }

    public void addObservation(String observation) {
        testResults.add(observation);
        addSection("Observation: " + observation);
    }

    public void addTestResult(String result) {
        testResults.add(result);
    }

    @Override
    public void generateReport() {
        System.out.println("\n========================");
        System.out.println(hospitalHeader);
        System.out.println("** LAB REPORT **");
        System.out.println("Patient: " + patientName);
        System.out.println("Doctor: " + doctorName);
        System.out.println("Diagnosis: " + diagnosis);
        System.out.println("Report Date: " + reportDate);
        System.out.println("Test Results:");
        for (String result : testResults) {
            System.out.println("- " + result);
        }
        System.out.println("Additional Sections:");
        for (String section : sections) {
            System.out.println("- " + section);
        }
        System.out.println("========================\n");
    }
}