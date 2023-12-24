import java.util.ArrayList;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        initialPrompt();
    }

    public static void initialPrompt() {
        Scanner sc = new Scanner(System.in);
        showHelpMessages(0);
        String command = sc.nextLine().trim();

        if (command.equals("login")) {
            showLoginPromt();
        } else if (command.equals("exit")) {
            checkExit(command, 0);
        } else if (command.equals("signup")) {
            signup();
        } else {
            System.err.println("'" + command + "' is not a recognized command.");
            initialPrompt();
        }
    }

    static void signup() {

        // Username
        Scanner sc = new Scanner(System.in);
        System.out.println("Type Username: ");
        String username = sc.nextLine();

        // Password
        Scanner sc2 = new Scanner(System.in);
        System.out.println("Type Password: ");
        String pass = sc.nextLine();

        // Name
        Scanner sc3 = new Scanner(System.in);
        System.out.println("Type Name: ");
        String name = sc.nextLine();

        // Role
        Scanner sc4 = new Scanner(System.in);
        System.out.println("Type role: ");
        String role = sc.nextLine();

        if ( User.addUser(username, pass, name, role) ) {
            System.out.println("Successfully Signed Up.");
            authenticate(username, pass);
        } else {
            System.err.println("Signup failed!");
            System.out.println("Wanna try again? type 'yes'");
            Scanner s = new Scanner(System.in);
            String c = s.nextLine();

            if ( c.equals("yes") ) {
                signup();
            } else {
                initialPrompt();
            }

        }
    }

    static void showCourseAdvisingPrompt() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Type the Course Code:");
        String cCode = sc.nextLine();


        Scanner sc2 = new Scanner(System.in);
        System.out.println("Type the Course Name:");
        String cName = sc2.nextLine();

        Scanner sc3 = new Scanner(System.in);
        System.out.println("Type the Student Id:");
        int cSid = sc.nextInt();

        String line = cCode + "," + cName + "," + cSid + "\n";
        if ( Student.addCourses(line) ) {
            System.out.println("Course advised successfully");
        } else {
            System.out.println("Course advising failed!");
        }
    }

    static  void showLoginPromt() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your username:");
        String username = sc.nextLine();

        Scanner sc2 = new Scanner(System.in);
        System.out.println("Enter your Password:");
        String pass = sc2.nextLine();
        authenticate(username, pass);
    }

    static void authenticate(String username, String pass) {
        if ( User.authenticate(username, pass) ) {
            System.out.println("You have successfully logged in.");
            showHelpMessages(1);
            continueWorks();
        } else {
            System.err.println("Authentication Error! Please type the correct username and password. or type 'exit' to terminate the program.");
            initialPrompt();
        }
    }

    static void getConsent(int stage) {
        Scanner sc = new Scanner(System.in);
        String c = sc.nextLine();
        if ( c.equals("yes") ) {
            System.out.println("Terminating the program...");
            System.exit(-1);
        } else if (c.equals("no")) {
            if ( stage == 0 ) {
                initialPrompt();
            } else if ( stage == 1 ) {
                continueWorks();
            }
        } else {
            System.err.println(c + " is not a recognized command.");
            System.out.println("Type either 'yes' or 'no'.");
            getConsent(stage);
        }
    }

    static void checkExit(String command, int stage) {
        if ( command.equals("exit") ) {
            System.out.println("Do you really want to terminate the program? Type 'yes' otherwise 'no'.");
            getConsent(stage);
        } else {
            if ( stage == 0 ) {
                initialPrompt();
            } else if ( stage == 1 ) {
                continueWorks();
            }
        }
    }

    private static void continueWorks() {
        String dec = getDecision();
        if (dec.equals("add")) {
            addStudent();
            continueWorks();
        } else if (dec.equals("show")) {
            showStudents();
            continueWorks();
        } else if (dec.equals("help")) {
            showHelpMessages(1);
            continueWorks();
        } else if ( dec.equals("logout") ) {
            logout();
        } else if (dec.equals("s")) {
            searchPrompt();
            continueWorks();
        } else if (dec.equals("advice")) {
            showCourseAdvisingPrompt();
            continueWorks();
        } else {
            System.err.println("'" + dec + "' is not a recognized command.");
            continueWorks();
        }
        continueWorks();
    }

    static String getDecision() {
        Scanner sc = new Scanner(System.in);
        String decision = sc.nextLine().toLowerCase().trim();
        switch (decision) {
            case "add":
                return "add";
            case "show":
                return "show";
            case "exit":
                checkExit(decision, 1);
            case "help":
                return "help";
            case "s":
            case "search":
                return "s";
            case "advice":
            case "advice course":
                return "advice";
            case "logout":
                return "logout";
            default:
                System.err.println("'" + decision + "' is not a recognized command.");
                getDecision();
        }
        return "";
    }
    static void addStudent() {
        Student st = new Student();
        String[] data = studentprompt().split(",");
        boolean isStudentAdded = st.addStudent(Integer.parseInt(data[0]), data[1], data[2], data[3], data[4], Double.parseDouble(data[5]));
        if (isStudentAdded) {
            System.out.println("Student added successfully.");
        } else {
            System.out.println("Student record is not added.");
        }
    }

    static void showStudents() {
        ArrayList<Student> students =  Student.getStudents();
        System.out.println("Showing all student records.");
        System.out.println("=============================");
        System.out.println("=============================");
        for ( Student s : students ) {
            StringBuilder sb = new StringBuilder();
            sb.append("ID: ").append(s.getId()).append(" | ");
            sb.append("Name: ").append(s.getName()).append(" | ");
            sb.append("Program: ").append(s.getProgram()).append(" | ");
            sb.append("Batch: ").append(s.getBatch()).append(" | ");
            sb.append("CGPA: ").append(s.getCGPA()).append(" | ");
            sb.append("Assigned Courses:");
            try {
                ArrayList<String> advisedCourses = Student.courseAdvisor(s.getId());

                if (advisedCourses.size() > 0) {
                    int counter = 0;
                    for ( String c : advisedCourses ) {
                        String[] courses = c.split(",");
                        if (advisedCourses.size() - counter > 0) {
                            sb.append(" ");
                        }
                        sb.append("(");
                        sb.append(courses[1]);
                        sb.append(" ");
                        sb.append(courses[0]);
                        sb.append(")");
                        counter++;
                    }
                } else {
                    sb.append(" No courses advised");
                }
            } catch (NullPointerException ignored) {}
            System.out.println( sb.toString() );
            System.out.print("---------------------------------");
            System.out.println("---------------------------------");
        }
        System.out.println("=============================");
        System.out.println("=============================");
    }

    static void showHelpMessages(int stage) {
        if (stage == 1) {
            System.out.println("add - To add a student record to the database. A prompt will allow you to add student records.");
            System.out.println("show - To show all the student records from the database.");
            System.out.println("help - Listing all the available commands.");
            System.out.println("logout - Logs out from the application");
            System.out.println("search | s - search student with his id");
            System.out.println("advice course | advice - Advising a course to a student");
            System.out.println("exit - Terminating the program.");
        } else {
            System.out.println("Type 'login' to access the admin information\n" +
                    "Type 'signup' to add admin user\n" +
                    "Or type 'exit' to terminate the program.");
        }
    }

    static void logout() {
        System.out.println("Logging out...");
        System.out.println("logout successful!");
        initialPrompt();
    }
    static String studentprompt() {

        // ID
        Scanner inpt1 = new Scanner(System.in);
        System.out.println("Type Student ID:");
        int id = inpt1.nextInt();

        // Password
        Scanner inpt2 = new Scanner(System.in);
        System.out.println("Type Student Password:");
        String pass = inpt2.nextLine();

        // Name
        Scanner inpt3 = new Scanner(System.in);
        System.out.println("Type Student Name:");
        String name = inpt3.nextLine();

        // Program
        Scanner inpt4 = new Scanner(System.in);
        System.out.println("Type Student Program:");
        String program = inpt4.nextLine();

        // Batch
        Scanner inpt5 = new Scanner(System.in);
        System.out.println("Type Student Batch:");
        String batch = inpt5.nextLine();

        // CGPA
        Scanner inpt6 = new Scanner(System.in);
        System.out.println("Type Student CGPA:");
        double cgpa = inpt6.nextDouble();

        String data = id + "," + pass + "," + name + ",";
        data += program + "," + batch + ",";
        data += cgpa;

        return data;

    }

    static void searchPrompt() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Type the student ID");
        int id = sc.nextInt();

        ArrayList<Student> students = Student.getStudents();
        for ( Student s : students ) {
            if (  id == s.getId() ) {
                System.out.print("---------------------------------");
                System.out.println("---------------------------------");
                StringBuilder sb = new StringBuilder();
                sb.append("ID: ").append(s.getId()).append(" | ");
                sb.append("Name: ").append(s.getName()).append(" | ");
                sb.append("Program: ").append(s.getProgram()).append(" | ");
                sb.append("Batch: ").append(s.getBatch()).append(" | ");
                sb.append("CGPA: ").append(s.getCGPA()).append(" | ");
                sb.append("Assigned Courses: ");
                try {
                    ArrayList<String> advisedCourses = Student.courseAdvisor(s.getId());

                    if (advisedCourses.size() > 0) {
                        for ( String c : advisedCourses ) {
                            String[] courses = c.split(",");
                            sb.append(" (");
                            sb.append(courses[1]);
                            sb.append(" ");
                            sb.append(courses[0]);
                            sb.append(")");
                        }
                    } else {
                        sb.append("No courses advised");
                    }
                } catch (NullPointerException ignored) {}
                System.out.println( sb.toString() );
                System.out.print("---------------------------------");
                System.out.println("---------------------------------");
                break;
            }
        }
    }
}