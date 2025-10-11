public class Main {
    public static void main(String[] args) {
        // Example 1: Square root
        double number = 49.0;
        //double sqrtResult = Math.sqrt(number);
        //System.out.println("Square root of " + number + " is " + sqrtResult);
        System.out.println(Math.sqrt(number));

        // Example 2: Power
        double base = 2.0;
        double exponent = 5.0;
        double powerResult = Math.pow(base, exponent);
        System.out.println(base + " raised to the power of " + exponent + " is " + powerResult);

        // Example 3: Negative input for sqrt
        double negativeNumber = -25.0;
        double sqrtNegative = Math.sqrt(negativeNumber);
        System.out.println("Square root of " + negativeNumber + " is " + sqrtNegative); // Will print NaN

        // Example 4: Fractional exponent (cube root)
        double cubeRoot = Math.pow(27.0, 1.0/3.0);
        System.out.println("Cube root of 27 is " + cubeRoot);
    }
}