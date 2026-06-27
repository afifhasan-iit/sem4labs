import java.util.ArrayList;

public interface QuestionSource {
    ArrayList<QuestionFactory> getQuestion();
    void displaySource();
    void addQuestion(QuestionFactory q);
}

class BankQuestionSource implements QuestionSource {
    ArrayList<QuestionFactory> questions =new ArrayList<>();
    String mode = "Question Bank ";
    @Override
    public ArrayList<QuestionFactory> getQuestion()
    {
         return questions;
    }
    public void addQuestion(QuestionFactory q){
        questions.add(q);
    }
    public void  displaySource()
    {
        System.out.println("Question sourcing Strategy: " + mode + "Mode");
    }
}

class FakeQuestionSource implements QuestionSource {
    String mode = "Fake Question Bank";
    ArrayList<QuestionFactory> questions = new ArrayList<>();

    @Override
    public ArrayList<QuestionFactory> getQuestion()
    {
        DummyuQuestionBank qb = new DummyuQuestionBank();
        qb.getQuestions();
        return questions;
    }
    public void addQuestion(QuestionFactory q){
        questions.add(q);

    }
    public void  displaySource()
    {
        System.out.println("Question sourcing Strategy: " + mode + " Mode ");
    }
}


class DummyuQuestionBank{
    ArrayList<String> questions;

    public  ArrayList<String> getQuestions()
    {
        return questions;
    }
}