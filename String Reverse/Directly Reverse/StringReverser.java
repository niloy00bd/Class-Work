public class StringReverser {
    String reverseString(String original) {
        // 1. Create a new StringBuilder object, initialized with the original string.
        StringBuilder sb = new StringBuilder(original);
        
        // 2. Use the built-in reverse() method.
        sb.reverse();
        
        // 3. Convert the StringBuilder back to a String.
        return sb.toString();
    }

    public static void main(String[] args) {
        String original = "hello world";
        StringReverser reverser = new StringReverser();
        String reversed = reverser.reverseString(original);
        
        System.out.println("Original: " + original); // Output: Original: hello world
        System.out.println("Reversed: " + reversed); // Output: Reversed: dlrow olleh
    }
}