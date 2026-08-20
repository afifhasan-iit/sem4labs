import java.util.ArrayList;
import java.util.List;


interface AcademicVisitor {
    void visitCourse(Course course);
    void visitLaboratoryCourse(LaboratoryCourse lab);
    void visitProject(Project project);
    void visitThesis(Thesis thesis);
}

interface AcademicComponent {
    void accept(AcademicVisitor visitor);
}


class Course implements AcademicComponent {
    private String courseCode;
    private String title;
    private int creditHours;
    private double marks;       // out of 100

    public Course(String courseCode, String title, int creditHours, double marks) {
        this.courseCode  = courseCode;
        this.title       = title;
        this.creditHours = creditHours;
        this.marks       = marks;
    }

    public String getCourseCode()  { return courseCode; }
    public String getTitle()       { return title; }
    public int getCreditHours()    { return creditHours; }
    public double getMarks()       { return marks; }

    @Override
    public void accept(AcademicVisitor visitor) { visitor.visitCourse(this); }
}

class LaboratoryCourse implements AcademicComponent {
    private String courseCode;
    private String title;
    private int creditHours;
    private double practicalMarks;
    private double labReportMarks;

    public LaboratoryCourse(String courseCode, String title, int creditHours,
                            double practicalMarks, double labReportMarks) {
        this.courseCode      = courseCode;
        this.title           = title;
        this.creditHours     = creditHours;
        this.practicalMarks  = practicalMarks;
        this.labReportMarks  = labReportMarks;
    }

    public String getCourseCode()     { return courseCode; }
    public String getTitle()          { return title; }
    public int getCreditHours()       { return creditHours; }
    public double getPracticalMarks() { return practicalMarks; }
    public double getLabReportMarks() { return labReportMarks; }
    public double getTotalMarks()     { return practicalMarks + labReportMarks; }

    @Override
    public void accept(AcademicVisitor visitor) { visitor.visitLaboratoryCourse(this); }
}

class Project implements AcademicComponent {
    private String courseCode;
    private String title;
    private int creditHours;
    private String supervisor;
    private double supervisorMarks;   // out of 60
    private double presentationMarks; // out of 40

    public Project(String courseCode, String title, int creditHours,
                   String supervisor, double supervisorMarks, double presentationMarks) {
        this.courseCode         = courseCode;
        this.title              = title;
        this.creditHours        = creditHours;
        this.supervisor         = supervisor;
        this.supervisorMarks    = supervisorMarks;
        this.presentationMarks  = presentationMarks;
    }

    public String getCourseCode()          { return courseCode; }
    public String getTitle()               { return title; }
    public int getCreditHours()            { return creditHours; }
    public String getSupervisor()          { return supervisor; }
    public double getSupervisorMarks()     { return supervisorMarks; }
    public double getPresentationMarks()   { return presentationMarks; }
    public double getTotalMarks()          { return supervisorMarks + presentationMarks; }

    @Override
    public void accept(AcademicVisitor visitor) { visitor.visitProject(this); }
}

class Thesis implements AcademicComponent {
    private String courseCode;
    private String title;
    private int creditHours;
    private String supervisor;
    private double thesisMarks;        // out of 70
    private double defenseMarks;       // out of 30

    public Thesis(String courseCode, String title, int creditHours,
                  String supervisor, double thesisMarks, double defenseMarks) {
        this.courseCode   = courseCode;
        this.title        = title;
        this.creditHours  = creditHours;
        this.supervisor   = supervisor;
        this.thesisMarks  = thesisMarks;
        this.defenseMarks = defenseMarks;
    }

    public String getCourseCode()    { return courseCode; }
    public String getTitle()         { return title; }
    public int getCreditHours()      { return creditHours; }
    public String getSupervisor()    { return supervisor; }
    public double getThesisMarks()   { return thesisMarks; }
    public double getDefenseMarks()  { return defenseMarks; }
    public double getTotalMarks()    { return thesisMarks + defenseMarks; }

    @Override
    public void accept(AcademicVisitor visitor) { visitor.visitThesis(this); }
}


