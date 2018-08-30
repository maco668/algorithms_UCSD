import java.util.Random;
import java.util.Scanner;


/*public class FractionalKnapsack {
    private static double getOptimalValue(int capacity, int[] values, int[] weights) {
        double totalValue = 0;
        //write your code here
		Item[] ip =  new Item[values.length];
		//int[] order = new int[values.length];
		double value = 0;		
		for(int i = 0; i < values.length; i++){
			ip[i].value = values[i];
			ip[i].weight = weights[i];
			ip[i].vw = (double)values[i]/weights[i];
		}		
		
		selectionSort(ip);

		int i = 0;		
		while((i < values.length) && (capacity > 0)){
				if (capacity >= ip[i].weight){
					totalValue += (double)ip[i].value;
					capacity -= ip[i].weight;
				}
				else {
					totalValue += capacity * ip[i].vw;
					capacity -= capacity;
				}
				i++;
		}
		
        return totalValue;
    }*/
	
public class Item{
		
		private int value;
		private int weight;
		private double vw;
		
		public Item(int a, int b){
			this.value = a;
			this.weight = b;
			this.vw = (double)a / b;
		}
		
		public void printItem(){
			//System.out.println();
		//for (Item i : ip){
			System.out.print("value = " + this.value + ", weight = " 
			+ this.weight + ", vw = " + this.vw);
		
		System.out.println();
		} 
		
		
//	}
	
	public static void selectionSort(Item[] ip){	
		
		for(int i = 0; i < ip.length; i++){
			int max = i;
			double maxVw = ip[max].vw;
			for(int j = i; j < ip.length; j++){
				if (ip[j].vw > maxVw){
					maxVw = ip[j].vw;
					max = j;
				}				
			}
			if (max != i){
				Item dummy = ip[i];				
				ip[i] = ip[max];				
				ip[max] = dummy;
				
			}			
		//	System.out.print(ip[i].vw+" ");
		}
		//return vw;
		System.out.println();
		for (Item i : ip){
			System.out.print(i.vw + " ");
			System.out.print(i.value + " ");
			System.out.print(i.weight + " ");
		}
		System.out.println();
	}
			

    public static void main(String args[]) {
        //Scanner scanner = new Scanner(System.in);
		//int n = scanner.nextInt();
        //int capacity = scanner.nextInt();
		//int[] values = new int[n];
		//int[] weights = new int[n];
    /*    for (int i = 0; i < n; i++) {
            values[i] = scanner.nextInt();
            weights[i] = scanner.nextInt();
        }*/
		
		int n = 3;
		int capacity = 50;
		int[] values = {60, 100, 120};
		int[] weights = {20, 50, 30};
		
		Item[] ip =  new Item[values.length];
		//int[] order = new int[values.length];
		double value = 0;		
		for(int i = 0; i < values.length; i++){
			ip[i] = new Item(values[i], weights[i]);
			//ip[i].weight = weights[i];
			//ip[i].vw = (double)values[i]/weights[i];
		}		
		
		for(int i = 0; i < values.length; i++)
			ip[i].printItem();
		
  //      System.out.println(getOptimalValue(capacity, values, weights));
		
		
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
