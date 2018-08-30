import java.util.*;

// prints out mods from 2 to 91 of the first 92 Fibonacci numbers 
public class FibonacciMod2To92 {
    private static long getFibonacciHuge(long n, long m) {
        //write your code here	
		
		//long fibn=1;
		long modn = 1;
		long modn_1 = 0;
		long period = 1;
		
		while (!(modn == 0 && modn_1 ==1) && period <= n){
			modn_1 = modn; 
			modn = fibfast(period) % m;	
			period++;
//			System.out.print(modn+", ");
		}
//		System.out.println();
//		System.out.println("period = "+(--period));
		return --period;
	}
    
	private static long fibfast(long n){
  
		long fibn = 1;
		long fibn_1 = 0;
		long fibn_2;
		long fibcount = 1;
		
//		System.out.print(fibn+", ");
			
		while (fibcount < n){
			
			fibn_2 = fibn_1;
			fibn_1 = fibn;			
			fibn = fibn_1 + fibn_2;
			fibcount++;
 //           System.out.print(fibn+", ");
		}
		
		return fibn;
				
		}
  
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
//        long n = scanner.nextLong();
		
//		FibonacciHuge.fibfast(n);
//        long m = scanner.nextLong();
		for (long i=2; i<92; i++)
			System.out.print(getFibonacciHuge(92, i)+", ");
//       System.out.println(getFibonacciHuge(n, m));
    }
}

