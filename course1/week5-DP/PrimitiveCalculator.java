/*
By Ding Ma on 05/08/2016. 

Task: Given an integer n, compute the minimum number of operations needed to obtain the number n
starting from the number 1.

Input Format: The input consists of a single integer 1 <= n <= 1e6.

Output Format. In the first line, output the minimum number k of operations needed to get n from 1.
In the second line output a sequence of intermediate numbers. That is, the second line should contain
positive integers a0; a2; : : : ; ak-1 such that a0 = 1, ak-1 = n and for all 0 <= i < k - 1, ai+1 is equal to
either ai + 1, 2ai, or 3ai. If there are many such sequences, output any one of them.

Time Limits. C: 1 sec, C++: 1 sec, Java: 1.5 sec, Python: 5 sec. C#: 1.5 sec, Haskell: 2 sec, JavaScript:
3 sec, Ruby: 3 sec, Scala: 3 sec.

Memory Limit. 128Mb.
*/

import java.util.*;

public class PrimitiveCalculator {
    private static List<Integer> optimal_sequence(int n) {
        List<Integer> sequence = new ArrayList<Integer>();

		int[] minNumOp = new int[n+1];
		Arrays.fill(minNumOp, Integer.MAX_VALUE);
		
		minNumOp[0] = 0;
		minNumOp[1] = 0;

		//Store the intermediate result one operation before	
		int[] previous = new int[n+1]; 
		
		int numOp;
		//System.out.println("n=" + n);
		
		//DP to find minimum number of operation for every previous number
		for(int i = 1; i <= n; i++ ){
			//determine which of the three operations is taken to reach i
			for (int j = 0; j < 3; j++){				
				//1st tested operation: divided by 3
				if ((j ==0) && (i % 3 == 0)){ 
					numOp = minNumOp[i / 3] + 1;
					if (numOp < minNumOp[i]){
						minNumOp[i] = numOp;	
						previous[i] = i / 3;
					}
				}
				//2nd tested operation: divided by 2
				else if ((j == 1) && (i % 2 == 0)){	
					numOp = minNumOp[i / 2] + 1;
					if (numOp < minNumOp[i]){
						minNumOp[i] = numOp;	
						previous[i] = i / 2;
					}						
				}
				// if 
				else if ((j == 2) && (i - 1 > 0)){	
					numOp = minNumOp[i - 1] + 1;
					if (numOp < minNumOp[i]){
						minNumOp[i] = numOp;	
						previous[i] = i - 1;
					}						
				}
			}
		}
		
		sequence.add(n);
		while(n > 1){
			sequence.add(previous[n]);
			n = previous[n];
		}
				
		Collections.reverse(sequence);
		
		return sequence;
	}										
        
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        List<Integer> sequence = optimal_sequence(n);
        System.out.println(sequence.size() - 1);
        for (Integer x : sequence) {
            System.out.print(x + " ");
        }
    }
}

