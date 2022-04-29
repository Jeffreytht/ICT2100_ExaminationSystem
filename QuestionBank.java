import java.io.File;
import java.io.FileReader;
import java.util.Scanner;
import java.util.Random;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class QuestionBank {

    private String title;           // Title of questionbank
    private Question[] question;    // A set of question

    /**
     * Constructor
     */
    public QuestionBank() {
        this.title = "";
        this.question = null;
    }

    /**
     *
     * @return title of question bank
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @return a set of question
     */
    public Question[] getQuestion() {
        return this.question;
    }

    /**
     * Set title
     *
     * @param title title of question bank
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Read all the question from question bank
     *
     * @param file file path
     */
    public void loadQuestion(String file) {
        // Check whether user enter the extension 
        if (!file.endsWith(".txt")) {
            file += ".txt";
        }

        File myFile = new File(file);
        try (FileReader fr = new FileReader(myFile)) {
            ArrayList<Question> question = new ArrayList<>();   // Store the question
            Scanner sf = new Scanner(fr);

            String questionText = "";   // Question
            String option[];            // Option ABCD
            String temp = "";
            char answer = '\0';         // Answer

            // Load all the question from the file path given
            for (int j = 0; sf.hasNext(); j++) {
                answer = '\0';

                // Store question
                questionText = sf.nextLine();
                option = new String[4];

                //Keep read line by line in the file until "a." or "*a." is meet.
                do {
                    temp = sf.next();
                    if (!temp.equals("a.") && !temp.equals("*a.")) {
                        temp += sf.nextLine();
                        questionText += "\n" + temp;
                    }

                } while (!temp.equals("a.") && !temp.equals("*a."));

                // Check whether A is the answer
                if (temp.equals("*a.")) {
                    answer = 'A';
                }

                // Read all the option and answer
                for (int i = 0; i < 4; i++) {
                    if (i != 0 && sf.next().charAt(0) == '*') {
                        answer = (char) ('A' + i);
                    }

                    option[i] = sf.nextLine();
                }

                // Check whether the question has answer
                if (answer != '\0') {
                    question.add(new Question(questionText, option, answer));
                }
            }

            final int TOTAL_QUESTION = question.size(); // Store the number of question that has been read

            // Check whether there are valid question in the file
            if (TOTAL_QUESTION == 0) {
                return;
            }

            //Assign all the question from arraylist to array
            this.question = new Question[TOTAL_QUESTION];

            for (int i = 0; i < TOTAL_QUESTION; i++) {
                this.question[i] = question.get(i);
            }

            sf.close();
            fr.close();
        } catch (FileNotFoundException ex) {
            System.out.println("\nNote: File has not been found.");
            this.question = null;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Select random question from question bank
     *
     * @param amountOfQuestion Amount of random question
     * @return a set of random question
     */
    public Question[] getQuestion(final int amountOfQuestion) {
        // Create an question array to store random question
        Question randomQuestion[] = new Question[amountOfQuestion];

        final int SIZE = question.length;       // Store the number of question in the question bank
        Random rand = new Random();             // Create an random object
        boolean inUsed[] = new boolean[SIZE];   // Check whether the question is already imported
        Arrays.fill(inUsed, false);             // Initialize all the element in inUsed array with false;

        // Repeat until all the random question is stored
        for (int i = 0; i < amountOfQuestion; i++) {
            int temp = rand.nextInt(SIZE);

            // An empty statement to loop until the inUsed is false. This can avoid redundant question and speed up the loading speed
            while (inUsed[temp = ++temp % SIZE]);

            // Store the random question
            randomQuestion[i] = question[temp];
            inUsed[temp] = true;
        }

        return randomQuestion;
    }

    /**
     * Show 3 questions in page by page basic
     */
    public void viewQuestion() {
        final int SIZE = question.length;   // Store the amount of question
        int page = 1;
        Scanner sc = new Scanner(System.in);

        for (int i = 0; i < SIZE; i++) {
            // Print next page after 3 question
            if (i % 3 == 0) {
                if (i != 0) {
                    System.out.print("Press enter key to proceed to next page...");
                    if (sc.nextLine().equals("-1")) {
                        break;
                    }
                    Project.clearScreen();
                }
                System.out.println("Page " + page++ + "/" + (int) (Math.ceil(SIZE / 3.0)));
            }

            //Print question
            System.out.print("Question " + (i + 1) + " :\n");
            System.out.println(question[i]);
            System.out.println("Answer :" + question[i].getAnswer());
            System.out.println();
        }
    }

    /**
     * clear all the preloaded questions
     */
    public void resetQuestionBank() {
        this.question = null;
        this.title = null;
    }
}
