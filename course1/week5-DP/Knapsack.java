import java.util.*;

public class Knapsack {
    static int optimalWeight(int W, int[] w) {
        //write you code here
        int result = 0;
/*        for (int i = 0; i < w.length; i++) {
          if (result + w[i] <= W) {
            result += w[i];
          }
        }

*/		
		int[][] value = new int[W+1][w.length+1];
		for(int j = 0; j <= w.length; j++ )
			for(int wi = 0; wi <= W; wi++ ){
				value[wi][j] = 0;
				System.out.println("value[" + wi + "][" + j + "]=" + value[wi][j]);
			}
			
		//System.out.println("W+1=" + (W+1) + " w.length+1=" + (w.length+1));
		//System.out.println("value=" + value[W][w.length]);
		
		
		
		int val;
		int i=1;
		int wi=1;
		for( i = 1; i<= w.length; i++){
			for( wi=1; wi <= W; wi++){
				value[wi][i] = value[wi][i-1];
				if (w[i-1] <= wi){
					val = value[wi-w[i-1]][i-1] + w[i-1];
					if (value[wi][i] < val)
						value[wi][i] = val;
				}
					
			}
		}
		//System.out.println("i=" + i + " wi=" + wi);
		//System.out.println("value=" + value[W][w.length]);
		result = value[W][w.length];
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int W, n;
        W = scanner.nextInt();
        n = scanner.nextInt();
        int[] w = new int[n];
        for (int i = 0; i < n; i++) {
            w[i] = scanner.nextInt();
        }
        System.out.println(optimalWeight(W, w));
    }
}

