public class StringReverserManual {
    public static String reverseStringManual(String original) {
        // Handle null or empty input
        if (original == null || original.isEmpty()) {
            return original;
        }

        // Convert the string to a character array for easier iteration
        char[] characters = original.toCharArray();
        
        // Use a StringBuilder to efficiently build the reversed string
        StringBuilder reversed = new StringBuilder();

        // Iterate backward from the last character (length - 1) down to 0
        for (int i = characters.length - 1; i >= 0; i--) {
            reversed.append(characters[i]);
        }
        
        return reversed.toString();
    }

    public static void main(String[] args) {
        String original = "Java programming";
        String reversed = reverseStringManual(original);
        
        System.out.println("Original: " + original); // Output: Original: Java programming
        System.out.println("Reversed: " + reversed); // Output: Reversed: gnimmargorp avaJ
    }
}