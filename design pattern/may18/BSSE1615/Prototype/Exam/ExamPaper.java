package Prototype.Exam;

import java.util.ArrayList;
import java.util.List;

public abstract class ExamPaper {

    protected String title;
    protected String instructions;
    protected List<String> questionList;
    protected String examDate;
    protected String departmentName;
    protected int marksDistribution;

    public ExamPaper(String title, String instructions,
                     List<String> questionList,
                     String examDate,
                     String departmentName,
                     int marksDistribution) {

        this.title = title;
        this.instructions = instructions;
        this.questionList = questionList;
        this.examDate = examDate;
        this.departmentName = departmentName;
        this.marksDistribution = marksDistribution;
    }

    public ExamPaper(ExamPaper target) {
        if (target != null) {
            this.title = target.title;
            this.instructions = target.instructions;
            this.examDate = target.examDate;
            this.departmentName = target.departmentName;
            this.marksDistribution = target.marksDistribution;

            this.questionList = new ArrayList<>(target.questionList);
        }
    }

    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }

    public void addQuestion(String question) {
        questionList.add(question);
    }

    public void removeQuestion(String question) {
        questionList.remove(question);
    }

    public void displayPaper() {
        System.out.println("\n========================");
        System.out.println("Title: " + title);
        System.out.println("Department: " + departmentName);
        System.out.println("Exam Date: " + examDate);
        System.out.println("Instructions: " + instructions);
        System.out.println("Marks: " + marksDistribution);
        System.out.println("Questions:");
        for (String q : questionList) {
            System.out.println("- " + q);
        }
        System.out.println("========================\n");
    }

    public abstract ExamPaper clone();
}