class Counter {

    private int c = 0;

    public synchronized void inc() {
        c++;
    }

    public synchronized int get() {
        return c;
    }
}

class CounterTask implements Runnable {

    private Counter cnt;

    public CounterTask(Counter cnt) {
        this.cnt = cnt;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            cnt.inc();
        }
    }
}

public class Geeks {

    public static void main(String[] args) {

        Counter cnt = new Counter();

        Thread t1 = new Thread(new CounterTask(cnt));
        Thread t2 = new Thread(new CounterTask(cnt));

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Counter: " + cnt.get());
    }
}