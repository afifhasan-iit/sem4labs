package Prototype.Exam;

public class ExamGenerator {

    public ExamPaper generateSectionB(ExamPaper prototype) {
        ExamPaper copy = prototype.clone();
        copy.setExamDate("22 May 2026");
        copy.addQuestion("Section B - Additional Question");
        return copy;
    }

    public ExamPaper generateRetakeExam(ExamPaper prototype) {
        ExamPaper copy = prototype.clone();
        copy.setExamDate("30 May 2026");
        copy.addQuestion("Retake Special Question");
        return copy;
    }
}