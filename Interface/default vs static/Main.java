interface Greetable {
    default void display() {
        System.out.println("Hello from default");
    }

    static void displayStatic() {
        System.out.println("Hello from static");
    }
}

class Greeter implements Greetable {
    // override default method
    public void display() {
        System.out.println("Hello from Greeter");
    }
}

public class Main {
    public static void main(String[] args) {
        Greetable g = new Greeter();
        g.display(); // calls overridden default method
        //g.displayStatic();//class er khetre evabe call kora jeto. kintu interface er khetre na
        //Greeter.displayStatic();//etaw possible na
        //Greetable.display(); // calls default method via class instance.etaw possible na

        Greetable.displayStatic(); // calls static method via interface name
    }
}