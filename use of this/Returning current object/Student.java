public class Student {
    String name;

    Student(String name) {
        this.name = name;
    }

    Student getStudent() {
        return this; // current object return করলাম
    }

    void showName() {
        System.out.println("Name: " + name);
    }

    public static void main(String[] args) {
        Student s1 = new Student("Johiru");
        Student s2 = s1.getStudent(); // s2 now refers to same object as s1
        s2.showName(); // Output: Name: Johiru
    }
}