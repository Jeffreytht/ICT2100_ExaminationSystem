import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.NoSuchElementException;
import java.util.InputMismatchException;
import java.util.Date;
import java.util.regex.Pattern;
import java.text.SimpleDateFormat;

public class Project {

    // Variable that represent the data type of return value in generic function
    private final static int INT = 1;
    private final static double DOUBLE = 1.0;
    private final static String STRING = "1.0";
    private final static char CHAR = '1';

    // Variable that represent the type of validation to perform
    private final static int MIN = 1;
    private final static int MAX = 2;
    private final static int MINLENGTH = 4;
    private final static int MAXLENGTH = 8;
    private final static int NAME = 16;
    private final static int CONFIRMATION = 32;

    public static void main(String[] args) {

        int option = 0;                     // Store user option in main menu
        LabTest labTest = new LabTest();    // Create a new labtest object

        do {

            // Print the main menu
            printMainMenu();

            // Get option from users
            option = getInput(INT, "Enter", "option", "", MIN | MAX, 1, 4);

            switch (option) {
                case 1:
                    clearScreen();
                    // Load question bank
                    loadQuestionBank(labTest.getQuestionBank());
                    break;

                case 2:
                    clearScreen();
                    // Load Student maintenance
                    loadStudentMaintenance(labTest.getStudent());
                    break;

                case 3:
                    clearScreen();
                    // Load examination
                    loadExamination(labTest);
                    break;

                case 4:
                    System.out.println("Program Terminated! --Bye--Bye--");
            }
            clearScreen();
        } while (option != 4);
    }

    /**
     * Prompt user to input data with message given. All the validation will be
     * performed as desired.
     *
     * @param <T> the data type
     * @param type store the constant value that represent data type
     * @param optMessage1 message that prompt user to input
     * @param message value that require user to input
     * @param optMessage2 message that prompt user to input
     * @param validation integer that represent validation required
     * @param minimum minimum value
     * @param maximum maximum value
     * @return input
     */
    public static <T> T getInput(final T type, final String optMessage1, final String message, final String optMessage2, final int validation, final int minimum, final int maximum) {
        Scanner sc = new Scanner(System.in);    //Declare a scanner class
        boolean isValid = true;                 //Check whether input is valid
        String input = "";                      //Store users' input

        // Repeat until user enter valid input or -1
        do {
            // Display prompt message 
            System.out.print(optMessage1 + " " + message + " " + optMessage2 + " :");

            // Trim leading,trailing and continuosly whitespace
            input = sc.nextLine().trim();
            input = input.replaceAll("\\s+", " ");

        } while (!validateInput(input, validation, message, minimum, maximum));

        // Cast the input to correct data type and return
        if (type instanceof Integer) {
            return (T) (Object) (Integer.parseInt(input));
        } else if (type instanceof Double) {
            return (T) (Object) (Double.parseDouble(input));
        } else if (type instanceof Character) {
            return (T) (Object) input.charAt(0);
        }
        return (T) (Object) input;
    }

