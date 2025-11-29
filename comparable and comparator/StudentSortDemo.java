//Write a program in java to sort Student objects by roll using Comparable and by CGPA using a Comparator.
import java.util.*;  
// java.util প্যাকেজ ইমপোর্ট করা হয়েছে কারণ এখানে List, ArrayList, Collections, Comparator ব্যবহার করা হবে।

// Student ক্লাস তৈরি করা হচ্ছে
class Student implements Comparable<Student> {  
    // Comparable ইন্টারফেস ইমপ্লিমেন্ট করা হয়েছে যাতে Student অবজেক্টকে roll অনুযায়ী সাজানো যায়।

    int roll;        // প্রতিটি Student-এর roll নম্বর
    String name;     // Student-এর নাম
    double cgpa;     // Student-এর CGPA

    // Constructor: নতুন Student অবজেক্ট বানানোর সময় roll, name, cgpa সেট করা হবে
    public Student(int roll, String name, double cgpa) {
        this.roll = roll;
        this.name = name;
        this.cgpa = cgpa;
    }

    // Comparable ইন্টারফেসের compareTo() মেথড override করা হয়েছে
    @Override
    public int compareTo(Student a) {
        // এখানে roll অনুযায়ী তুলনা করা হচ্ছে
        // this.roll ছোট হলে আগে আসবে, বড় হলে পরে আসবে
        return Integer.compare(this.roll, a.roll);
    }
//     - compareTo() সবসময় int রিটার্ন করে → negative, zero, positive।
// - Collections.sort() এই রিটার্ন ভ্যালু দেখে সাজায়।
// - ছোট আগে আনতে চাইলে → Integer.compare(this.roll, a.roll)।
// - বড় আগে আনতে চাইলে → Integer.compare(a.roll, this.roll) বা -Integer.compare(this.roll, a.roll)।


    // toString() মেথড override করা হয়েছে যাতে Student অবজেক্ট প্রিন্ট করলে সুন্দরভাবে তথ্য দেখা যায়
    @Override
    public String toString() {
        return "Roll: " + roll + ", Name: " + name + ", CGPA: " + cgpa;
    }
    //• - "Roll: " + roll 	 → String এর সাথে roll ভ্যালু যোগ হচ্ছে।
    //• - ", Name: " + name	 → String এর সাথে name ভ্যালু যোগ হচ্ছে।
    //• - ", CGPA: " + cgpa	 → String এর সাথে cgpa ভ্যালু যোগ হচ্ছে।
    //• 	সবগুলো মিলিয়ে একটি বড় String তৈরি হচ্ছে।
    // কিভাবে কাজ করে: System.out.println(StudentObject); কল করলে এই মেথডটি অটোমেটিকলি কল হয় এবং Student এর তথ্য সুন্দরভাবে প্রিন্ট হয়।
    //- System.out.println(object) → আসলে object.toString() কল করে।
    //যদি তুমি  override না করো, তখন অবজেক্ট প্রিন্ট করলে এরকম কিছু দেখাবে: Student@5a07e868
    //   Student → ক্লাসের নাম
    // • @5a07e868	 → অবজেক্টের hashcode (মেমরির রেফারেন্সের মতো একটা সংখ্যা)
    // এটা মানুষের পড়ার মতো তথ্য নয়, শুধু ডিবাগিং-এর কাজে লাগে।


}

// Comparator ক্লাস তৈরি করা হচ্ছে CGPA অনুযায়ী সাজানোর জন্য
class CgpaComp implements Comparator<Student> {
    @Override
    public int compare(Student s1, Student s2) {
        // Double.compare ব্যবহার করা হয়েছে CGPA তুলনা করার জন্য
        // এখানে s2 আগে রাখা হয়েছে যাতে বড় CGPA আগে আসে (descending order)
        return Double.compare(s2.cgpa, s1.cgpa);
    }
}

// মূল ক্লাস যেখানে main() মেথড থাকবে
public class StudentSortDemo {
    public static void main(String[] args) {
        // Student অবজেক্ট রাখার জন্য একটি ArrayList তৈরি করা হচ্ছে
        List<Student> students = new ArrayList<>();

        // কিছু Student অবজেক্ট লিস্টে যোগ করা হচ্ছে
        students.add(new Student(102, "Rahim", 3.75));
        students.add(new Student(101, "Karim", 2.90));
        students.add(new Student(103, "Anika", 3.60));
        students.add(new Student(104, "Sadia", 3.95));

        // মূল লিস্ট প্রিন্ট করা হচ্ছে (যেভাবে যোগ করা হয়েছে)
        System.out.println("Original List:");
        for (Student s : students) {
            System.out.println(s);
        }

        // Comparable ব্যবহার করে roll অনুযায়ী sort করা হচ্ছে
        Collections.sort(students);
        System.out.println("\nSorted by Roll (Comparable):");
        for (Student s : students) {
            System.out.println(s);
        }

        // Comparator ব্যবহার করে CGPA অনুযায়ী sort করা হচ্ছে
        Collections.sort(students, new CgpaComp());
        System.out.println("\nSorted by CGPA (Comparator):");
        for (Student s : students) {
            System.out.println(s);
        }
        System.out.println(students.get(0));  // প্রথম Student-এর নাম প্রিন্ট করবে, n dile nth student er name dibe
        System.out.println(students.size());       // লিস্টে কয়টা Student আছে তা দেখাবে

    }
}