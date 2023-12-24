import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class User {


    User() {
    }

    public static boolean addUser(String username, String pass, String name, String role) {
        try {
            String line = username + "," + pass + "," + name + "," + role + "\n";
            RandomAccessFile rf = new RandomAccessFile("users.txt", "rw");
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



    public static boolean authenticate(String username, String pass) {
        try {
            RandomAccessFile rf = new RandomAccessFile("users.txt", "r");
            String line;
            while ((line = rf.readLine()) != null) {
                String[] user = line.split(",");
                if (username.equals(user[0])) {
                    if (pass.equals(user[1])) {
                        return true;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not exist.");
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return false;
    }
}
