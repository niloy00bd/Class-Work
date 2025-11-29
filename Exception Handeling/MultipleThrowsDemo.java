import java.io.*;
import java.sql.*;

public class MultipleThrowsDemo {

    // Method declares it may throw IOException and SQLException
    public static void processData(String fileName) throws IOException, SQLException {
        // File handling → IOException হতে পারে
        FileReader fr = new FileReader(fileName);
        BufferedReader br = new BufferedReader(fr);

        String line = br.readLine();
        System.out.println("File content: " + line);

        br.close();
        fr.close();

        // Database handling → SQLException হতে পারে
        throw new SQLException("Database error occurred!");
    }

    public static void main(String[] args) {
        try {
            processData("test.txt");
        } 
        catch (IOException e) {
            System.out.println("Handled IOException: " + e.getMessage());
        } 
        catch (SQLException e) {
            System.out.println("Handled SQLException: " + e.getMessage());
        }
    }
}