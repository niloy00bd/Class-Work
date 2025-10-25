// Parent class: Animal
class Animal {
    String name = "Generic Animal"; // Parent class variable

    void makeSound() {              // Parent class method
        System.out.println("Animal makes sound");
    }
}

// Child class: Dog extends Animal
class Dog extends Animal {
    String name = "Dog"; // Child class variable (same name as parent)

    Dog() {
        super(); // Calls parent class constructor (optional here since Animal has no explicit constructor)
        System.out.println("Dog constructor called");
    }

    void printNames() {
        System.out.println(name);       // Prints Dog's name
        System.out.println(super.name); // Uses 'super' to access Animal's name
    }

    void makeSound() {
        super.makeSound(); // Calls Animal's makeSound() method
        System.out.println("Dog barks"); // Adds Dog-specific behavior
    }
}

// Main class to run the program
public class Main {
    public static void main(String[] args) {
        Dog d = new Dog();       // Creates Dog object, calls constructor
        d.printNames();          // Shows both Dog and Animal names
        d.makeSound();           // Calls overridden method with super
        System.out.println(d.name); // Accesses Dog's name directly
        //System.out.println(d.super.name); // Accesses Animal's name using super 
        /*(❌ This line will cause a compile-time error  because 'super' cannot be used like this 
        outside of instance methods)*/
    }
}