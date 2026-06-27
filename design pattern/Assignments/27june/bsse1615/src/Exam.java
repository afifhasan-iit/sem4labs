//Factory method
public interface Exam {
    String getType();
    void displayInfo();
}

class PracticeQuiz implements Exam {
    String type = "Practice Quiz";
    @Override
    public String getType() {
        return type;
    }
    @Override
    public void displayInfo() {
        System.out.println("Type:\t " + type);
    }
}
class MidTermExam implements Exam {
    String type = "MidTerm Exam";
    @Override
    public String getType() {
       return type;
    }

    @Override
    public void displayInfo() {
        System.out.println("Type:\t " + type);

    }
}
class FinalExam implements Exam {
    String type = "final Exam";
    @Override
    public String getType() {
        return type;
    }
    @Override
    public void displayInfo() {
        System.out.println("Type:\t " + type);

    }
}

abstract class ExamFactory{
    public abstract Exam createExam();

    public void displayInformation(){
        Exam exam = createExam();
        exam.displayInfo();
    }
}

class PracticeQuizFactory extends ExamFactory{
    @Override
    public Exam createExam(){
        return new PracticeQuiz();
    }
}
class MidTermExamFactory  extends ExamFactory{
    @Override
    public Exam createExam(){
        return new MidTermExam();
    }
}
class FinalExamFactory extends ExamFactory{
    @Override
    public Exam createExam(){
        return new FinalExam();
    }
}

