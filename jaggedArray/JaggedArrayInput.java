package jaggedArray;
import java.util.*;

public class JaggedArrayInput {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Step 1: Total number of rows
        System.out.print("Enter number of rows: ");
        int rows = sc.nextInt();

        // Step 2: Declare jagged array
        int[][] jagged = new int[rows][];

        // Step 3: For each row, take column size and input values
        for (int i = 0; i < rows; i++) {
            System.out.print("Enter number of columns for row " + i + ": ");
            int cols = sc.nextInt();

            jagged[i] = new int[cols];  // allocate column size

            System.out.println("Enter " + cols + " elements for row " + i + ":");
            for (int j = 0; j < cols; j++) {
                jagged[i][j] = sc.nextInt();
            }
        }

        // Step 4: Print jagged array
        System.out.println("\nYour Jagged Array:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < jagged[i].length; j++) {
                System.out.print(jagged[i][j] + " ");
            }
            System.out.println();
        }

        sc.close();
    }
}
