import java.util.Random;
import java.util.Scanner;


public class FractionalKnapsack {
    private static double getOptimalValue(int capacity, int[] values, int[] weights) {
        double totalValue = 0;
        //write your code here
		Item[] IP =  new Item[values.length];
		int[] order = new int[values.length];
		double value = 0;		
		for(int i = 0; i < values.length; i++){
			vw[i] = ((double)values[i]) / weights[i];
		}
		
		selectionSort(vw, order);

		int i = 0;		
		while((i < values.length) && (capacity > 0)){
				if (capacity >= weights[order[i]]){
					totalValue += (double)values[order[i]];
					capacity -= weights[order[i]];
				}
				else {
					totalValue += ((double)capacity) / weights[order[i]] * values[order[i]];
					capacity -= capacity;
				}
				i++;
		}
		
        return totalValue;
    }
	
	public class Item{
		
		int value;
		int weight;
		double vw;
		
		public Item(int a, int b){
			value = a;
			weight = b;
			vw = (double)a / b;
		}
		
	}
	
	private static void selectionSort(double[] vw, int[] order){	
		
		for(int i = 0; i < vw.length; i++){
			int max = i;
			for(int j = i; j < vw.length; j++)
				if (vw[j] > vw[max])
					max = j;
			if (max != i){
				double dummy = vw[i];
				int dummy2 = order[i];
				vw[i] = vw[max];
				order[i] = order[max];
				vw[max] = dummy;
				order[max] = dummy2;
			}			
			System.out.print(vw[i]+" ");
		}
		//return vw;
		System.out.println();
		for (int i : order)
			System.out.print(i + " ");
		System.out.println();
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