class GradeUtil {
    public static String getLetterGrade(double marks) {
        if (marks >= 80) return "A+";
        if (marks >= 75) return "A";
        if (marks >= 70) return "A-";
        if (marks >= 65) return "B+";
        if (marks >= 60) return "B";
        if (marks >= 55) return "B-";
        if (marks >= 50) return "C+";
        if (marks >= 45) return "C";
        if (marks >= 40) return "D";
        return "F";
    }

    public static double getGradePoint(double marks) {
        if (marks >= 80) return 4.00;
        if (marks >= 75) return 3.75;
        if (marks >= 70) return 3.50;
        if (marks >= 65) return 3.25;
        if (marks >= 60) return 3.00;
        if (marks >= 55) return 2.75;
        if (marks >= 50) return 2.50;
        if (marks >= 45) return 2.25;
        if (marks >= 40) return 2.00;
        return 0.00;
    }

    public static boolean isPassed(double marks) {
        return marks >= 40;
    }
}


class GradeCalculationVisitor implements AcademicVisitor {
    private double totalGradePoints = 0;
    private int    totalCredits     = 0;

    @Override
    public void visitCourse(Course c) {
        double marks      = c.getMarks();
        double gradePoint = GradeUtil.getGradePoint(marks);
        totalGradePoints += gradePoint * c.getCreditHours();
        totalCredits     += c.getCreditHours();
        System.out.printf("[GRADE] %-30s Marks: %5.1f  Grade: %-3s  GP: %.2f%n",
                c.getTitle(), marks, GradeUtil.getLetterGrade(marks), gradePoint);
    }

    @Override
    public void visitLaboratoryCourse(LaboratoryCourse lab) {
        double marks      = lab.getTotalMarks();
        double gradePoint = GradeUtil.getGradePoint(marks);
        totalGradePoints += gradePoint * lab.getCreditHours();
        totalCredits     += lab.getCreditHours();
        System.out.printf("[GRADE] %-30s Marks: %5.1f  Grade: %-3s  GP: %.2f  (Practical: %.1f + Report: %.1f)%n",
                lab.getTitle(), marks, GradeUtil.getLetterGrade(marks), gradePoint,
                lab.getPracticalMarks(), lab.getLabReportMarks());
    }

    @Override
    public void visitProject(Project p) {
        double marks      = p.getTotalMarks();
        double gradePoint = GradeUtil.getGradePoint(marks);
        totalGradePoints += gradePoint * p.getCreditHours();
        totalCredits     += p.getCreditHours();
        System.out.printf("[GRADE] %-30s Marks: %5.1f  Grade: %-3s  GP: %.2f  (Supervisor: %.1f + Presentation: %.1f)%n",
                p.getTitle(), marks, GradeUtil.getLetterGrade(marks), gradePoint,
                p.getSupervisorMarks(), p.getPresentationMarks());
    }

    @Override
    public void visitThesis(Thesis t) {
        double marks      = t.getTotalMarks();
        double gradePoint = GradeUtil.getGradePoint(marks);
        totalGradePoints += gradePoint * t.getCreditHours();
        totalCredits     += t.getCreditHours();
        System.out.printf("[GRADE] %-30s Marks: %5.1f  Grade: %-3s  GP: %.2f  (Thesis: %.1f + Defense: %.1f)%n",
                t.getTitle(), marks, GradeUtil.getLetterGrade(marks), gradePoint,
                t.getThesisMarks(), t.getDefenseMarks());
    }

    public void printCGPA() {
        double cgpa = totalCredits > 0 ? totalGradePoints / totalCredits : 0;
        System.out.printf("%nCalculated CGPA: %.2f (over %d credit hours)%n", cgpa, totalCredits);
    }
}


class CreditCalculationVisitor implements AcademicVisitor {
    private int completedCredits = 0;
    private int failedCredits    = 0;

    private void process(String title, int credits, double marks) {
        if (GradeUtil.isPassed(marks)) {
            completedCredits += credits;
            System.out.printf("[CREDIT] %-30s Credits: %d  Status: COMPLETED%n", title, credits);
        } else {
            failedCredits += credits;
            System.out.printf("[CREDIT] %-30s Credits: %d  Status: FAILED%n", title, credits);
        }
    }

