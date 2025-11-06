/**
 * Java class to demonstrate manual string reversal using a while loop
 * and in-place character swapping.
 */
public class StringReverserWhileLoop {

    /**
     * Reverses the input string by converting it to a character array
     * and swapping characters from the start and end until the pointers meet.
     *
     * @param original The string to be reversed.
     * @return The reversed string.
     */
    public static String reverseStringManual(String original) {
        // Handle edge cases (null or empty string)
        if (original == null || original.isEmpty()) {
            return original;
        }

        // Convert the string to a mutable character array
        char[] charArray = original.toCharArray();

        // Initialize two pointers: 'left' at the start and 'right' at the end
        int left = 0;
        int right = charArray.length - 1;

        // Use a while loop to swap characters until the pointers cross
        while (left < right) {
            // 1. Store the character at the left pointer temporarily
            char temp = charArray[left];

            // 2. Move the character from the right pointer to the left position
            charArray[left] = charArray[right];

            // 3. Move the stored left character to the right position
            charArray[right] = temp;

            // 4. Move the pointers inward
            left++;
            right--;
        }

        // Convert the modified character array back into a new String
        return new String(charArray);
    }

    public static void main(String[] args) {
        String testString1 = "The quick brown fox.";
        String reversed1 = reverseStringManual(testString1);
        System.out.println("Original 1: " + testString1);
        System.out.println("Reversed 1: " + reversed1 + "\n");
        // Output: .xof nworb kciuq ehT

        String testString2 = "12345";
        String reversed2 = reverseStringManual(testString2);
        System.out.println("Original 2: " + testString2);
        System.out.println("Reversed 2: " + reversed2);
        // Output: 54321
    }
}
