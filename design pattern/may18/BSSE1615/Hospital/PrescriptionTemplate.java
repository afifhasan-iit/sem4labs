package Hospital;

import java.util.ArrayList;
import java.util.List;

public class PrescriptionTemplate extends MedicalReport {

    private List<String> medicineList;

    public PrescriptionTemplate(String patientName, String doctorName,
                                 String diagnosis, String reportDate,
                                 String hospitalHeader, List<String> sections,
                                 List<String> medicineList) {

        super(patientName, doctorName, diagnosis, reportDate, hospitalHeader, sections);
        this.medicineList = medicineList;
    }

    public PrescriptionTemplate(PrescriptionTemplate target) {
        super(target);
        if (target != null) {
            this.medicineList = new ArrayList<>(target.medicineList);
        }
    }

    @Override
    public MedicalReport clone() {
        return new PrescriptionTemplate(this);
    }

    public void addMedicine(String medicine) {
        medicineList.add(medicine);
        addSection("Medicine: " + medicine);
    }

    public void removeMedicine(String medicine) {
        medicineList.remove(medicine);
        removeSection("Medicine: " + medicine);
    }

    @Override
    public void generateReport() {
        System.out.println("\n========================");
        System.out.println(hospitalHeader);
        System.out.println("** PRESCRIPTION **");
        System.out.println("Patient: " + patientName);
        System.out.println("Doctor: " + doctorName);
        System.out.println("Diagnosis: " + diagnosis);
        System.out.println("Report Date: " + reportDate);
        System.out.println("Prescribed Medicines:");
        for (String medicine : medicineList) {
            System.out.println("- " + medicine);
        }
        System.out.println("Additional Sections:");
        for (String section : sections) {
            System.out.println("- " + section);
        }
        System.out.println("========================\n");
    }
}