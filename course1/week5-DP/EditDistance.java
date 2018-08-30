import java.util.*;

class EditDistance {
  public static int EditDistance(String s, String t) {

    //write your code here
    
	int n = s.length();
	int m = t.length();
	int[][] D = new int[n+1][m+1];
	
	char[] A = s.toCharArray();
	char[] B = t.toCharArray();
	
	
	for(int i = 0; i <= n; i++)
		D[i][0] = i;
	
	for(int j = 0; j <= m; j++)
		D[0][j] = j;
	
	for(int j = 1; j <= m; j++)
		for(int i = 1; i <= n; i++){
			int minD = D[i][j-1] + 1;
			int D2 = D[i-1][j] + 1;
			int D3 = Integer.MAX_VALUE;
			if(A[i-1] == B[j-1])
				D3 = D[i-1][j-1];
			else
				D3 = D[i-1][j-1] + 1;
			
			if (minD > D2)
				minD = D2;
			if (minD > D3)
				minD = D3;		
			D[i][j] = minD;
			
		}
		
	return D[n][m];
  } 
  public static void main(String args[]) {
    Scanner scan = new Scanner(System.in);

    String s = scan.next();
    String t = scan.next();

    System.out.println(EditDistance(s, t));
  }

}
