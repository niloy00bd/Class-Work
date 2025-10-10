import java.io.*;
import java.util.*;
class Check{
    //checking result is perfect or not
    public static void perfect (int num){
        
        int result=0;
        for (int i=1; i<num; i++){
            if(num % i == 0){
                result=result+i;
            }
        }
        if(result==num){
            System.out.printf("perfect\n");
        }
        else{
            System.out.printf("not perfect\n");
        }
    }
    
    //checking result is prime or not
    public static void prime (int num){
        int prime=1;
        for (int i=2; i<num; i++){
            if(num % i == 0){
                prime=0;//not prime
                System.out.printf("not prime\n");
                return;
            }
        }
        
        System.out.printf("prime\n");
    }
    
    //checking result is strong or not
    public static void strong (int num){
        
        int result=0;
        for (int i=num; i>0; i=i/10){
            int fact=1;
            for(int j=1; j<=i%10; j++){
                fact = fact * j;
            }
            result = result + fact;
        }
        if(result==num){
            System.out.printf("strong\n");
        }
        else{
            System.out.printf("not strong\n");
        }
    }

    //checking sum of factorial of each digit
    public static void sum (int num){
        
        int result=0;
        for (int i=num; i>0; i=i/10){
            int fact=1;
            for(int j=1; j<=i%10; j++){
                fact = fact * j;
            }
            result = result + fact;
        }
        System.out.printf("The sum of factorial of each digit is %d\n", result);
    }
}
class NumberCheck {
    
    
    public static void main (String[] args) {
        Scanner scan = new Scanner(System.in);
        int head = 0;
        while (head!=5){
            System.out.print("Enter a Number = ");
            int num = scan.nextInt();
            System.out.printf("1. Check Perfect\n2. Check Prime\n3. Check Strong\n4. sum of factorial of each digit\n5. Exit\nEnter Your Choice: ");
            int s = scan.nextInt();
            switch (s){
                case 1:
                    Check.perfect(num);
                    break;
                    case 2:
                        Check.prime(num);
                        break;
                        case 3:
                            Check.strong(num);
                            break;
                            case 4:
                                Check.sum(num);
                                break;
                                case 5:
                                    head = 5;
                                    break;
                                    default:
                                        System.out.printf("Wrong Input Try again\n");
                                        break;
            }
        }
        
        
    }
}