//call by referance example
public class practice2 {
    int data = 50;

    void change(practice2 obj , int newValue) {
        obj.data = obj.data + newValue; // changes will be in the instance variable
        //method er parameter hisabe shorashori object pass kora hoyeche amra parameter e jekono name use korte pari
        //karon ekhane change shorashori object e hobe
        //change(practice2 p , int newValue) eivabeo lekha jeto
        //muloto argument e jei object pass kora hoyeche shorashori sei object er data change hobe
    }

    public static void main(String args[]) {
        practice2 op = new practice2();//created object
        System.out.println("before change " + op.data);
        op.change(op , 500);//passing object as an argument
        System.out.println("after first change " + op.data);
        
        practice2 op2 = new practice2();//created another object
        op2.change(op2 , 500);//passing new object as an argument
        System.out.println("after second change " + op2.data); //same output as first change
        //karon notun object er data o 50 theke start hobe

        practice2 op3 = new practice2();//created another object
        op3.change(op3 , op2.data);//passing new object as an argument with value from previous object
        System.out.println("after third change " + op3.data);
    }
}