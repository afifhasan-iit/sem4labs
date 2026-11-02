package Prototype.Exam;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<String> questions = new ArrayList<>();
        questions.add("1. Who is Mikhail?");
        questions.add("2. Explain inheritance.");

        TheoryExamPaper original = new TheoryExamPaper(
                "Final Exam",
                "Answer all questions",
                questions,
                "18 May 2026",
                "IIT",
                100
        );

        ExamGenerator generator = new ExamGenerator();

        ExamPaper sectionB = generator.generateSectionB(original);

        sectionB.addQuestion("3. New cloned question added");
        sectionB.setExamDate("22 May 2026");

        System.out.println("ORIGINAL PAPER:");
        original.displayPaper();

        System.out.println("CLONED PAPER:");
        sectionB.displayPaper();
    }
}