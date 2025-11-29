/*
• Create an interface isEmergency with only one method - soundSiren which takes no arguments and returns
no value.
• Write a class FireEmergency that implements the IsEmergency interface. The soundSiren method should
print "Siren Sounded".
• Write a class SmokeAlarm that does not implement any interface. The class has an empty body.
• Create an array of Object class, myArray in the main method.
• Construct 2 SmokeAlarm object and add it to the array myArray in the main method.
• Construct 2 FireEmergency object and add it to the array myArray in the main method.
• In the main method, write a for loop, to print which array elements are instances of classes that implement
the IsEmergency interface and if so, call the soundSiren method.*/
interface isEmergency{
    void soundSiren();
}
class FireEmergency implements isEmergency{
    public void soundSiren(){
        System.out.println("Siren Sounded");
    }
}
class SmokeAlarm {
    
}

public class Main
{
	public static void main(String[] args) {
	    Object[] myArray = new Object[4];
	    myArray[0] = new SmokeAlarm();
	    myArray[1] = new SmokeAlarm();
	    myArray[2] = new FireEmergency();
	    myArray[3] = new FireEmergency();
	    for(int i=0; i<=3; i++){
	        if (myArray[i] instanceof isEmergency) {
                System.out.println(myArray[i].getClass().getSimpleName() + " implements IsEmergency:");
                ((isEmergency) myArray[i]).soundSiren();
            } else {
                System.out.println(myArray[i].getClass().getSimpleName() + " does NOT implement IsEmergency");
            }
	    }
	}
}