import java.util.Random;
import java.util.Scanner;


public class FractionalKnapsack {
    private static double getOptimalValue(int capacity, int[] values, int[] weights) {
		
	    double[][] ip = new double[values.length][3];

		double value = 0;		
		for(int i = 0; i < values.length; i++){
			ip[i][0] = values[i];
			ip[i][1] = weights[i];
			ip[i][2] = (double)values[i]/weights[i];			
		}		
		
/*		for(int i = 0; i < values.length; i++)
			System.out.println(ip[i][0] + ", " + ip[i][1] + ", " + ip[i][2]);
*/		
        double totalValue = 0;
      
		
		selectionSort(ip);

		// greedy algorithm taking the maximum value-to-weight ratio item
		int i = 0;		
		while((i < values.length) && (capacity > 0)){
				if (capacity >= ip[i][1]){
					totalValue += ip[i][0];
					capacity -= ip[i][1];
				}
				else {
					totalValue += capacity * ip[i][2];
					capacity -= capacity;
				}
				//System.out.print(totalValue);
				i++;
		}
		
        return totalValue;
    }
	
	// sort in descending order
	private static void selectionSort(double[][] ip){	
		
		for(int i = 0; i < ip.length; i++){
			int max = i;
			double maxVw = ip[max][2];
			for(int j = i; j < ip.length; j++){
				if (ip[j][2] > maxVw){
					maxVw = ip[j][2];
					max = j;
				}				
			}
			if (max != i){
				double[] dummy = ip[i];				
				ip[i] = ip[max];				
				ip[max] = dummy;
				
			}			
		//	System.out.print(ip[i].vw+" ");
		}
		//return vw;
/*		System.out.println();
		for (int i = 0 ; i < ip.length; i++){
			System.out.println(ip[i][2] + ", " + ip[i][0] + ", " + ip[i][1]);			
		}
		System.out.println();
*/
	}
			

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
		int n = scanner.nextInt();
        int capacity = scanner.nextInt();
		int[] values = new int[n];
		int[] weights = new int[n];
        for (int i = 0; i < n; i++) {
            values[i] = scanner.nextInt();
            weights[i] = scanner.nextInt();
        }
		
/*  random number stress test
		Random rand = new Random();
		int n = rand.nextInt(1000)+1; // 1 <= n <1e3
		int capacity = rand.nextInt(2000000+1); // 0 <= capacity <= 2e6
		System.out.println(n + " " + capacity + "\n");
		
		int[] values = new int[n];
		int[] weights = new int[n];
		
		for (int i = 0; i < n; i++) {
            values[i] = rand.nextInt(2000000+1);
			weights[i] = rand.nextInt(2000000)+1;
            System.out.print("(" + values[i] + " " + weights[i] + ") ");
		}
		System.out.println();
*/	
		
		
        System.out.println(getOptimalValue(capacity, values, weights));
		
		
		// test the selectionsort on int, double, pass-by-value
/*		int n = 20;
		//double[] vw = new double[n];
		double[] vw = new double[n];
		Random rand =  new Random();
		for (int i = 0; i < n; i++) {
            vw[i] = (double)rand.nextInt(10);
            System.out.print(vw[i]+" ");
		}
		System.out.println();
		selectionSort(vw);
		System.out.println();
		for (int i = 0; i < n; i++) {
            System.out.print(vw[i]+" ");
		}*/
    }
} 
