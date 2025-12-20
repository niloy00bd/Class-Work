//Instead of synchronizing an entire method, Java allows synchronization on specific 
//blocks of code. This improves performance by locking only the necessary section.
class Counter{

    private int c = 0;

    public void inc(){

        // Synchronize only this block
        synchronized (this) { c++; }
    }

    public int get() { return c; }
}

public class Geeks {

    public static void main(String[] args)
        throws InterruptedException{
            
        Counter cnt = new Counter();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++)
                cnt.inc();
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++)
                cnt.inc();
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("Counter: " + cnt.get());
    }
}