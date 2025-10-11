package app;
import java.io.*;
import java.util.*;
import multiply.Multiply;

public class Main {

    public static void main(String[] args) {
        Calculator calc = new Calculator();

        System.out.println("add(2, 3): " + calc.add(2, 3));               // int + int
        System.out.println("add(2, 3, 4): " + calc.add(2, 3, 4));         // int + int + int
        System.out.println("add(2.5, 3.5): " + calc.add(2.5, 3.5));       // double + double
        System.out.println("add(2, 3.55): " + calc.add(2, 3.55));           // int + double
        System.out.println("add float(2.5, 3.5, 5.1): " + calc.add((float)2.5, (float)3.5, (float)5.1)); //float+float+float
        
        Multiply cal = new Multiply();
        System.out.println("multiplication(2, 3): " + cal.mult(2, 3)); 
    }
}