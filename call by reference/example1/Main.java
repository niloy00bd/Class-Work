public class Main {
    public static void main(String[] args) {
        Department d = new Department("CSE");
        Student s = new Student("Taskin", d);
        System.out.println(s.studentName + " studies in " + s.dept.name);
    }
}

class Department {
    String name;
    Department(String name) {
        this.name = name;
    }
}

class Student {
    Department dept;
    // Department dept = new Department("Unknown");
    // evabe dileo same e hobe. dept pore d diye replace hoye jabe.
    String studentName;
    Student(String name, Department d) {
        this.dept = d; // dept ke d diye initialize kora holo
        // orthat dept object er reference d object er reference diye set kora holo
        this.studentName = name;
    }
}