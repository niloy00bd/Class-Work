// Grandparent abstract class
abstract class GrandParent {
    abstract void greet(); // abstract method
}

// Parent class implements the abstract method
class Parent extends GrandParent {
    @Override
    void greet() {
        System.out.println("Hello from Parent");
    }
}

// Child class overrides the method again
class Child extends Parent {
    @Override
    void greet() {
        System.out.println("Hello from Child");
    }
}

// Main class to test
public class Main {
    public static void main(String[] args) {
        GrandParent obj1 = new Parent();
        obj1.greet(); // Output: Hello from Parent

        GrandParent obj2 = new Child();
        obj2.greet(); // Output: Hello from Child

        Parent obj3 = new Child();
        obj3.greet(); // Output: Hello from Child
    }
}