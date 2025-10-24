import java.util.*;

class fact {
    int fact(int n) {
        
        if (n == 1){
            return 1;
        }
        else{
            return n * fact(n - 1);
        }
        
    }
}


public class factorial {
    public static void main(String[] args) {
        fact f = new fact();
        int n, r;
        Scanner s = new Scanner(System.in);
        System.out.println("Enter a number to find factorial:");
        n = s.nextInt();
        r = f.fact(n);
        System.out.println(r);
    }
}