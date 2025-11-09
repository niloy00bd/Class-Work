import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a character: ");
        char ch  = scanner.next().charAt(0);

        
            System.out.printf("%d", ch);
        

        scanner.close();
    }
}