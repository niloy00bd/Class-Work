public class sum {
    int sum = 0;
    float avg = 1;
    void calculation (int ... a){
        for (int i=0 ; i<a.length ; i++){
            sum = sum + a[i];
        }
        avg = (float)sum / (float)a.length;
        System.out.println("average is : " + avg);
    }
    public static void main(String[] args){
        sum obj = new sum();
        obj.calculation(10,20,30,40,50);
        obj.calculation(15,25,35);
        obj.calculation(5,10,15,20,25,30,35);
    }
    }