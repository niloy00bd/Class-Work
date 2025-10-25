public class Student {
    String name;

    Student(String name) {
        this.name = name; // this.name → instance variable, name → constructor parameter
    }

    void showName() {
        System.out.println("Student name: " + name);
    }

    public static void main(String[] args) {
        Student s = new Student("Johiru");
        s.showName(); // Output: Student name: Johiru
    }
}