    /**
     * Validate user input
     *
     * @param INPUT input of user
     * @param validation integer that represent validation required
     * @param message value that require user to input
     * @param minimum minimum value
     * @param maximum maximum value
     * @return whether the input is valid
     */
    public static boolean validateInput(final String INPUT, final int validation, final String message, final int minimum, final int maximum) {

        // -1 represent return to previous menu or sub-menu
        if (INPUT.equals("-1")) {
            return true;
        }

        try {
            switch (validation) {

                // Validate the max value of input
                case MAX: {
                    double input = Double.parseDouble(INPUT);
                    if (input > maximum) {
                        System.out.println("\nNote: " + message + " cannot be greater than " + maximum);
                        return false;
                    }
                }
                break;

                // Validate the min and max value of input
                case MIN | MAX: {
                    double input = Double.parseDouble(INPUT);
                    if (input > maximum) {
                        System.out.println("\nNote: " + message + " cannot be greater than " + maximum);
                        return false;
                    }

                    if (input < minimum) {
                        System.out.println("\nNote: " + message + " cannot be less than " + minimum);
                        return false;
                    }
                }
                break;

                // Validate the maximum character in a string
                case MAXLENGTH:
                    if (INPUT.length() > maximum) {
                        System.out.println("\nNote: " + message + " cannot be longer than " + maximum + " characters.");
                        return false;
                    }
                    break;

                // Validate the minimum and maximum character in a string
                case MINLENGTH | MAXLENGTH: {
                    int length = INPUT.length();
                    if (length > maximum) {
                        System.out.println("\nNote: " + message + " cannot be longer than " + maximum + " characters.");
                        return false;
                    }

                    if (length < minimum) {
                        System.out.println("\nNote: " + message + " cannot be shorter than " + minimum + " characters.");
                        return false;
                    }
                }
                break;

                // Validate the minimum and maximum character in a name. A name can contain only alphabet
                case MINLENGTH | MAXLENGTH | NAME: {
                    int length = INPUT.length();
                    if (length > maximum) {
                        System.out.println("\nNote: " + message + " cannot be longer than " + maximum + " characters.");
                        return false;
                    }

                    if (length < minimum) {
                        System.out.println("\nNote: " + message + " cannot be shorter than " + minimum + " characters.");
                        return false;
                    }

                    if (!(Pattern.matches("[A-Za-z ]+", INPUT))) {
                        System.out.println("\nNote: " + message + " must contain only alphabet.");
                        return false;
                    }
                }
                break;

                case CONFIRMATION: {
                    int length = INPUT.length();
                    if (length != 1) {
                        System.out.println("\nNote: Enter \"y\" or \"n\" only.");
                        return false;
                    }
                }
            }
        } // Validate whether user input string to an integer or double
        catch (NumberFormatException e) {
            System.out.println("\nNote: " + message + " must only contain digit.");
            return false;
        }

        // If user's input is valid, return true
        return true;
    }

    /**
     * Clear screen
     */
    public static void clearScreen() {
        for (int i = 0; i < 5; i++) {
            System.out.println();
        }
    }

    /**
     * Print the main menu of lab tests automation system
     */
    public static void printMainMenu() {
        System.out.println("Lab Tests Automation System");
        System.out.println("\t1) Question Bank");
        System.out.println("\t2) Student Maintenance");
        System.out.println("\t3) Examination");
        System.out.println("\t4) Exit");
    }

