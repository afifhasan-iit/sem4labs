package Prototype.Exam;

public class TheoryExamPaper extends ExamPaper {

    public TheoryExamPaper(String title, String instructions,
                           java.util.List<String> questionList,
                           String examDate,
                           String departmentName,
                           int marksDistribution) {
        super(title, instructions, questionList, examDate, departmentName, marksDistribution);
    }

    // Copy constructor
    public TheoryExamPaper(TheoryExamPaper target) {
        super(target);
    }

    @Override
    public ExamPaper clone() {
        return new TheoryExamPaper(this);
    }

    public void addEssayQuestion(String question) {
        addQuestion("Essay: " + question);
    }
}