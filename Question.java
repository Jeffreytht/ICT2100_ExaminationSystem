public class Question {

    private String questionText;    // Question
    private String option[];        // Option ABCD
    private char answer;            // Answer

    /**
     *
     * @return question
     */
    public String getQuestion() {
        return questionText;
    }

    /**
     *
     * @return option ABCD
     */
    public String[] getOption() {
        return option;
    }

    /**
     *
     * @return answer
     */
    public char getAnswer() {
        return answer;
    }

    /**
     * Set answer
     *
     * @param answer answer of question
     */
    public void setAnswer(final char answer) {
        this.answer = answer;
    }

    /**
     * User defined constructor
     *
     * @param answer answer of question
     */
    public Question(final char answer) {
        this.questionText = "";
        this.option = null;
        this.answer = answer;
    }

    /**
     * User defined constructor
     *
     * @param questionText question
     * @param option option ABCD
     * @param answer answer of question
     */
    public Question(final String questionText, final String option[], final char answer) {
        this.questionText = questionText;
        this.option = option;
        this.answer = answer;
    }

    @Override
    /**
     * Override toString method to print question and option
     *
     * @return question and option
     */
    public String toString() {
        String output = "";
        output += questionText + "\n";

        char temp = 'A';
        for (int i = 0; i < 4; i++) {
            output += temp++;
            output += option[i] + "\n";
        }
        return output;
    }

}
