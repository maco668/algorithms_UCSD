import java.util.*;
import java.math.BigInteger;

public class FibonacciHuge {
    private static long getFibonacciHuge(long n, long m) {
        //write your code here 
  
  //long fibn=1;
  BigInteger modn = new BigInteger("1");
  BigInteger modn_1 = new BigInteger("0");
  BigInteger mbig = BigInteger.valueOf(m);
  long period = 1;
  
  BigInteger fibn = new BigInteger("1");
  BigInteger fibn_1 = new BigInteger("0");
  BigInteger fibn_2;
  //long fibcount = 1;
  
  while (!(modn.equals(modn.ZERO) && modn_1.equals(modn_1.ONE) ) && period <= n){
   
   if (period > 1){
    fibn_2 = fibn_1;
    fibn_1 = fibn;   
    fibn = fibn_1.add(fibn_2);
   }
//            System.out.print(fibn+", ");
   
   modn_1 = modn;    
   modn = fibn.mod(mbig); 
   period++;
 //  System.out.print(modn+", ");
  }
 // System.out.println("");
 // System.out.println("period = " + (--period));
  period--;
  long nth = n % period; ;
	if (nth == 0)
		return modn.longValue();	
   else {
	fibn = fibn.valueOf(1);  
	fibn_1 = fibn_1.valueOf(0);
	period = 1;
  
   //System.out.print(fibn + ", " + fibn_1);
   //System.out.println();
   while (period <= nth){
   
    if (period > 1){
     fibn_2 = fibn_1;
     fibn_1 = fibn;   
     fibn = fibn_1.add(fibn_2);    
    }
//    System.out.print(fibn+", "); 
    period++;
   }
//   System.out.println("period = "+(--period));
//  System.out.println(fibn);
   modn = fibn.mod(mbig);
   return modn.longValue();
  }
 }
    
/* private static BigInteger fibfast(long n){
  
  BigInteger fibn = new BigInteger("1");
  BigInteger fibn_1 = new BigInteger("0");
  BigInteger fibn_2;
  long fibcount = 1;
  
  System.out.print(fibn+", ");
   
  while (fibcount < n){
   
   fibn_2 = fibn_1;
   fibn_1 = fibn;   
   fibn = fibn_1.add(fibn_2);
   fibcount++;
            System.out.print(fibn+", ");
  }
  
  return fibn;
    
  }*/
  
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long n = scanner.nextLong();
  
//  FibonacciHuge.fibfast(n);
        long m = scanner.nextLong();
//  for (long i=2; i<92; i++)
//   System.out.print(getFibonacciHuge(92, i)+", ");
        System.out.println(getFibonacciHuge(n, m));
    }
}