    @Override
    public void visitCourse(Course c) {
        process(c.getTitle(), c.getCreditHours(), c.getMarks());
    }
    @Override
    public void visitLaboratoryCourse(LaboratoryCourse lab) {
        process(lab.getTitle(), lab.getCreditHours(), lab.getTotalMarks());
    }
    @Override
    public void visitProject(Project p) {
        process(p.getTitle(), p.getCreditHours(), p.getTotalMarks());
    }
    @Override
    public void visitThesis(Thesis t) {
        process(t.getTitle(), t.getCreditHours(), t.getTotalMarks());
    }

    public void printSummary() {
        System.out.println("\nCompleted Credits : " + completedCredits);
        System.out.println("Failed Credits    : " + failedCredits);
        System.out.println("Total Credits     : " + (completedCredits + failedCredits));
    }
}


class TranscriptGenerationVisitor implements AcademicVisitor {
    @Override
    public void visitCourse(Course c) {
        System.out.printf("  %-10s %-30s %3d cr   %5.1f   %-3s%n",
                c.getCourseCode(), c.getTitle(), c.getCreditHours(),
                c.getMarks(), GradeUtil.getLetterGrade(c.getMarks()));
    }
    @Override
    public void visitLaboratoryCourse(LaboratoryCourse lab) {
        System.out.printf("  %-10s %-30s %3d cr   %5.1f   %-3s  [LAB]%n",
                lab.getCourseCode(), lab.getTitle(), lab.getCreditHours(),
                lab.getTotalMarks(), GradeUtil.getLetterGrade(lab.getTotalMarks()));
    }
    @Override
    public void visitProject(Project p) {
        System.out.printf("  %-10s %-30s %3d cr   %5.1f   %-3s  [PROJECT | Supervisor: %s]%n",
                p.getCourseCode(), p.getTitle(), p.getCreditHours(),
                p.getTotalMarks(), GradeUtil.getLetterGrade(p.getTotalMarks()), p.getSupervisor());
    }
    @Override
    public void visitThesis(Thesis t) {
        System.out.printf("  %-10s %-30s %3d cr   %5.1f   %-3s  [THESIS | Supervisor: %s]%n",
                t.getCourseCode(), t.getTitle(), t.getCreditHours(),
                t.getTotalMarks(), GradeUtil.getLetterGrade(t.getTotalMarks()), t.getSupervisor());
    }
}


class StatisticsVisitor implements AcademicVisitor {
    private int    totalComponents = 0;
    private int    passed          = 0;
    private int    failed          = 0;
    private double totalMarks      = 0;
    private double highestMarks    = Double.MIN_VALUE;
    private String highestTitle    = "";

    private void process(String title, double marks) {
        totalComponents++;
        totalMarks += marks;
        if (GradeUtil.isPassed(marks)) passed++; else failed++;
        if (marks > highestMarks) { highestMarks = marks; highestTitle = title; }
    }

    @Override
    public void visitCourse(Course c)             { process(c.getTitle(), c.getMarks()); }
    @Override
    public void visitLaboratoryCourse(LaboratoryCourse lab) { process(lab.getTitle(), lab.getTotalMarks()); }
    @Override
    public void visitProject(Project p)           { process(p.getTitle(), p.getTotalMarks()); }
    @Override
    public void visitThesis(Thesis t)             { process(t.getTitle(), t.getTotalMarks()); }

    public void printStatistics() {
        System.out.println("\n--- Performance Statistics ---");
        System.out.println("Total Components : " + totalComponents);
        System.out.println("Passed           : " + passed);
        System.out.println("Failed           : " + failed);
        System.out.printf ("Average Marks    : %.2f%n", totalComponents > 0 ? totalMarks / totalComponents : 0);
        System.out.println("Highest Grade    : " + highestTitle + " (" + highestMarks + ")");
    }
}


class AcademicProbationVisitor implements AcademicVisitor {
    private boolean onProbation = false;

    private void check(String title, double marks) {
        if (!GradeUtil.isPassed(marks)) {
            System.out.println("[PROBATION] FAILED component detected: " + title
                    + " (Marks: " + marks + ")");
            onProbation = true;
        }
    }

