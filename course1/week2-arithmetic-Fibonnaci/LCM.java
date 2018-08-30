import java.util.*;

public class LCM {
  private static long lcm(long a, long b) {
    //write your code here
      
    long GCD = EuclidGCD(a, b);
    a = a / GCD;
    b = b / GCD;
    
    return a * b * GCD;
  }

  private static long EuclidGCD(long a, long b){
      long remainder =-1;
      if (b == 0)
          return a;

      while (remainder != 0){
          remainder = a % b;
          a = b;
          b = remainder;
      }
      
      return a; 
  }
    
  public static void main(String args[]) {
    Scanner scanner = new Scanner(System.in);
    long a = scanner.nextLong();
    long b = scanner.nextLong();

    System.out.println(lcm(a, b));
  }
}
