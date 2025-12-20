class Counter{
    
    // Shared variable
    private int c = 0; 

    // Synchronized method to increment counter
    public synchronized void inc(){
        c++; 
        
    }

    // Synchronized method to get counter value
    public synchronized int get(){
        return c; 
        
    }
}

public class Geeks{
    
    public static void main(String[] args){
        
        // Shared resource
        Counter cnt = new Counter(); 

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++)
                cnt.inc();
        });

        //new Thread(() -> { cnt.inc(); });
        //Java internally এটাকে এমন বানিয়ে ফেলে:
        //new Thread(new Runnable() {
        //     @Override
        //     public void run() {
        //         cnt.inc();
        //     }
        // });

        // () -> { ... } = Runnable এর run() method‑এর body
        // new Thread( ... ) = notun thread object create kore

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++)
                cnt.inc();
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Counter: " + cnt.get());
    }
}