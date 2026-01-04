class SharedResource {
    private int data;
    private boolean hasData = false;

    // Producer method
    public synchronized void produce(int value) {
        while (hasData) { // যদি data আগে থেকেই থাকে, অপেক্ষা করো
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        data = value;
        hasData = true;
        System.out.println("Produced: " + value);
        notify(); // Consumer কে জাগাও
    }

    // Consumer method
    public synchronized void consume() {
        while (!hasData) { // যদি data না থাকে, অপেক্ষা করো
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Consumed: " + data);
        hasData = false;
        notify(); // Producer কে জাগাও
    }
}

class Producer extends Thread {
    private SharedResource resource;

    Producer(SharedResource resource) {
        this.resource = resource;
    }

    public void run() {
        for (int i = 1; i <= 5; i++) {
            resource.produce(i);
        }
    }
}

class Consumer extends Thread {
    private SharedResource resource;

    Consumer(SharedResource resource) {
        this.resource = resource;
    }

    public void run() {
        for (int i = 1; i <= 5; i++) {
            resource.consume();
        }
    }
}

public class InterThreadDemo {
    public static void main(String[] args) {
        SharedResource resource = new SharedResource();
        Producer p = new Producer(resource);
        Consumer c = new Consumer(resource);

        p.start();
        c.start();
    }
}