


interface ReportExporter {
    void export(String reportName, String content);
}

abstract class Report {

    protected ReportExporter exporter;

    public Report(ReportExporter exporter) {
        this.exporter = exporter;
    }

    public abstract void generateReport();
}

class ReportManager {

    public void createReport(Report report) {
        report.generateReport();
    }
}

class StudentReport extends Report {

    public StudentReport(ReportExporter exporter) {
        super(exporter);
    }

    @Override
    public void generateReport() {
        String content = "Student grades and attendance data";
        exporter.export("Student Report", content);
    }
}

class EmployeeReport extends Report {

    public EmployeeReport(ReportExporter exporter) {
        super(exporter);
    }

    @Override
    public void generateReport() {
        String content = "Employee attendance and payroll data";
        exporter.export("Employee Report", content);
    }
}

class SalesReport extends Report {

    public SalesReport(ReportExporter exporter) {
        super(exporter);
    }

    @Override
    public void generateReport() {
        String content = "Quarterly sales figures by region";
        exporter.export("Sales Report", content);
    }
}

class FinancialReport extends Report {

    public FinancialReport(ReportExporter exporter) {
        super(exporter);
    }

    @Override
    public void generateReport() {
        String content = "Revenue, expenses and profit summary";
        exporter.export("Financial Report", content);
    }
}

class PDFExporter implements ReportExporter {
    @Override
    public void export(String reportName, String content) {
        System.out.println("Exporting " + reportName + " as PDF -> " + content);
    }
}

class ExcelExporter implements ReportExporter {
    @Override
    public void export(String reportName, String content) {
        System.out.println("Exporting " + reportName + " as Excel -> " + content);
    }
}

class CSVExporter implements ReportExporter {
    @Override
    public void export(String reportName, String content) {
        System.out.println("Exporting " + reportName + " as CSV -> " + content);
    }
}

class HTMLExporter implements ReportExporter {
    @Override
    public void export(String reportName, String content) {
        System.out.println("Exporting " + reportName + " as HTML -> " + content);
    }
}


public class Main {
    public static void main(String[] args) {

        ReportManager manager = new ReportManager();

        Report studentPDF = new StudentReport(new PDFExporter());
        Report employeeExcel = new EmployeeReport(new ExcelExporter());
        Report salesCSV = new SalesReport(new CSVExporter());
        Report financialHTML = new FinancialReport(new HTMLExporter());
        Report studentHTML = new StudentReport(new HTMLExporter());

        manager.createReport(studentPDF);
        manager.createReport(employeeExcel);
        manager.createReport(salesCSV);
        manager.createReport(financialHTML);
        manager.createReport(studentHTML);
    }
}