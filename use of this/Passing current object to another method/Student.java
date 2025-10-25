public class Student {
    void print(Student s) {
        System.out.println("Printing student object: " + s);
    }

    void callPrint() {
        print(this); // current object কে pass করলাম
    }

    public static void main(String[] args) {
        Student s = new Student();
        s.callPrint(); // Output: Printing student object: Student@someHash
    }
}