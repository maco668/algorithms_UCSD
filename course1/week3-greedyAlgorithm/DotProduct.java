import java.util.*;
import java.math.BigInteger;

public class DotProduct {
    private static long minDotProduct(int[] a, int[] b) {
        //write your code here
		
		selectionSortDes(a);
		selectionSortAsc(b);
		
		BigInteger resultBig = new BigInteger("0");
        long result = 0;
        for (int i = 0; i < a.length; i++) {
			BigInteger ai = BigInteger.valueOf(a[i]);
			BigInteger bi = BigInteger.valueOf(b[i]);
            resultBig = resultBig.add(ai.multiply(bi));
        }		

        //return result;
		return resultBig.longValue();
    }
	
	// sort in descending order
	private static void selectionSortDes(int[] a){	
		
		for(int i = 0; i < a.length; i++){
			int max = i;
			int maxV = a[max];
			for(int j = i; j < a.length; j++){
				if (a[j] > maxV){
					maxV = a[j];
					max = j;
				}				
			}
			if (max != i){
				int dummy = a[i];				
				a[i] = maxV;				
				a[max] = dummy;
				
			}					
		}
		
		/*System.out.println();
		for (int i = 0 ; i < a.length; i++){
			System.out.print(a[i] + ", ");			
		}
		System.out.println();*/

	}
	
	// sort in ascending order
	private static void selectionSortAsc(int[] b){	
		
		for(int i = 0; i < b.length; i++){
			int min = i;
			int minV = b[min];
			for(int j = i; j < b.length; j++){
				if (b[j] < minV){
					minV = b[j];
					min = j;
				}				
			}
			if (min != i){
				int dummy = b[i];				
				b[i] = minV;				
				b[min] = dummy;
				
			}			
		//	System.out.print(ip[i].vw+" ");
		}
		
/*		System.out.println();
		for (int i = 0 ; i < b.length; i++){
			System.out.print(b[i] + ", ");			
		}
		System.out.println();*/
	}
	

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        int[] b = new int[n];
        for (int i = 0; i < n; i++) {
            b[i] = scanner.nextInt();
        }		
		
		//  random number stress test
/*		Random rand = new Random();
		int n = rand.nextInt(10)+1;
		//int capacity = rand.nextInt(2000000+1);
		System.out.println(n + " " + "\n");
		
		int[] a = new int[n];
		int[] b = new int[n];
		
		for (int i = 0; i < n; i++) {
            a[i] = rand.nextInt(20+1)-10;
			b[i] = rand.nextInt(20+1)-10;
            //System.out.print("(" + values[i] + " " + weights[i] + ") ");
		}
		
		for (int i = 0; i < n; i++)
			System.out.print(a[i] + ", ");		
		System.out.println("\n====================================");
		
		for (int i = 0; i < n; i++)
			System.out.print(b[i] + ", ");		
		System.out.println("\n====================================");	
*/
		
        System.out.println(minDotProduct(a, b));
    }
}

