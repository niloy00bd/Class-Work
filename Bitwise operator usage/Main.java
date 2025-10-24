public class Main {
    public static void main(String[] args) {
        int a = 6;  // binary: 0110
        int b = 4;  // binary: 0100

        // Bitwise AND
        int andResult = a & b;  // 0110 & 0100 = 0100 (4)
        System.out.println("a & b = " + andResult);

        // Bitwise OR
        int orResult = a | b;   // 0110 | 0100 = 0110 (6)
        System.out.println("a | b = " + orResult);

        // Bitwise XOR
        int xorResult = a ^ b;  // 0110 ^ 0100 = 0010 (2)
        System.out.println("a ^ b = " + xorResult);

        // Bitwise NOT
        int notResult = ~a;     // ~0110 = 1001 (in 2's complement: -7)
        System.out.println("~a = " + notResult);

        // Left Shift
        int leftShift = a << 1; // 0110 << 1 = 1100 (12)
        System.out.println("a << 1 = " + leftShift);

        // Right Shift
        int rightShift = a >> 1; // 0110 >> 1 = 0011 (3)
        System.out.println("a >> 1 = " + rightShift);

        // Unsigned Right Shift
        int unsignedRightShift = a >>> 1; // same as >> for positive numbers
        System.out.println("a >>> 1 = " + unsignedRightShift);
    }
}