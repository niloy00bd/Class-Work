public class Student {
    String name;
    int age;

    Student(String name) {
        this(name, 18); // default age set করলাম
    }

    Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    void showInfo() {
        System.out.println("Name: " + name + ", Age: " + age);
    }

    public static void main(String[] args) {
        Student s = new Student("Johiru");
        s.showInfo(); // Output: Name: Johiru, Age: 18
    }
}