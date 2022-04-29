import java.io.Serializable;
import java.util.ArrayList;
import java.util.regex.*;

public class Student implements Serializable {

    private String name;                    // Student name
    private int studentId;                  // Stuedent ID
    private double mark;                    // Student mark
    private static int matrixNum = 100000;  // Class member that store the next available student ID

    /**
     * User defined constructor
     *
     * @param id student id
     */
    public Student(String id) {
        this.studentId = Integer.parseInt(id.substring(1));
    }

    /**
     * User defined constructor
     *
     * @param name student name
     * @param mark student mark
     */
    public Student(final String name, final double mark) {
        this.studentId = matrixNum;
        this.name = name;
        this.mark = mark;
        matrixNum++;
    }

    /**
     * @return Student ID
     */
    public int getStudentId() {
        return this.studentId;
    }

    /**
     * @return Student name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return Student mark
     */
    public double getMark() {
        return this.mark;
    }

    /**
     * Set student name
     *
     * @param name student name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Set student mark
     *
     * @param mark student mark
     */
    public void setMark(final double mark) {
        this.mark = mark;
    }

    /**
     * Set the next available student id
     *
     * @param num current student ID
     */
    public static void assignMatrix(int num) {
        matrixNum = ++num;
    }

    /**
     * Print header of student detail
     */
    public static void printStudentDetailHeader() {
        System.out.printf("%-5s%-15s%-30s%10s\n", "#", "Student ID", "Student Name", "Mark");
        System.out.println("------------------------------------------------------------");
    }

    /**
     * Search student by criteria
     *
     * @param criteria Searching criteria
     * @param student Student list
     * @return Student object
     */
    public static Student searchStudent(final String criteria, final ArrayList<Student> student) {
        //Check whether user cancel the search process
        if (criteria.equals(-1)) {
            return null;
        } else if (Pattern.matches("P[0-9]{6}", criteria)) {
            return searchStudentById(criteria, student);
        } else {
            return searchStudentByName(criteria, student);
        }
    }

    /**
     * Search student by student name
     *
     * @param name student name
     * @param student student list
     * @return Student
     */
    public static Student searchStudentByName(String name, final ArrayList<Student> student) {
        int count = 0;         // Store the index of student that match the criteria    
        int studentNo = 0;     // Store the index of foundStudent

        // Create an arraylist to store all the student that match the searching criteria
        ArrayList<Student> foundStudent = new ArrayList<>();

        // Search all student in the student list and store those who match the criteria in arraylist
        for (Student s : student) {
            String studentName = s.getName().toUpperCase();
            name = name.toUpperCase();

            // Check whether student name contain the criteria
            if (studentName.contains(name)) {
                foundStudent.add(student.get(count));
            }
            count++;
        }

        final int SIZE = foundStudent.size(); // Number of student that match the criteria 

        // Print header
        System.out.println("\n\nSearch Result");

        if (SIZE > 1) {
            // Print student list and prompt user to select student if found student is more than 1
            printStudentList(foundStudent);
            studentNo = Project.getInput(1, "Enter", "Student No", "[#]", 1 | 2, 1, SIZE) - 1;

            // Check whether user cancel the search process
            if (studentNo != -1) {
                return foundStudent.get(studentNo);
            } else {
                return null;
            }
        } else if (SIZE == 1) {
            return foundStudent.get(0);
        } else {
            // Notify user if cannot found the student
            System.out.println("Note: " + name + " was not found in the database.\n");
            return null;
        }
    }

    /**
     * Search student by student id
     *
     * @param id student id
     * @param student student list
     * @return student object
     */
    public static Student searchStudentById(String id, ArrayList<Student> student) {
        final int SIZE = student.size();    // Store the number of student in student list

        // Check the mathces student ID
        for (int i = 0; i < SIZE; i++) {
            if (id.equals("P" + student.get(i).getStudentId())) {
                return student.get(i);
            }
        }

        // Notify user if student ID cannot be found
        System.out.println("\nNote: " + id + " has not been found in the database.\n");

        return null;
    }

    /**
     * Print student list with ID, name and mark
     *
     * @param student Student list
     */
    public static void printStudentList(ArrayList<Student> student) {
        int count = 0;
        printStudentDetailHeader();
        for (Student s : student) {
            System.out.print(s.toString(++count));
        }
        System.out.println("[\"x\" represent no mark]");
        System.out.println("-------------------------END RESULT-------------------------");
        System.out.println();

    }

    /**
     * Print student detail
     */
    public void printStudentDetail() {
        printStudentDetailHeader();
        System.out.println(this);
    }

    @Override
    /**
     * Override toString method to print student detail
     *
     * @return student detail
     */
    public String toString() {
        String output = "";
        output += String.format("%-5s", "-");
        output += String.format("%-15s", "P" + studentId);
        output += String.format("%-30s", name);
        String temp = (mark < 0) ? "X" : String.format("%.2f", mark);
        output += String.format("%10s", temp);
        output += "\n";
        return output;
    }

    /**
     * Overloaded toString method to print student detail with index
     *
     * @param index counter
     * @return Student detail
     */
    public String toString(int index) {
        String output = "";
        output += String.format("%-5d", index);
        output += String.format("%-15s", "P" + studentId);
        output += String.format("%-30s", name);
        String temp = (mark < 0) ? "X" : String.format("%.2f", mark);
        output += String.format("%10s", temp);
        output += "\n";
        return output;
    }
}
