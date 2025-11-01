public class OverloadExample {

    // Method 1: int + int
    public int add(int a, int b) {
        System.out.println("Called add(int, int)");
        return a + b;
    }

    

    // Method 2: double + double
    public int add(double a, double b) {
        System.out.println("Called add(double, double)");
        return (int)(a + b);
    }

    // Method 3: float + float
    public int add(float a, float b) {
        System.out.println("Called add(float, float)");
        return (int)(a + b);
    }

    // Method 4: int and double
    public void add(int a, double b) {
        System.out.println("Called add(int, int)");
    }
    public static void main(String[] args) {
        OverloadExample obj = new OverloadExample();

        System.out.println("Result: " + obj.add(5, 10));         // int, int
        System.out.println("Result: " + obj.add(5.5, 3.3));       // double, double
        System.out.println("Result: " + obj.add(2.5f, 4.5f));     // float, float
        obj.add(7, 2.2);
    }
}