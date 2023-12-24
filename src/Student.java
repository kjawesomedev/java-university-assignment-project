import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Student {
    private int id;
    private String name;

    private String program;

    private String batch;

    private String password;

    private double cgpa;


    Student() {

    }

    public void setId(int id) {
        this.id = id;
    }

    private void setPassword(String password) {
        this.password = password;
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setProgram(String program) {
        this.program = program;
    }

    private void setBatch(String batch) {
        this.batch = batch;
    }

    private void setCGPA(double cgpa) {
        this.cgpa = cgpa;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getProgram() {
        return program;
    }

    public String getBatch() {
        return batch;
    }

    public double getCGPA() {
        return cgpa;
    }

    public boolean addStudent(int id, String password, String name, String program, String batch, double cgpa) {
        try {
            String path = "students.txt";
            String record = id + "," + password + "," + name + "," + program + "," + batch + "," + cgpa + "\n";
            RandomAccessFile rf = new RandomAccessFile(path, "rw");
            rf.seek(rf.length());
            rf.writeBytes(record);
            return true;
        } catch (FileNotFoundException e) {
            System.err.println("File not found.");
        } catch (IOException e) {
            System.err.println("Failed to read the file");
        }
        return false;
    }


    public static ArrayList<Student> getStudents() {
        List<Student> studentList = new ArrayList<Student>();
        try {
            String path = "students.txt";
            RandomAccessFile rf = new RandomAccessFile(path, "r");
            String line;
            while((line = rf.readLine()) != null ) {
                String[] studentString = line.split(",");
                int id = Integer.parseInt(studentString[0]);
                String password = studentString[1];
                String name = studentString[2];
                String program = studentString[3];
                String batch = studentString[4];
                double cgpa = Double.parseDouble(studentString[5]);
                Student s = new Student();
                s.setId(id);
                s.setPassword(password);
                s.setName(name);
                s.setProgram(program);
                s.setBatch(batch);
                s.setCGPA(cgpa);
                studentList.add(s);
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found.");
        } catch (IOException e) {
            System.err.println("Failed to read the file");
        }

        return (ArrayList<Student>) studentList;
    }

    static ArrayList<String> courseAdvisor(int id) {
        try {
            ArrayList<String> courses = new ArrayList<String>();
            RandomAccessFile rf = new RandomAccessFile("courses.txt", "r");
            String line;
            while ((line = rf.readLine()) != null) {
                if ( id == Integer.parseInt(line.split(",")[2])) {
                    courses.add(line);
                }
            }
            return courses;
        } catch (FileNotFoundException e) {
            System.err.println("File doesn't exist.");
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return null;
    }

    static boolean addCourses(String line) {
        try {
            RandomAccessFile rf = new RandomAccessFile("courses.txt", "rw");
            rf.seek(rf.length());
            rf.writeBytes(line);
            rf.close();
            return true;
        } catch (FileNotFoundException e) {
            System.err.println("File doesn't exist.");
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return false;
    }



}
