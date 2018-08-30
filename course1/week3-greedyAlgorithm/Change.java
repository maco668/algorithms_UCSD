import java.util.Scanner;

public class Change {
    private static int getChange(int n) {
        //write your code here
		
		int[] denominations = {10, 5, 1};
		int quotient = -1;
		int i = 0;
		int change=0;
		while ((i < 3) && (n > 0)){
			
			quotient = n / denominations[i];
			if (quotient > 0){
				change += quotient;
				n %= denominations[i];
				/*if (i==0)
					System.out.print(n + " = " + denominations[i] + " * " + quotient);
				else 
					System.out.print(" + " + denominations[i] + " * " + quotient);*/
			}
			i++;			
		}
		//System.out.println();
        return change;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        System.out.println(getChange(n));

    }
}

