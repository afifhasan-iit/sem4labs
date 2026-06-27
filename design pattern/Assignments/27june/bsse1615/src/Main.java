import javax.swing.*;
import java.util.ArrayList;

public class  Main {

    static void generateMCQQuestion(int point, String difficulty){
        QuestionFactory  q1 = new MCQFactory().setPoint(point).setDifficulty(difficulty);
        Question Question = q1.createQuestion();
        QuestionRenderer Renderer = q1.createQuestionRenderer();
        QuestionEvaluator Evaluator = q1.createQuestionEvaluator();

        Renderer.render(Question);
    }
    static void generateTrueFalseQuestion(int point, String difficulty){
        QuestionFactory  q1 = new TrueFalseFactory().setPoint(point).setDifficulty(difficulty);
        Question Question = q1.createQuestion();
        QuestionRenderer Renderer = q1.createQuestionRenderer();
        QuestionEvaluator Evaluator = q1.createQuestionEvaluator();

        Renderer.render(Question);
    }
    static void generateEssayQuestion(int point, String difficulty){
        QuestionFactory  q1 = new EssayFactory().setPoint(point).setDifficulty(difficulty);
        Question Question = q1.createQuestion();
        QuestionRenderer Renderer = q1.createQuestionRenderer();
        QuestionEvaluator Evaluator = q1.createQuestionEvaluator();

        Renderer.render(Question);
    }
    static void generateProgrammingQuestion(int point, String difficulty){
        QuestionFactory  q1 = new ProgrammingFactory().setPoint(point).setDifficulty(difficulty);
        Question Question = q1.createQuestion();
        QuestionRenderer Renderer = q1.createQuestionRenderer();
        QuestionEvaluator Evaluator = q1.createQuestionEvaluator();

        Renderer.render(Question);
    }



    static void displayQuestion(QuestionSource source){
//        System.out.println("Compiled Structural Components:");
//        generateMCQQuestion(2,"medium");
//        generateMCQQuestion(2,"easy");
//        generateEssayQuestion(10,"hard");
//        generateProgrammingQuestion(20,"hard");
//        generateTrueFalseQuestion(1,"easy");

        generateQuestionBank(source);
        printFetchedQuestion(source);

    }

    static void generateQuestionBank(QuestionSource source){
        QuestionFactory q1 = new MCQFactory().setPoint(2).setDifficulty("medium");
        QuestionFactory q2 = new MCQFactory().setPoint(2).setDifficulty("easy");
        QuestionFactory q3 = new EssayFactory().setPoint(10).setDifficulty("hard");
        QuestionFactory q4 = new ProgrammingFactory().setPoint(20).setDifficulty("hard");
        QuestionFactory q5 = new TrueFalseFactory().setPoint(1).setDifficulty("easy");
        source.addQuestion(q1);
        source.addQuestion(q2);
        source.addQuestion(q3);
        source.addQuestion(q4);
        source.addQuestion(q5);
    }

    static void printFetchedQuestion(QuestionSource source){

        ArrayList<QuestionFactory> questions = source.getQuestion();
        for(QuestionFactory q : questions){
            Question Question = q.createQuestion();
            QuestionRenderer Renderer = q.createQuestionRenderer();
            QuestionEvaluator Evaluator = q.createQuestionEvaluator();
            Renderer.render(Question);

        }
    }


    public static void main(String[] args) {
        ExamFactory midExamFactory = new MidTermExamFactory();
        QuestionSource questionSource = new BankQuestionSource();
        QuestionFactory qf = new MCQFactory();
        Question q1 = qf.createQuestion();
        ExamConfiguration examConfig = new ExamConfiguration.ExamBuilder()
                .setTitle("OOP Midterm Exam")
                .setDuration(60)
                .setPassingScore(50)
                .setNegativeMarking(true)
                .setQuestionShuffle(true)
                .setCalculatorAllowed(false)
                .setAutoSubmit(true)
                .build();


        System.out.println("=========================================================================");
        midExamFactory.displayInformation();
        examConfig.display();
        questionSource.displaySource();
        displayQuestion(questionSource);
        System.out.println("=========================================================================");

    }
}
