// Task:
// Write a Java program that demonstrates basic multithreading with numerical calculations.
// Requirements:
// - Create two thread classes:
// - SumThread: Calculates and stores the sum of integers from 1 to 10.
// - ProductThread: Calculates and stores the product of integers from 1 to 5.
// - In the main method:
// - Instantiate both thread classes, passing the required range values through their constructors.
// - Start both threads using the start() method.
// - Use the join() method to ensure the main thread waits for both threads to finish.
// - After completion, retrieve and display the final sum and product computed by the respective threads.
// Thread class to calculate sum from 1 to 10
class SumThread extends Thread {
    int start, end;
    int sum;

    SumThread(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        sum = 0;
        for (int i = start; i <= end; i++) {
            sum += i;
        }
    }
}

// Thread class to calculate product from 1 to 5
class ProductThread extends Thread {
    private int start, end;
    private int product;

    public ProductThread(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getProduct() {
        return product;
    }

    @Override
    public void run() {
        product = 1;
        for (int i = start; i <= end; i++) {
            product *= i;
        }
    }
}

// Main class to run both threads
public class MultiThreadCalculation {
    public static void main(String[] args) {
        SumThread sumThread = new SumThread(1, 10);
        ProductThread productThread = new ProductThread(1, 5);

        sumThread.start();
        productThread.start();

        try {
            sumThread.join();
            productThread.join();
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted: " + e);
        }

        System.out.println("Sum from 1 to 10: " + sumThread.sum);
        System.out.println("Product from 1 to 5: " + productThread.getProduct());
    }
}