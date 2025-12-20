// Shared data holder class
class DataBox {
    private int data;          // যে ডাটা Producer রাখবে এবং Consumer নেবে
    private boolean hasData = false; // ডাটা আছে কি না তার ফ্ল্যাগ

    // Producer এই method দিয়ে data রাখে
    public synchronized void put(int value) {

        // যদি আগের data Consumer এখনো consume না করে থাকে → Producer অপেক্ষা করবে
        while (hasData) {
            try {
                wait();        // lock ছেড়ে দিয়ে waiting state এ যায়
            } catch (InterruptedException e) {}
        }

        data = value;          // নতুন data সেট করা হলো
        hasData = true;        // এখন data আছে
        System.out.println("Produced: " + value);

        notify();              // Consumer কে জানানো হলো—data ready
    }

    // Consumer এই method দিয়ে data নেয়
    public synchronized int get() {

        // যদি data না থাকে → Consumer অপেক্ষা করবে
        while (!hasData) {
            try {
                wait();        // lock ছেড়ে দিয়ে waiting state এ যায়
            } catch (InterruptedException e) {}
        }

        hasData = false;       // data পড়ে নেওয়া হলো, এখন box খালি
        System.out.println("Consumed: " + data);

        notify();              // Producer কে জানানো হলো—box খালি, নতুন data দিতে পারো
        return data;           // Consumer data রিটার্ন করছে
    }
}

public class Main {
    public static void main(String[] args) {

        DataBox box = new DataBox();  // Shared object যেটা Producer ও Consumer দুজনেই ব্যবহার করবে

        // Producer Thread তৈরি
        Thread producer = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {  // ৫টা data produce করবে
                box.put(i);                 // DataBox এ data রাখা
            }
        });

        // Consumer Thread তৈরি
        Thread consumer = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {  // ৫টা data consume করবে
                box.get();                  // DataBox থেকে data নেওয়া
            }
        });

        producer.start();   // Producer thread শুরু
        consumer.start();   // Consumer thread শুরু
    }
}