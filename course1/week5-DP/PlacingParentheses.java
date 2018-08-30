import java.util.Scanner;

public class PlacingParentheses {
    private static long getMaximValue(String exp) {
      //write your code here
	int n = (exp.length() - 1) / 2;
	char[] s = exp.toCharArray(); //char ss = exp.charAt(position);
/*	System.out.println("n=" + n);
	for (int i=0; i<exp.length(); i++)
		System.out.print(s[i]);
	System.out.println();
*/	 
	long[][][] value = new long[n+1][n+1][2]; 
	  
		  
	for(int i = 0; i < n+1; i++){
		// char digit to integer
		value[i][i][0] = (long)Character.getNumericValue(s[i*2]);
		//System.out.println("value[" + i + "][" + i + "][0]=" + value[i][i][0]);
		value[i][i][1] = (long)Character.getNumericValue(s[i*2]);;
	}
			
	for(int dist = 1; dist <= n; dist++)  // distance from the diagonal
		for(int i = 0; i <= n-dist; i++){	
			int j = i + dist;
			//System.out.print("value[" + i + "][" + j + "], ");
			//System.out.print("\n");
			
			// keep min, max registered in the k loop to find corner values
			// refresh these two for every new position in the matrix
			long min = Long.MAX_VALUE; //capital L for class
			long max = Long.MIN_VALUE; //only Double supports NEGATIVE_INFINITY;
			for(int k = i; k < j; k++){							
				long a = eval (value[i][k][0], value[k+1][j][0], s[2*k+1]);				
				long b = eval (value[i][k][0], value[k+1][j][1], s[2*k+1]);
				long c = eval (value[i][k][1], value[k+1][j][0], s[2*k+1]);
				long d = eval (value[i][k][1], value[k+1][j][1], s[2*k+1]);	
				//System.out.println("i=" + k + " j=" + j + " k=" + k);			
				//System.out.println("a=" + a + " b=" + b + " c=" + c + " d=" + d);
				min = min(min, a, b, c, d);
				max = max(max, a, b, c, d);
				//System.out.println("min=" + min);
				//System.out.println("max=" + max);
			}	
			value[i][j][0] = max;
			value[i][j][1] = min;

		}
	  
    return value[0][n][0];
    }

    private static long eval(long a, long b, char op) {
        if (op == '+') {
            return a + b;
        } else if (op == '-') {
            return a - b;
        } else if (op == '*') {
            return a * b;
        } else {
            assert false;
            return 0;
        }
    }
	
	private static long min(long a, long b, long c, long d, long e) {
		
		long min = a;
		if (min > b)
			min = b;
		if (min > c)
			min = c;
		if (min > d)
			min = d;
		if (min > e)
			min = e;
		return min;
	}
	
	private static long max(long a, long b, long c, long d, long e) {
		
		long max = a;
		if (max < b)
			max = b;
		if (max < c)
			max = c;
		if (max < d)
			max = d;
		if (max < e)
			max = e;
		return max;
	}

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String exp = scanner.next();
        System.out.println(getMaximValue(exp));
    }
}

