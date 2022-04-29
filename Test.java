public class Test {

    private Student student;    // Student object
    private String answer[];    // Answer of test

    /**
     * Constructor
     */
    public Test() {
        this.student = null;
        this.answer = null;
    }

    /**
     *
     * @return student object
     */
    public Student getStudent() {
        return this.student;
    }

    /**
     *
     * @return answer of test
     */
    public String[] getAnswer() {
        return this.answer;
    }

    /**
     * Set student
     *
     * @param student student object
     */
    public void setStudent(final Student student) {
        this.student = student;
    }

    /**
     * Set answer
     *
     * @param answer answer of test
     */
    public void setAnswer(final String answer[]) {
        this.answer = answer;
    }

    /**
     * Display test results in proper format
     */
    public void showTestResult() {
        final int SIZE = answer.length;

        System.out.println("Student Name:" + student.getStudentId());
        for (int i = 0; i < SIZE; i++) {
            System.out.print(answer[i]);
        }
    }
}
