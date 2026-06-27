public class ExamConfiguration {
    String title;
    int duration;
    int passingScore;
    boolean negativeMarking;
    boolean questionShuffle;
    boolean autoSubmit;
    boolean calculatorAllowed;

private ExamConfiguration(ExamBuilder b) {
    this.title = b.title;
    this.duration = b.duration;
    this.passingScore = b.passingScore;
    this.negativeMarking = b.negativeMarking;
    this.questionShuffle = b.questionShuffle;
    this.autoSubmit = b.autoSubmit;
    this.calculatorAllowed = b.calculatorAllowed;
}

public void display(){
    System.out.println("Title:\t " + title+ "\n");
    System.out.println("Duration:\t " + duration);
    System.out.println("Passing Score:\t  " + passingScore + "\n");

    System.out.println("Configuration Profile:");
    System.out.println("Negetive Marking " + (negativeMarking? "Enabled":"Disabled"));
    System.out.println("Question Shuffle " + (questionShuffle? "Activated":"Deactivated"));
    System.out.println("Embedded Calculator "+ (calculatorAllowed? "Allowed":"Not Allowed"));
    System.out.println((autoSubmit? "Auto-Submit on Timeout":"\n"));
}

   static class ExamBuilder{
        String title;
        int duration;
        int passingScore;
        boolean negativeMarking;
        boolean questionShuffle;
        boolean autoSubmit;
        boolean calculatorAllowed;

        ExamBuilder setTitle(String title){
            this.title = title;
            return this;
        }
        ExamBuilder setDuration(int duration){
            this.duration = duration;
            return this;
        }
        ExamBuilder setPassingScore(int passingScore){
            this.passingScore = passingScore;
            return this;
        }
        ExamBuilder setNegativeMarking(boolean negativeMarking){
            this.negativeMarking = negativeMarking;
            return this;
        }
        ExamBuilder setQuestionShuffle(boolean questionShuffle){
            this.questionShuffle = questionShuffle;
            return this;
        }
        ExamBuilder setAutoSubmit(boolean autoSubmit){
            this.autoSubmit = autoSubmit;
            return this;
        }
        ExamBuilder setCalculatorAllowed(boolean calculatorAllowed){
            this.calculatorAllowed = calculatorAllowed;
            return this;
        }
        public ExamConfiguration build(){
            return new ExamConfiguration(this);
        }

    }
}
