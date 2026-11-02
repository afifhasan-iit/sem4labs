package Hospital;

public class ReportGenerator {

    public MedicalReport generatePatientReport(MedicalReport prototype, String patientName, String diagnosis, String reportDate) {
        MedicalReport copy = prototype.clone();
        copy.setPatientName(patientName);
        copy.setDiagnosis(diagnosis);
        copy.setReportDate(reportDate);
        return copy;
    }

    public MedicalReport generateFollowUpReport(MedicalReport prototype, String patientName, String reportDate) {
        MedicalReport copy = prototype.clone();
        copy.setPatientName(patientName);
        copy.setReportDate(reportDate);
        copy.addSection("Follow-Up Visit");
        return copy;
    }
}