package Prototype.Exam;

public class LabExamPaper extends ExamPaper {

    public LabExamPaper(String title, String instructions,
                        java.util.List<String> questionList,
                        String examDate,
                        String departmentName,
                        int marksDistribution) {
        super(title, instructions, questionList, examDate, departmentName, marksDistribution);
    }

    public LabExamPaper(LabExamPaper target) {
        super(target);
    }

    @Override
    public ExamPaper clone() {
        return new LabExamPaper(this);
    }

    public void addPracticalTask(String task) {
        addQuestion("Practical Task: " + task);
    }
}