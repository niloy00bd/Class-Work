public class GfG {

    // Function to multiply two numbers represented as strings
    static String multiplyStrings(String s1, String s2) {

        // Store lengths of both strings
        int n1 = s1.length(), n2 = s2.length();

        // If either string is empty, multiplication is meaningless → return 0
        if (n1 == 0 || n2 == 0)
            return "0";
        
        // Check if strings are negative
        int nn = 1, mm = 1;   // nn = sign of s1, mm = sign of s2

        // If first character of s1 is '-', mark it negative
        if (s1.charAt(0) == '-')
            nn = -1;

        // If first character of s2 is '-', mark it negative
        if (s2.charAt(0) == '-')
            mm = -1;
        
        // Final sign of result = product of signs
        // (- * - = +), (- * + = -), (+ * + = +)
        int isNeg = nn * mm;
        
        // Result array will store digits of multiplication in reverse order
        // Maximum digits = n1 + n2
        int[] result = new int[n1 + n2];
        
        // i1 = position shift for s1 digits
        int i1 = 0;
        
        // i2 = position shift for s2 digits
        int i2 = 0;
        
        // Loop from rightmost digit of s1 → left
        for (int i = n1 - 1; i >= 0; i--) {

            // Skip minus sign if present
            if (s1.charAt(i) == '-')
                continue;

            // Carry for multiplication
            int carry = 0;

            // Convert char digit to integer
            int n1Digit = s1.charAt(i) - '0';

            // Reset i2 for each new digit of s1
            i2 = 0;
            
            // Loop from rightmost digit of s2 → left
            for (int j = n2 - 1; j >= 0; j--) {

                // Skip minus sign if present
                if (s2.charAt(j) == '-')
                    continue;

                // Convert char digit to integer
                int n2Digit = s2.charAt(j) - '0';
                
                // Multiply digits + existing result + carry
                int sum = n1Digit * n2Digit + result[i1 + i2] + carry;
                
                // Update carry for next iteration
                carry = sum / 10;
                
                // Store the last digit of sum in result array
                result[i1 + i2] = sum % 10;
                
                // Move to next position in result array
                i2++;
            }
            
            // After finishing inner loop, if carry remains, store it
            if (carry > 0)
                result[i1 + i2] += carry;
            
            // Move result index for next digit of s1
            i1++;
        }
        
        // Remove trailing zeros from the result array
        int i = result.length - 1;
        while (i >= 0 && result[i] == 0)
            i--;
        
        // If all digits were zero → result is 0
        if (i == -1)
            return "0";
        
        // Build the final result string (reverse of result array)
        String s = "";
        while (i >= 0) {
            int digit = result[i];          // array থেকে digit নেওয়া
            s = s + Integer.toString(digit); // string এ যোগ করা
            i = i - 1;                       // index এক ধাপ কমানো
        }
        
        // Add negative sign if needed
        if (isNeg == -1)
            s = "-" + s;
        
        // Return final multiplied string
        return s;
    }
    
    public static void main(String[] args) {

        // Example input strings
        String s1 = "123", s2 = "678";

        // Print multiplication result
        System.out.println(multiplyStrings(s1, s2));
    }
}