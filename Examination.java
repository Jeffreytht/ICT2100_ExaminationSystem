import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Examination {

    private String examName;        // Exam name
    private Test[] test;            // A set of test
    private Question question[];    // A set of question

    /**
     *
     * @return exam name
     */
    public String getExamName() {
        return this.examName;
    }

    /**
     *
     * @return a set of test
     */
    public Test[] getTest() {
        return this.test;
    }

    /**
     *
     * @return a set of question
     */
    public Question[] getQuestion() {
        return this.question;
    }

    /**
     * Set test
     *
     * @param test a set of test
     */
    public void setTest(Test test[]) {
        this.test = test;
    }

    /**
     * Set exam name
     *
     * @param examName exam name
     */
    public void setExamName(String examName) {
        this.examName = examName;
    }

    /**
     * Set question
     *
     * @param question a set of question
     */
    public void setQuestion(Question[] question) {
        this.question = question;
    }

    /**
     * Constructor
     */
    public Examination() {
        this.examName = "";
        this.test = null;
        this.question = null;
    }

    /**
     * User defined constructor
     *
     * @param examName exam name
     * @param totalStudent total student who sit the exam
     * @param totalQuestion total question in the exam
     */
    public Examination(final String examName, final int totalStudent, final int totalQuestion) {
        this.examName = examName;
        this.test = new Test[totalStudent];
        this.question = new Question[totalQuestion];
    }

    /**
     * Load question from question bank
     *
     * @param qb
     */
    public void loadQuestion(QuestionBank qb) {
        final int NUM_OF_QUESTION = question.length;
        this.question = qb.getQuestion(NUM_OF_QUESTION);
    }

    /**
     * Export exam result to exam.txt
     */
    public void exportResult() {

        // Create a date object that store today's date
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = formatter.format(date);

        // Export the result to exam.txt
        try {
            final int NUM_OF_STUDENT = getTest().length;        // Store the number of student who sit the exam
            final int NUM_OF_QUESTION = getQuestion().length;   // Store the number of question in the exam
            File myFile = new File("exam.txt");

            try (
                    FileWriter fw = new FileWriter(myFile);
                    PrintWriter pw = new PrintWriter(fw);) {

                // Print header (Exam name and Date)
                pw.printf("%-30s", getExamName());
                pw.printf("%10s%n", currentDate);

                // Print the number of question and correct answer
                pw.printf("%d%n", NUM_OF_QUESTION);
                for (int i = 0; i < NUM_OF_QUESTION; i++) {
                    pw.printf("%c", getQuestion()[i].getAnswer());
                }
                pw.println();

                // Print student ID and answer
                for (int i = 0; i < NUM_OF_STUDENT; i++) {
                    pw.printf("P%d%n", getTest()[i].getStudent().getStudentId());

                    for (int j = 0; j < NUM_OF_QUESTION; j++) {
                        pw.printf("%s", getTest()[i].getAnswer()[j]);
                    }

                    pw.println();
                }
                pw.close();
                fw.close();
            } catch (IOException ex) {

            }
        } catch (NullPointerException ex) {
            System.out.println("Note: There are no result in the examination.");
        }
    }

    /**
     * Remove all the questions and test results
     */
    public void clearExamination() {
        this.examName = null;
        this.test = null;
        this.question = null;
    }
}
