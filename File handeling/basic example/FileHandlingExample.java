import java.io.File;          // Import File class for file operations
import java.io.FileWriter;    // Import FileWriter to write into a file
import java.io.IOException;   // Import IOException for handling file errors
import java.util.Scanner;     // Import Scanner to read from the file

public class FileHandlingExample {

    public static void main(String[] args) {

        try {
            // -----------------------------------
            // STEP 1: CREATE A FILE
            // -----------------------------------

            File myFile = new File("example.txt");   // Create File object with file name

            if (myFile.createNewFile()) {            // Try to create the file
                System.out.println("Now The File Created: " + myFile.exists()); // Confirm file exists
                System.out.println("File Name: " + myFile.getName());           // Print file name
                System.out.println("Absolute Path: " + myFile.getAbsolutePath()); // Print full path
                System.out.println("File Size in Bytes: " + myFile.length());   // Print file size
                System.out.println("File is Readable: " + myFile.canRead());    // Check readability
                System.out.println("File is Writeable: " + myFile.canWrite());  // Check writability
            } else {
                System.out.println("File already exists.");                     // If exists, notify
            }

            // ✅ IMPORTANT:
            // Scanner(System.in) MUST NOT be inside try-with-resources
            // Otherwise System.in closes → file cannot be deleted on Windows
            Scanner input = new Scanner(System.in);   // Scanner for user input (kept open)

            // -----------------------------------
            // STEP 2: WRITE MULTIPLE LINES (OVERWRITE MODE)
            // -----------------------------------

            try (FileWriter writer = new FileWriter(myFile)) {  // FileWriter auto-closes

                System.out.println("Enter multiple lines (press Enter on empty line to stop):");

                while (true) {
                    String line = input.nextLine();   // Read user input

                    if (line.isEmpty()) {             // Stop on empty line
                        break;
                    }

                    writer.write(line + "\n");        // Write each line to the file
                }

                System.out.println("Successfully wrote to the file."); // Confirm writing
                System.out.println("File Size in Bytes: " + myFile.length()); // Show size
            }

            // -----------------------------------
            // STEP 3: READ FROM THE FILE
            // -----------------------------------

            System.out.println("\nReading file content:");

            try (Scanner reader = new Scanner(myFile)) {  // Scanner auto-closes

                while (reader.hasNextLine()) {            // Loop until no more lines
                    String data = reader.nextLine();      // Read one line
                    System.out.println(data);             // Print the line
                }
            }

            // -----------------------------------
            // STEP 4: DELETE THE FILE
            // -----------------------------------

            if (myFile.delete()) {                        // Try to delete the file
                System.out.println("\nFile deleted successfully."); // Success message
            } else {
                System.out.println("\nFailed to delete the file."); // Failure message
            }

            // ✅ DO NOT CLOSE input (System.in)
            // input.close();  // <-- NEVER DO THIS

        } catch (IOException e) {                         // Catch file-related errors
            System.out.println("An error occurred.");     // Print error message
            e.printStackTrace();                          // Print detailed error info
        }
    }
}