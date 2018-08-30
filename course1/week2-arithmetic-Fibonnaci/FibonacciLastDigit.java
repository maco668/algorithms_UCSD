import java.util.*;

public class FibonacciLastDigit {
    private static int getFibonacciLastDigit(int n) {
        
		long[] numbers = new long[n+1];
      
		int fibn = 1;
		int fibn_1 = 0;
		int fibn_2 = 0;
		int pcount = 1;
	  
	  while (pcount <= n){
		  if (pcount > 1){
			fibn_2 = fibn_1;
			fibn_1 = fibn;
			fibn = (fibn_1 + fibn_2) % 10; 
		  }		  
		  pcount++;
	//	  System.out.print(fibn + ", ");
	  }
		
          return fibn;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int c = getFibonacciLastDigit(n);
        System.out.println(c);
    }
}