    /**
     * Save student file to binary file
     *
     * @param path student binary file path
     * @param student student list
     */
    public static void saveFile(final String path, final ArrayList<Student> student) {

        File file = new File(path);
        try (
                FileOutputStream fo = new FileOutputStream(file);
                ObjectOutputStream output = new ObjectOutputStream(fo);) {

            // Serialize the student object and write into binary file
            for (Student object : student) {
                output.writeObject(object);
            }

            output.close();
            fo.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Read student object from binary file
     *
     * @param path student binary file path
     * @return student list
     */
    public static void readFile(final String path, ArrayList<Student> student) {
        Student s;
        File file = new File(path);

        try (
                FileInputStream fi = new FileInputStream(file);
                ObjectInputStream input = new ObjectInputStream(fi);) {

            while (true) {
                // Read  student object in the binary file and unserialize it 
                s = (Student) input.readObject();

                // Get the next available matrix ID so that it can be used while adding student
                Student.assignMatrix(s.getStudentId());

                // Add student object to array list.
                student.add(s);
            }

        } catch (EOFException ex) {

        } catch (FileNotFoundException ex) {
            System.out.println("Warning: File has not been found.");
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Load student maintenance module
     *
     * @param student Student list
     */
    public static void loadStudentMaintenance(ArrayList<Student> student) {
        int option = 0;     // Store user's option in the sub-menu

        do {
            // Print Student maintenance menu and prompt user to input his/her option
            printStudentMaintenanceMenu();
            option = getInput(INT, "Enter", "option", "", MIN | MAX, 1, 5);

            switch (option) {
                case 1:
                    viewStudent(student);
                    break;
                case 2:
                    addStudent(student);
                    break;
                case 3:
                    updateStudent(student);
                    break;
                case 4:
                    deleteStudent(student);
                    break;
            }
        } while (option != 5);
    }

    /**
     * Print menu of student maintenance module
     */
    public static void printStudentMaintenanceMenu() {
        System.out.println("Student Maintenance");
        System.out.println("\t1) View student");
        System.out.println("\t2) Add student");
        System.out.println("\t3) Update student");
        System.out.println("\t4) Delete student");
        System.out.println("\t5) Return");
    }

    /**
     * Add student module
     *
     * @param student student list
     */
    public static void addStudent(ArrayList<Student> student) {
        String name = "";
        String studentId = "";
        final double MARK = -1.0;
        clearScreen();

        // Repeat until user enter -1
        do {
            // Print header
            System.out.println("Add Student Detail");
            System.out.println("------------------");

            // Print student list
            System.out.println("Student List");
            Student.printStudentList(student);

            // Prompt user to enter the name of student that wish to add
            name = getInput(STRING, "Enter", "student name", "to add [\"-1\" to cancel]", MINLENGTH | MAXLENGTH | NAME, 1, 30);

            // Check whether user cancel the add student process
            if (!name.equals("-1")) {

                // Create a new student with the name given and add to array list
                Student newStudent = new Student(name, MARK);
                student.add(newStudent);

                // Save the students file in binary file
                saveFile("student.bin", student);

                // Show user's the output after adding a student
                System.out.println("Student List");
                Student.printStudentList(student);
            }
            clearScreen();
        } while (!name.equals("-1"));
    }

    /**
     * Update student detail module
     *
     * @param student student list
     */
    public static void updateStudent(ArrayList<Student> student) {
        String criteria = "";       // Store the search criteria
        double mark = 0.0;          // Store the mark 
        Student foundStudent = null;// Store the student object after seaching
        clearScreen();

        // Repeat until user enter -1
        do {
            // Print header
            System.out.println("Update Student Detail");
            System.out.println("---------------------");

            // Print student list
            System.out.println("Student List");
            Student.printStudentList(student);

            // Prompt user to input student name or id
            criteria = Project.getInput(STRING, "Enter", "Student Name Or ID", "[\"-1\" to cancel]", MINLENGTH | MAXLENGTH, 1, 30);

            // Check whether user cancel the update process
            if (!criteria.equals("-1")) {

                // Search student base on the criteria
                foundStudent = Student.searchStudent(criteria, student);

                // Check whether the student is existing
                if (foundStudent != null) {

                    // Store user's option in update menu
                    int option = 0;

                    // Repeat until user enter 3
                    do {
                        clearScreen();
                        System.out.println();
                        System.out.println();

                        // Print founded student's detail
                        System.out.println("Student Information");
                        foundStudent.printStudentDetail();

                        // Print update menu and prompt user to enter his/her option
                        System.out.println("Update " + foundStudent.getName() + "'s Information");
                        System.out.println("\t1) Update Student Name");
                        System.out.println("\t2) Update Student Mark");
                        System.out.println("\t3) Done");
                        option = getInput(INT, "Enter", "Option", "", MIN | MAX, 1, 3);

                        switch (option) {
                            case 1:
                                // Get the new name for founded student
                                String name = getInput(STRING, "Enter", "Student Name", "[\"-1\" to cancel]", MINLENGTH | MAXLENGTH | NAME, 1, 30);
                                if (!name.equals("-1")) {
                                    foundStudent.setName(name);
                                }
                                break;

                            case 2:
                                // Get the new mark for founded student
                                mark = getInput(DOUBLE, "Enter", "Student Mark", "[\"-1\" represent no mark]", MIN | MAX, -1, 100);
                                foundStudent.setMark(mark);
                                break;
                        }

                        //  Save the changes to binary file
                        if (option == 1 || option == 2) {
                            saveFile("student.bin", student);
                        }
                    } while (option != 3);
                }
            }
            clearScreen();
        } while (!criteria.equals("-1"));
    }

    /**
     * Print a list of students' detail
     *
     * @param student student list
     */
    public static void viewStudent(final ArrayList<Student> student) {
        Scanner sc = new Scanner(System.in);
        clearScreen();

        // Print header
        System.out.println("View Student Detail");
        System.out.println("-------------------");

        // Print student list
        System.out.println("Student List");
        Student.printStudentList(student);

        System.out.print("Press any key to return to sub-menu...");
        sc.nextLine();
        clearScreen();
    }

    /**
     * Delete student detail
     *
     * @param student student list
     */
    public static void deleteStudent(ArrayList<Student> student) {
        String criteria = "";       // Store the seearching criteria
        Student foundStudent = null;// Store the student object after searching
        clearScreen();

        // Repeat until user enter -1
        do {
            // Print header
            System.out.println("Delete Student Detail");
            System.out.println("---------------------");

            //Print student list
            System.out.println("Student List");
            Student.printStudentList(student);

            // Prompt user to enter name or ID.
            criteria = Project.getInput(STRING, "Enter", "Student Name Or ID", "[\"-1\" to cancel]", MINLENGTH | MAXLENGTH, 1, 30);

            // Search student based on the criteria
            foundStudent = Student.searchStudent(criteria, student);

            // Check whether the student is existing
            if (foundStudent != null) {
                System.out.println();
                System.out.println();

                // Print founded student's details
                System.out.println("Student Information");
                foundStudent.printStudentDetail();

                // Ask user whether confirm to delete the student
                char yesNo = Character.toUpperCase(getInput(CHAR, "Confirm delete " + foundStudent.getName() + " [Y/N]", "", "", CONFIRMATION, 0, 0));

                // Remove the student if user confirm the delete process
                if (yesNo == 'Y') {
                    student.remove(foundStudent);
                    saveFile("student.bin", student);
                    System.out.println("Note: " + criteria + " had been removed from the database");
                }
            }
            clearScreen();
        } while (!criteria.equals("-1"));
    }

    /**
     * Question bank module
     */
    public static void loadQuestionBank(QuestionBank questionBank) {
        int option = 0; //Store the option 
        do {
            // Print question bank menu and prompt user to enter his/her option
            printQuestionBankMenu();
            option = getInput(INT, "Enter", "Option", "", MIN | MAX, 1, 3);

            switch (option) {
                case 1:
                    loadQuestion(questionBank);
                    break;

                case 2:
                    viewQuestion(questionBank);
                    break;
            }
        } while (option != 3);
    }

    /**
     * Print all question from questions bank.
     *
     * @param questionBank an object that store all the questions
     */
    public static void viewQuestion(final QuestionBank questionBank) {
        clearScreen();

        // Notify user if current questionbank does not contain any question
        if (questionBank.getQuestion() == null) {
            System.out.println("Note: Current question bank is empty. Please load question before using this option.");
        } else {
            // Print header
            System.out.println("View Question");
            System.out.println("-------------");

            // Print question
            questionBank.viewQuestion();

            //Print footer
            System.out.println("~~End Of Question~~");
            clearScreen();
        }
    }

    /**
     * Read all the questions from given file.
     *
     * @param questionBank
     */
    public static void loadQuestion(QuestionBank questionBank) {
        clearScreen();

        // Print header
        System.out.println("Load Question");
        System.out.println("-------------");

        // Check whether the current question bank is empty
        if (questionBank.getQuestion() == null) {
            System.out.println("Note: Current question bank is empty");
        } else {
            System.out.println("Current question bank is loading " + questionBank.getTitle() + ". ");
        }

        String path = "";           // The path of the question bank
        boolean isInvalid = false;  // Check whether the file given contains at least one valid question

        // Repeat until the question bank file given is valid or user cancel the process
        do {
            isInvalid = false;  // Assign false as the initial value 

            // Prompt user to enter the file path of question bank
            path = getInput(STRING, "Enter new", "Question bank file name", "[\"-1\" to cancel]", 0, 0, 0);

            // Check whether the user cancel the load question process
            if (!path.equals("-1")) {

                // Load all the question from the file path given
                questionBank.loadQuestion(path);

                // Check whether the question bank contain at least one question
                if (questionBank.getQuestion() != null) {

                    // Prompt user to enter the title of the question bank
                    questionBank.setTitle(getInput(STRING, "Enter", "Title of question", "[\"-1\" to cancel]", 0, 0, 0));

                    // Check whether user cancel the load question process
                    if (questionBank.getTitle().equals("-1")) {
                        // Clear all the preloaded question
                        questionBank.resetQuestionBank();
                    } else {
                        System.out.print("Questions loaded successfully. Total Question: " + questionBank.getQuestion().length + ".");
                    }
                } else {
                    // Notify user if the question bank given is empty
                    System.out.println("Note: There are no valid question in " + path + ".txt.");
                    System.out.println("Please select another question banks file.");

                    // Clear all the preloaded question
                    questionBank.resetQuestionBank();
                    isInvalid = true;
                }
            }
            clearScreen();
        } while (isInvalid);
    }

    /**
     * Print question bank menu
     */
    public static void printQuestionBankMenu() {
        System.out.println("Question Bank");
        System.out.println("\t1)Load Question");
        System.out.println("\t2)Display Question Details");
        System.out.println("\t3)Return");
    }

    /**
     * Load examination module
     *
     * @param student student list
     */
    public static void loadExamination(LabTest labTest) {
        int option = 0; // Store the user's option at the load examination menu

        do {
            // Print examination menu and prompt user to enter his/her option
            printExaminationMenu();
            option = getInput(INT, "Enter", "Option", "", MIN | MAX, 1, 5);

            switch (option) {
                case 1:
                    createExamination(labTest);
                    break;
                case 2:
                    startExamination(labTest);
                    break;
                case 3:
                    exportResult(labTest);
                    break;
                case 4:
                    processResult(labTest);
                    break;
            }
            clearScreen();
        } while (option != 5);
    }

    /**
     * Export result to exam.txt
     *
     * @param labTest An object that aggregate all other objects
     */
    public static void exportResult(final LabTest labTest) {

        // Check whether the result of examination is available
        if (labTest.getExam() != null) {
            // Export result to exam.txt
            labTest.getExam().exportResult();

            // Show users the output after export
            System.out.printf("Result export successfully. You can view the result of %s in exam.txt file.\n", labTest.getExam().getExamName());
        } else {
            // Notify user if no available result
            System.out.println("Note: No result available. Please start an examination.");
        }
    }

    /**
     * Print examination main menu
     */
    public static void printExaminationMenu() {
        System.out.println("Examination");
        System.out.println("\t1)Create Examination");
        System.out.println("\t2)Start Examination");
        System.out.println("\t3)Export Result");
        System.out.println("\t4)Process Result");
        System.out.println("\t5)Return");
    }

    /**
     * Create examination.
     *
     * @param labTest An object that aggregate all other objects
     */
    public static void createExamination(LabTest labTest) {
        clearScreen();
        // Print header
        System.out.println("Create Examination");
        System.out.println("------------------");

        if (labTest.getExam() != null) {
            System.out.println("Note: There are an existing examination named " + labTest.getExam().getExamName() + ".");

            char yesNo = Character.toUpperCase(getInput(CHAR, "If you create a new examination, the existing examination will be overwritten. Proceed? [Y/N]", "", "", CONFIRMATION, 0, 0));
            if (yesNo != 'Y') {
                return;
            }
        }

        // Assign the value to corresponding object 
        QuestionBank question = labTest.getQuestionBank();
        ArrayList<Student> student = labTest.getStudent();

        // Get the total number of student
        int NUM_OF_STUDENT = student.size();

        // Check whether the question is available
        if (question.getQuestion() == null) {
            // Notify user if the question bank is empty
            System.out.println("Note: Current question bank is empty. Please load questions to question bank at Question Bank option in main menu.");

        } else if (NUM_OF_STUDENT == 0) {
            // Notify user if no student available in database
            System.out.println("Note: There are no student in the database. Please add student before creating an examination.");

        } else {
            String examName = "";   // store the exam name

            // Prompt user to enter exam name
            examName = getInput(STRING, "Enter", "Exam Name", "[\"-1\" to cancel]", 0, 0, 0);

            // Check whether user cancel the create examination process
            if (!examName.equals("-1")) {

                // Prompt user on how many question that they want for the test
                final int NUM_OF_QUESTION = getInput(INT, "Enter", "Number of Question", String.format("[1 - %d]", question.getQuestion().length), MIN | MAX, 1, question.getQuestion().length);

                // Check whether user cancel the create examination process
                if (NUM_OF_QUESTION == -1) {
                    return;
                }

                // Prompt user to enter the number of student that will sit the exam
                final int NUM_OF_TEST = getInput(INT, "Enter", "Number of Student", String.format("[1 - %d]", student.size()), MIN | MAX, 0, student.size());

                // Check whether user cancel the create examination process 
                if (NUM_OF_TEST == -1) {
                    return;
                }

                // If the current system have exam, clear it
                if (labTest.getExam() != null) {
                    labTest.getExam().clearExamination();
                }

                // Create exam object 
                labTest.setExam(new Examination(examName, NUM_OF_TEST, NUM_OF_QUESTION));
                labTest.getExam().loadQuestion(question);

                // Show the output after creating examination 
                System.out.println("Exam created successfully");
            }
        }
    }

    /**
     * Start examination
     *
     * @param labTest An object that aggregate all other objects
     */
    public static void startExamination(LabTest labTest) {
        clearScreen();

        // Print header
        System.out.println("Start Examination");
        System.out.println("-----------------");

        // Store existing examination object
        Examination exam = labTest.getExam();

        try {
            exam.getTest()[0].getStudent();
            System.out.println("Note: All student had answered the test.");

        } catch (NullPointerException ex) {
            // Check whether the exam is available
            if (exam != null) {
                String id = "";     // Store the student id

                // Store the existing object
                Test test[] = exam.getTest();
                Question question[] = exam.getQuestion();

                // Store the number of question and student
                final int NUM_OF_QUESTION = question.length;
                final int NUM_OF_STUDENT = exam.getTest().length;

                // Repeat untill all the student have completed the examination
                for (int i = 0; i < NUM_OF_STUDENT;) {

                    // Prompt student to enter their student id and convert it to uppercase
                    id = getInput(STRING, "Enter", "Student ID", "", MINLENGTH | MAXLENGTH, 7, 7);
                    id = id.toUpperCase();

                    // Search student based on the student id given 
                    Student student = Student.searchStudent(id, labTest.getStudent());

                    //Check whether the student is existing
                    if (student != null) {

                        // Check whether the student had sit the exam
                        if (validateStudentExam(id, test)) {

                            // Store the answer of student
                            String answer[] = new String[NUM_OF_QUESTION];
                            Scanner sc = new Scanner(System.in);

                            // Print the greeting and rule of examination
                            System.out.println("Welcome " + student.getName());
                            System.out.println("Rule and Regulation");
                            System.out.println("-------------------");
                            System.out.println(" 1. This test consists of " + NUM_OF_QUESTION + " questions. Please answer all the questions with the option given.");
                            System.out.println(" 2. This test allows only one attempt. No redo and resubmission.");
                            System.out.println(" 3. This test has no time limitation.");
                            System.out.println(" 3. Once you has started the test, no pause is permited.");
                            System.out.println(" 4. You must switch off all mobile phones prior to answering the test.");
                            System.out.println("  ~~ GOOD LUCK ~~\n");
                            System.out.print("Press enter key to start the exam...");
                            sc.nextLine();
                            clearScreen();

                            // Repeat until user answered all the question
                            for (int j = 0; j < NUM_OF_QUESTION; j++) {

                                // Print question
                                System.out.printf("\nQuestion %d/%d\n\n", j + 1, NUM_OF_QUESTION);
                                System.out.print(j + 1 + ".");
                                System.out.println(question[j]);

                                // Store the student answer temporarily 
                                char temp = '\0';

                                // Repeat until user enter valid answer
                                do {

                                    // Get answer from student and make sure user enter valid answer only
                                    temp = Character.toUpperCase(getInput(CHAR, "Enter", "Answer", "", MINLENGTH | MAXLENGTH, 1, 1));
                                    if (temp < 'A' || temp > 'D') {
                                        System.out.println("\nNote: Please enter A,B,C or D only.");
                                    }
                                } while (temp < 'A' || temp > 'D');

                                // Store the temporary answer in student answer
                                answer[j] = String.valueOf(temp);
                                System.out.println();
                            }

                            // Create a new test and assign student's detial and answer
                            test[i] = new Test();
                            test[i].setStudent(student);
                            test[i].setAnswer(answer);

                            // Save the test information
                            exam.setTest(test);

                            // Represent the current number of student who has done the examination
                            i++;

                            // Print output to student after they completed the exam
                            System.out.println("You have answered all the questions and may leave now...");
                            sc.nextLine();
                            clearScreen();
                        } else {
                            // Notify user if they enter redundant student id
                            System.out.println("Note: " + student.getName() + " had sit the exam.\n");
                        }
                    }
                }
                System.out.println("Examination ended. ");
            } else {
                // Notify user if there are no available examination
                System.out.println("Note: No Examination. Please create an examination.");
            }
        }
    }

    /**
     * Validate whether the student had sit the examination
     *
     * @param id student id
     * @param test an Test object that all the test
     * @return whether the student had sit the examination
     */
    public static boolean validateStudentExam(final String id, final Test[] test) {
        // Store the number of test
        final int LENGTH = test.length;

        // Check whether the student had sit the examination
        for (int i = 0; i < LENGTH && test[i] != null; i++) {
            if (id.equals("P" + test[i].getStudent().getStudentId())) {
                return false;
            }
        }
        return true;
    }

    public static void processResult(LabTest labTest) {

        // Read data in exam.txt and store them in exam object
        Examination exam = readResult();
        if (exam == null) {
            return;
        }

        // Store the test object of exam object
        Test[] test = exam.getTest();

        // Store the number of question and number of student
        final int NUM_OF_QUESTION = exam.getQuestion().length;
        final int NUM_OF_STUDENT = test.length;

        // Create 2D arrays to store the output that are going to display. 
        String[][] output1 = new String[NUM_OF_STUDENT][NUM_OF_QUESTION + 1];
        String[][] output2 = new String[NUM_OF_STUDENT][2];

        // Store the mark per each question
        final double MARK_PER_QUESTION = 100 / NUM_OF_QUESTION;

        // Store the students' id and answer into output1
        for (int i = 0; i < NUM_OF_STUDENT; i++) {

            // Store student ID
            output1[i][0] = "P" + test[i].getStudent().getStudentId();

            // Initial mark of every student
            double mark = 0.0;

            // Store the student's answer into output1. Append correct answer if student's answer is wrong.
            for (int j = 0; j < NUM_OF_QUESTION; j++) {
                output1[i][j + 1] = "";
                output1[i][j + 1] += String.format("%2d", j + 1) + ") ";
                output1[i][j + 1] += test[i].getAnswer()[j];

                // Increment the mark of student base on the mark per question
                if (test[i].getAnswer()[j].equals(String.valueOf(exam.getQuestion()[j].getAnswer()))) {
                    mark += MARK_PER_QUESTION;
                } else {
                    // Append correct answer
                    output1[i][j + 1] += " (" + exam.getQuestion()[j].getAnswer() + ")";
                }
            }

            //Search and set the mark of student 
            Student s = Student.searchStudent("P" + test[i].getStudent().getStudentId(), labTest.getStudent());
            if (s != null) {
                s.setMark(mark);
            }

            // Store the student id and mark into output2
            output2[i][0] = "Student " + (i + 1) + " (P" + test[i].getStudent().getStudentId() + "):";
            output2[i][1] = String.format("%7.2f", mark) + "% ";

            // Append grade to output2
            if (mark >= 90) {
                output2[i][1] += 'A';
            } else if (mark >= 80) {
                output2[i][1] += 'B';
            } else if (mark >= 70) {
                output2[i][1] += 'C';
            } else if (mark >= 60) {
                output2[i][1] += 'D';
            } else {
                output2[i][1] += 'E';
            }
        }

        // Save student information as mark has changed
        saveFile("student.bin", labTest.getStudent());

        // Print output1 and output2 to result.txt
        File myFile = new File("result.txt");
        try (PrintWriter pw = new PrintWriter(myFile)) {
            //Print header
            pw.println(exam.getExamName());
            pw.println("Student Answer:");

            // Print student answer along with correct answer
            for (int i = 0; i < Math.ceil(NUM_OF_STUDENT / 3.0); i++) {
                for (int j = 0; j < NUM_OF_QUESTION + 1; j++) {
                    for (int k = i * 3; k < 3 * (i + 1) && k < NUM_OF_STUDENT; k++) {
                        pw.printf("%-15s", output1[k][j]);
                    }
                    pw.println();
                }
                pw.println();
            }

            // Print the grade and mark of student
            for (int i = 0; i < NUM_OF_STUDENT; i++) {
                for (int j = 0; j < 2; j++) {
                    pw.print(output2[i][j]);
                }
                pw.println();
            }

            // Print the output after processed result.
            System.out.println("Result processed successfully. You can view the report in result.txt");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Read exam.txt
     *
     * @return exam object that contain the test
     */
    public static Examination readResult() {
        File myFile = new File("exam.txt");         // Create a new file
        Examination exam = new Examination();       // Crate a new examination object
        ArrayList<Test> test = new ArrayList<>();   // Create an arraylist to store test object
        int numOfQuestion = 0;                      // Count the total number of question

        try (
                FileReader fr = new FileReader(myFile);
                Scanner sf = new Scanner(fr);) {

            String tempAnswer = "";                 // Store correct answer
            int numOfStudent = 0;                   // Store the number of student

            exam.setExamName(sf.nextLine());        // Read exam name from file and store as exam name
            numOfQuestion = Integer.parseInt(sf.nextLine());    // Read the number of question
            tempAnswer = sf.nextLine();                         // Read correct answer
            Question question[] = new Question[numOfQuestion];  // Create a question object array base on the number of question 

            for (int i = 0; i < numOfQuestion; i++) {
                // Store the correct answer into test as answer
                question[i] = new Question(tempAnswer.charAt(i));
            }

            // Set the question object of exam object 
            exam.setQuestion(question);

            // Read all line from the file
            while (sf.hasNext()) {

                String[] answer = new String[numOfQuestion];    // Create an String array to store the answer

                Student s = new Student(sf.nextLine());         // Create a student object with the id in the file
                String temp = sf.nextLine();                    // Read student's answer

                for (int i = 0; i < numOfQuestion; i++) {
                    answer[i] = String.valueOf(temp.charAt(i)); // Store all the student answer 
                }

                // Create a new test object and assign its value base on the file
                Test t = new Test();
                t.setStudent(s);
                t.setAnswer(answer);
                test.add(t);

                // Increment the number of student by 1
                numOfStudent++;
            }

            // Store the test object in arraylist
            final int SIZE = test.size();
            Test[] finalTest = new Test[SIZE];
            for (int i = 0; i < SIZE; i++) {
                finalTest[i] = test.get(i);
            }
            exam.setTest(finalTest);
        } catch (NoSuchElementException ex) {
            System.out.println("The exam.txt is empty. Please export result before using this option.");
            return null;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return exam;
    }
}
