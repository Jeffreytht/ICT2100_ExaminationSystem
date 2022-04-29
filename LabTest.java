import java.util.ArrayList;

public class LabTest {

    private ArrayList<Student> student;  // A set of student
    private QuestionBank questionBank;  // Question bank object
    private Examination exam;           // Examination object

    /**
     * Constructor
     */
    public LabTest() {
        this.student = new ArrayList<>();
        this.questionBank = new QuestionBank();
        this.exam = null;
        Project.readFile("student.bin", this.student);
    }

    /**
     *
     * @return a set of student
     */
    public ArrayList<Student> getStudent() {
        return this.student;
    }

    /**
     *
     * @return question bank object
     */
    public QuestionBank getQuestionBank() {
        return this.questionBank;
    }

    /**
     *
     * @return exam object
     */
    public Examination getExam() {
        return this.exam;
    }

    /**
     * Set exam
     *
     * @param exam exam object
     */
    public void setExam(Examination exam) {
        this.exam = exam;
    }

    /**
     * Set student
     *
     * @param student a set of student
     */
    public void setStudent(ArrayList<Student> student) {
        this.student = student;
    }

    /**
     * Set question bank
     *
     * @param questionBank question bank object
     */
    public void setQuestionBank(QuestionBank questionBank) {
        this.questionBank = questionBank;
    }
}
