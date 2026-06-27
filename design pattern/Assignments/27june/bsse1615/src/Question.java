//abstract Factory method
public interface Question {
    String getType();
    int getPoint();
    String getDifficulty();
    Question setPoint(int point);
    Question setDifficulty(String difficulty);

}
interface QuestionRenderer{
    void render(Question q);
}
interface QuestionEvaluator{
    int evaluate(Question q, String ans);
}

class MCQQuestion implements Question{


    String type = "mcq";
    @Override
    public String getType() {
        return type;
    }

    String difficulty;
    public Question setDifficulty(String difficulty) {
        this.difficulty = difficulty;
        return this;
    }
    int point=2;
    public Question setPoint(int point) {
        this.point = point;
        return this;
    }
    public int getPoint() {
        return point;
    }
    public String getDifficulty() {
        return difficulty;
    }
}
class TrueFalseQuestion implements Question{
    String type = "true/False";
    @Override
    public String getType() {
        return type;
    }
    String difficulty;
    public Question setDifficulty(String difficulty) {
        this.difficulty = difficulty;
        return this;
    }
    int point=1;
    public Question setPoint(int point) {
        this.point = point;
        return this;
    }
    public int getPoint() {
        return point;
    }
    public String getDifficulty() {
        return difficulty;
    }
}
class EssayQuestion implements Question{
    String type = "Essay";
    @Override
    public String getType() {
        return type;
    }
    String difficulty;
    public Question setDifficulty(String difficulty) {
        this.difficulty = difficulty;
        return this;

    }
    int point=10;
    public Question setPoint(int point) {
        this.point = point;
        return this;

    }
    public int getPoint() {
        return point;
    }
    public String getDifficulty() {
        return difficulty;
    }
}
class ProgrammingQuestion implements Question{
    String type = "Programming";
    @Override
    public String getType() {
        return type;
    }
    String difficulty;
    public Question setDifficulty(String difficulty) {
        this.difficulty = difficulty;
        return this;

    }
    int point=20;
    public Question setPoint(int point) {
        this.point = point;
        return this;

    }
    public int getPoint() {
        return point;
    }
    public String getDifficulty() {
        return difficulty;
    }
}

class MCQRenderer implements QuestionRenderer{

    @Override
    public void render(Question q) {
        System.out.println("-[Type:" + q.getType() + "]\t\t\t Points:" + q.getPoint() +"  Difficulty:" + q.getDifficulty() );
    }
}
class TrueFalseRenderer implements QuestionRenderer{
    @Override
    public void render(Question q) {
        System.out.println("-[Type:" + q.getType() + "]\t Points:" + q.getPoint() +  "  Difficulty:" + q.getDifficulty() );

    }
}
class EssayRenderer implements QuestionRenderer{
    @Override
    public void render(Question q) {
        System.out.println("-[Type:" + q.getType() + "]\t\t Points:" + q.getPoint() +"  Difficulty:" + q.getDifficulty() );

    }
}
class ProgrammingRenderer implements QuestionRenderer{
    @Override
    public void render(Question q) {
        System.out.println("-[Type:" + q.getType() + "]\t Points:" + q.getPoint() +"  Difficulty:" + q.getDifficulty() );

    }
}

class MCQEvaluator implements QuestionEvaluator{
    @Override
    public int evaluate(Question q, String ans) {
        return 0;
    }
}
class TrueFalseEvaluator implements QuestionEvaluator{
    @Override
    public int evaluate(Question q, String ans) {
        return 0;
    }
}
class EssayEvaluator implements QuestionEvaluator{
    @Override
    public int evaluate(Question q, String ans) {
        return 0;
    }
}
class ProgrammingEvaluator implements QuestionEvaluator{
    @Override
    public int evaluate(Question q, String ans) {
        return 0;
    }
}


interface QuestionFactory{
    Question createQuestion();
    QuestionRenderer createQuestionRenderer();
    QuestionEvaluator createQuestionEvaluator();



    String getType();
    int getPoint();
    String getDifficulty();
    QuestionFactory setPoint(int point);
    QuestionFactory setDifficulty(String difficulty);
}

class MCQFactory implements QuestionFactory{

    String type = "mcq";
    @Override
    public String getType() {
        return type;
    }

    String difficulty;
    int point=2;

    public QuestionFactory setDifficulty(String difficulty) {
        this.difficulty = difficulty;
        return this;
    }
    public QuestionFactory setPoint(int point) {
        this.point = point;
        return this;
    }
    public int getPoint() {
        return point;
    }
    public String getDifficulty() {
        return difficulty;
    }




    Question q;
    @Override
    public Question createQuestion() {
        q=new MCQQuestion().setPoint(point).setDifficulty(difficulty);
        return q;
    }
    @Override
    public QuestionRenderer createQuestionRenderer() {
        return new MCQRenderer();
    }
    @Override
    public QuestionEvaluator createQuestionEvaluator() {
        return new MCQEvaluator();
    }




}
class TrueFalseFactory implements QuestionFactory{
    @Override
    public Question createQuestion() {
        return new TrueFalseQuestion().setPoint(point).setDifficulty(difficulty);
    }
    @Override
    public QuestionRenderer createQuestionRenderer() {
        return new TrueFalseRenderer();
    }
    @Override
    public QuestionEvaluator createQuestionEvaluator() {
        return new TrueFalseEvaluator();
    }



    String type = "true/False";
    @Override
    public String getType() {
        return type;
    }
    String difficulty;
    public QuestionFactory setDifficulty(String difficulty) {
        this.difficulty = difficulty;
        return this;
    }
    int point=1;
    public QuestionFactory setPoint(int point) {
        this.point = point;
        return this;
    }
    public int getPoint() {
        return point;
    }
    public String getDifficulty() {
        return difficulty;
    }
}

class EssayFactory implements QuestionFactory{
    @Override
    public Question createQuestion() {
        return new EssayQuestion().setPoint(point).setDifficulty(difficulty);
    }
    @Override
    public QuestionRenderer createQuestionRenderer() {
        return new EssayRenderer();
    }
    @Override
    public QuestionEvaluator createQuestionEvaluator() {
        return new EssayEvaluator();
    }



    String type = "Essay";
    @Override
    public String getType() {
        return type;
    }
    String difficulty;
    public QuestionFactory setDifficulty(String difficulty) {
        this.difficulty = difficulty;
        return this;

    }
    int point=10;
    public QuestionFactory setPoint(int point) {
        this.point = point;
        return this;

    }
    public int getPoint() {
        return point;
    }
    public String getDifficulty() {
        return difficulty;
    }
}

class ProgrammingFactory implements QuestionFactory{
    @Override
    public Question createQuestion() {
        return new ProgrammingQuestion().setPoint(point).setDifficulty(difficulty);
    }
    @Override
    public QuestionRenderer createQuestionRenderer() {
        return new ProgrammingRenderer();
    }
    @Override
    public QuestionEvaluator createQuestionEvaluator() {
        return new ProgrammingEvaluator();
    }



    String type = "programming";
    @Override
    public String getType() {
        return type;
    }
    String difficulty;
    public QuestionFactory setDifficulty(String difficulty) {
        this.difficulty = difficulty;
        return this;

    }
    int point=10;
    public QuestionFactory setPoint(int point) {
        this.point = point;
        return this;

    }
    public int getPoint() {
        return point;
    }
    public String getDifficulty() {
        return difficulty;
    }
}