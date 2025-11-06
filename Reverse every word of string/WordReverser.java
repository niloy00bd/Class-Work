/**
 * The WordReverser class demonstrates how to reverse the characters within
 * each word of a sentence while preserving the order of the words.
 *
 * Example: "You are good" becomes "uoY era doog"
 */
public class WordReverser {

    /**
     * Reverses the characters of each word in the input sentence.
     *
     * @param sentence The string to be processed (e.g., "Hello world").
     * @return The string with characters in each word reversed (e.g., "olleH dlrow").
     */
    public static String reverseCharactersInEachWord(String sentence) {
        // 1. Handle null or empty input string
        if (sentence == null || sentence.isEmpty()) {
            return sentence;
        }

        // 2. Split the sentence into an array of words using a space as a delimiter.
        // The -1 argument ensures that trailing empty strings (if the sentence ends with spaces) are included.
        String[] words = sentence.split(" ", -1);

        // 3. Initialize a StringBuilder to construct the final result efficiently.
        StringBuilder result = new StringBuilder();

        // 4. Iterate through each word
        for (int i = 0; i < words.length; i++) {
            String word = words[i];

            // 5. Use StringBuilder's reverse() method for efficient character reversal
            // and append the reversed word to the result.
            String reversedWord = new StringBuilder(word).reverse().toString();
            result.append(reversedWord);

            // 6. Append a space after every word except the last one.
            // This ensures we keep the original spacing structure.
            if (i < words.length - 1) {
                result.append(" ");
            }
        }

        return result.toString();
    }

    public static void main(String[] args) {
        String input1 = "You are good";
        String reversed1 = reverseCharactersInEachWord(input1);
        System.out.println("Original: \"" + input1 + "\"");
        System.out.println("Reversed: \"" + reversed1 + "\"");
        // Expected Output: "uoY era doog"

        System.out.println("-------------------------");

        String input2 = "Java is fun to code";
        String reversed2 = reverseCharactersInEachWord(input2);
        System.out.println("Original: \"" + input2 + "\"");
        System.out.println("Reversed: \"" + reversed2 + "\"");
        // Expected Output: "avaJ si nuf ot edoc"

        System.out.println("-------------------------");

        String input3 = "  Leading and Trailing spaces  ";
        String reversed3 = reverseCharactersInEachWord(input3);
        System.out.println("Original: \"" + input3 + "\"");
        System.out.println("Reversed: \"" + reversed3 + "\"");
        // This tests the handling of multiple and boundary spaces.
        // Expected Output: "  gnidaeL dna gnilairT secaps  "
    }
}