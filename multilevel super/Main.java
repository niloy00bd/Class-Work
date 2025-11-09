// Grandparent class
class Animal {
    void sound() {
        System.out.println("Animal makes sound");
    }
}

// Parent class
class Dog extends Animal {
    @Override
    void sound() {
        super.sound(); // ✅ Animal এর sound() call করছে
        System.out.println("Dog barks");
    }
    void eat() {
        System.out.println("Dog eats");
    }
}

// Child class
class DogChild extends Dog {
    @Override
    void sound() {
        super.sound(); // ✅ Dog এর sound() call করছে, যা আবার Animal এর sound() call করে
        System.out.println("DogChild whines");
        super.eat(); // ✅ Dog এর eat() call করছে
    }

    void testSuperUsage() {
        // super = Dog, so only Dog's accessible members can be called directly
        super.sound(); // ✅ works, calls Dog's overridden sound()

        // super.eat(); ❌ যদি Dog বা Animal-এ eat() না থাকে, তাহলে compile error হবে

        // super.super.sound(); ❌ Java-তে super.super নেই, তাই grandparent কে সরাসরি call করা যাবে না
    }
}

// Main class to run
public class Main {
    public static void main(String[] args) {
        DogChild dc = new DogChild();
        dc.sound(); // Output: Animal → Dog → DogChild
        System.out.println("\nTesting super usage:\n");
        dc.testSuperUsage();
    }
}

// ❌ super.super.method() → Java-তে নেই, তাই grandparent কে সরাসরি call করা যাবে না
// ❌ private method → parent class-এর private method subclass থেকে super দিয়ে access করা যাবে না
// ❌ static method → super দিয়ে static method call করা যায় না, className.method() ব্যবহার করতে হয়
// ❌ constructor chaining → যদি parent constructor explicitly না থাকে, super() call করলে error হবে
// ❌ inaccessible members → যদি parent class-এর method/package-private হয় এবং subclass অন্য package-এ থাকে