    @Override
    public void visitCourse(Course c)                      { check(c.getTitle(), c.getMarks()); }
    @Override
    public void visitLaboratoryCourse(LaboratoryCourse lab){ check(lab.getTitle(), lab.getTotalMarks()); }
    @Override
    public void visitProject(Project p)                    { check(p.getTitle(), p.getTotalMarks()); }
    @Override
    public void visitThesis(Thesis t)                      { check(t.getTitle(), t.getTotalMarks()); }

    public void printVerdict() {
        System.out.println(onProbation
                ? "\n[PROBATION] ⚠ Student is on ACADEMIC PROBATION."
                : "\n[PROBATION] Student is in good academic standing.");
    }
}


class Student {
    private String studentId;
    private String name;
    private String department;
    private String batch;
    private List<AcademicComponent> components = new ArrayList<>();

    public Student(String studentId, String name, String department, String batch) {
        this.studentId  = studentId;
        this.name       = name;
        this.department = department;
        this.batch      = batch;
    }

    public void addAcademicComponent(AcademicComponent component) {
        components.add(component);
    }

    public void removeAcademicComponent(AcademicComponent component) {
        components.remove(component);
    }

    public void processRecord(AcademicVisitor visitor) {
        for (AcademicComponent component : components) {
            component.accept(visitor);
        }
    }

    public String getStudentId()  { return studentId; }
    public String getName()       { return name; }
    public String getDepartment() { return department; }
    public String getBatch()      { return batch; }

    public void printHeader() {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("  Student  : " + name + " (" + studentId + ")");
        System.out.println("  Dept     : " + department + "  |  Batch: " + batch);
        System.out.println("═══════════════════════════════════════════════════");
    }
}


public class Main {
    public static void main(String[] args) {

        Student student = new Student("BSSE-1615", "Afif", "Software Engineering", "2021");

        student.addAcademicComponent(new Course("CSE301", "Data Structures", 3, 78.5));
        student.addAcademicComponent(new Course("CSE303", "Operating Systems", 3, 62.0));
        student.addAcademicComponent(new Course("CSE305", "Database Systems", 3, 35.0)); // will fail
        student.addAcademicComponent(new LaboratoryCourse("CSE302L", "DS Lab", 1, 42.0, 38.5));
        student.addAcademicComponent(new Project("CSE400", "Library Management System", 3,
                "Dr. Rahman", 52.0, 34.0));
        student.addAcademicComponent(new Thesis("CSE499", "ML-Based Traffic Prediction", 6,
                "Dr. Hasan", 61.0, 25.0));

        System.out.println("\n========== GRADE CALCULATION ==========");
        student.printHeader();
        GradeCalculationVisitor gradeVisitor = new GradeCalculationVisitor();
        student.processRecord(gradeVisitor);
        gradeVisitor.printCGPA();

        System.out.println("\n========== CREDIT CALCULATION ==========");
        student.printHeader();
        CreditCalculationVisitor creditVisitor = new CreditCalculationVisitor();
        student.processRecord(creditVisitor);
        creditVisitor.printSummary();

        System.out.println("\n========== OFFICIAL TRANSCRIPT ==========");
        student.printHeader();
        System.out.printf("  %-10s %-30s %5s   %5s   %s%n",
                "Code", "Title", "Cr", "Marks", "Grade");
        System.out.println("  " + "─".repeat(70));
        TranscriptGenerationVisitor transcriptVisitor = new TranscriptGenerationVisitor();
        student.processRecord(transcriptVisitor);

        System.out.println("\n========== STATISTICS ==========");
        student.printHeader();
        StatisticsVisitor statsVisitor = new StatisticsVisitor();
        student.processRecord(statsVisitor);
        statsVisitor.printStatistics();

        System.out.println("\n========== ACADEMIC PROBATION CHECK ==========");
        student.printHeader();
        AcademicProbationVisitor probationVisitor = new AcademicProbationVisitor();
        student.processRecord(probationVisitor);
        probationVisitor.printVerdict();
    }
}