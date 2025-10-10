
public class SecondSmallest
{
	public static void main(String[] args) {
	    int[] num = {5,2,3,2,5,3,7,8};
	    int n = 8;
	    int low = 0;
	    for(int i = 1; i<n; i++){
	        if(num[i]<num[low]){
	            low=i;
	        }
	    }
	    int secsmall;
	    if(low!=0){
	        secsmall=0;
	    }
	    else {
	        secsmall=1;
	    }
	    
	    for(int i = 1; i<n; i++){
	        if(num[i]<num[secsmall] && num[i]>num[low]){
	            secsmall=i;
	        }
	    }
		System.out.printf("Second smallest = %d\n", num[secsmall]);
		
		//for sorting
		for(int i=0; i<n-1; i++){
		    for(int j=0; j<n-1-i; j++){
		        if(num[j]>num[j+1]){
		            int temp = num[j+1];
		            num[j+1]=num[j];
		            num[j]=temp;
		        }
		    }
		}
		
		System.out.printf("Sorted array:\n");
		for(int i = 0; i<n; i++){
		    System.out.printf("%d .", num[i]);
		}
	}
}