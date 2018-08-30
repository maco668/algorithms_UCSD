import java.util.Scanner;

public class Fib {
  private static long calc_fib(int n) {
    
    if (n <= 1)
        return n;
    else 
        return calc_fib(n - 1) + calc_fib(n - 2);
 
  }
  
  private static long calc_fibfast(int n){
  
   //   long[] numbers = new long[n+1];
      
	  long fibn = 1;
	  long fibn_1 = 0;
	  long fibn_2 = 0;
	  int pcount = 1;
	  
	  if (n==0)
		  return 0;
	  else{
		while (pcount <= n){
		  if (pcount > 1){
			fibn_2 = fibn_1;
			fibn_1 = fibn;
			fibn = fibn_1 + fibn_2; 
		  }		  
		  pcount++;
	//	  System.out.print(fibn + ", ");
	  }
	/*  for (int i=0; i <= n; i++){
          if (i<2)
              numbers[i] = i;
          else
              numbers[i] = numbers[i-1]+numbers[i-2];
          if (i > 0)
              System.out.print(numbers[i]+", ");
      }*/
 /*     for (int j=1; j<=10; j++){
          System.out.println("");
          for (int i=1; i<=n; i++)
              System.out.print(numbers[i]%j+", ");
      }*/
      return fibn;
	  }
  }

  public static void main(String args[]) {
    Scanner in = new Scanner(System.in);
    int n = in.nextInt();
    System.out.println(Fib.calc_fibfast(n));
//   System.out.println(Fib.calc_fibfast(n));
 //   System.out.println(Fib.calc_fib(n));
 //   System.out.println(Fib.calc_fibfast(n));
  }
}
