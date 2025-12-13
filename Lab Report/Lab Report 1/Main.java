class Employee {
    String name;
    int id;
    int basicSalary;

    Employee(String name, int id, int basicSalary) {
        this.name = name;
        this.id = id;
        this.basicSalary = basicSalary;
    }

    void displayInfo() {
        System.out.println("Class Employee:");
        System.out.println("Name: " + name);
        System.out.println("ID: " + id);
        System.out.println("Basic Salary: " + basicSalary);
    }
}

class Manager extends Employee {
    String department;

    Manager(String name, int id, int basicSalary, String department) {
        super(name, id, basicSalary);
        this.department = department;
    }

    void displayInfo() {
        System.out.println("Class Manager:");
        System.out.println("Name: " + name);
        System.out.println("ID: " + id);
        System.out.println("Basic Salary: " + basicSalary);
        System.out.println("Department: " + department);
    }
}

public class Main {
    public static void main(String[] args) {

        Employee emp = new Employee("niloy", 87, 25000);
        Manager mgr = new Manager("johir", 88, 45000, "IT");
        System.out.println("Displaying Information From Employee Class:");
        emp.displayInfo();
        System.out.println("Displaying Information From Manager Class:");
        mgr.displayInfo();
    }
}