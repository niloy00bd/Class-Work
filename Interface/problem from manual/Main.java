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
                //myArray[i].soundSiren(); not possible Because myArray is an array of Object, the compiler only
				// knows that myArray[i] is an object.
 				// object does not have a method named soundSiren(), so Java throws a compile‑time error.
				//- Java only allows calling methods that the declared type supports
				//shoja kothay myArray[i] er type ekhon Object. tai amader type casting korte hobe
				//type casting kore amra bolte pari je eta ekhon isEmergency type er.
				((isEmergency)myArray[i]).soundSiren();
				((FireEmergency)myArray[i]).soundSiren();
            } else {
                System.out.println(myArray[i].getClass().getSimpleName() + " does NOT implement IsEmergency");
            }
	    }
	}
}