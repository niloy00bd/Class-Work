//Create a class InvalidMarksException which extends the Exception class. The InvalidMarksException is
//thrown when an invalid marks (less than zero or greater than one hundred) has been entered as an input.

// This class defines a custom exception named InvalidMarksException
class InvalidMarksException extends Exception {

    // This is the default constructor of the exception
    // It calls the parent Exception class with a default error message
    public InvalidMarksException() {
        super("Invalid marks entered. Marks must be between 0 and 100.");
    }

    // This is a parameterized constructor
    // It allows passing a custom error message when throwing the exception
    // public InvalidMarksException(String message) {
    //     super(message);
    // }
}

// This is the main class where we test the custom exception
public class StudentMarksTest {

    // This method checks whether the marks are valid
    // It declares that it may throw InvalidMarksException
    static void checkMarks(int marks) throws InvalidMarksException {

        // This condition checks if marks are less than 0 or greater than 100
        if (marks < 0 || marks > 100) {

            // If invalid, throw the custom exception with a custom message
            throw new InvalidMarksException();
        }

        // If marks are valid, print them
        System.out.println("Valid marks: " + marks);
    }

    // This is the main method — the entry point of the program
    public static void main(String[] args) {
    //public static void main(String[] args)  throws InvalidMarksException{ 
    //evabe diye try catch dileo hobe na dileo hobe

        // Start of try block to catch exceptions
        try {

            // Calling checkMarks with an invalid value (120)
            // This will cause the exception to be thrown
            checkMarks(120);

            // Calling checkMarks with a valid value (85)
            // This will print "Valid marks: 85"
            checkMarks(85);//jehetu uporer line e exception peye jabe tai eta r check korbena. er agei ber hoye jabe

        } catch (InvalidMarksException e) {

            // This block runs when InvalidMarksException is thrown
            // It prints the exception message
            System.out.println("Exception caught: " + e.getMessage());
            //System.out.println("Exception caught: " + e);
        }
    }
}