import java.util.Scanner;

public class DaysConverter {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input total number of days
        System.out.print("Enter total number of days: ");
        int totalDays = scanner.nextInt();

        // Conversion logic
        int years = totalDays / 365;
        int remainingDaysAfterYears = totalDays % 365;

        int weeks = remainingDaysAfterYears / 7;
        int days = remainingDaysAfterYears % 7;

        // Output result
        System.out.println("Equivalent time:");
        System.out.println("Years: " + years);
        System.out.println("Weeks: " + weeks);
        System.out.println("Days: " + days);

        scanner.close();
    }
}