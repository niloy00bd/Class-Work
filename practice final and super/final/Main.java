// Final class: can't be extended
class Vehicle {//class er shurute final dile oi class ke extend kora jabe na
//orthat final diye ja e declare kora hoy ta change(override) ba extend kora jabe na 
    int maxSpeed = 120; // Not Final variable: can be changed

    final void displaySpeed() { // Final method: can't be overridden
        System.out.println("Max speed: " + maxSpeed);
    }
}

class Car extends Vehicle {
    int maxSpeed = 150;//overriding variable possible
    
    // void displaySpeed(){
    //System.out.println("Modified speed"); // ❌ This would cause error: can't override final method
    //}
} 


// Main class to run the program
public class main{
    public static void main(String[] args) {
        Car c = new Car();       // Creates Car's object
        c.displaySpeed();        // Calls final method from Vehicle
        System.out.println("Car Max Speed: " + c.maxSpeed); // Accesses overridden variable
        c.displaySpeed();
    }
}