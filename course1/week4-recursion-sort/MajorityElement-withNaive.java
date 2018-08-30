import java.util.*;
import java.io.*;

public class MajorityElement {
    private static int getMajorityElement(int[] a, int left, int right) {
//		System.out.println("left=" + left + " right=" + right);
        if (left == right) {
            return a[left];
        }
/*      if ((left + 1 == right) )			
            return a[left];
		else
			return -1;
*/        
        //write your code here
		
		int mid = (left + right) / 2;	
//		System.out.println("mid=" + mid);
		
		int r1 = getMajorityElement(a, left, mid);
//		System.out.println("r1=" + r1);
		int r2 = getMajorityElement(a, mid+1, right);	
//		System.out.println("r2=" + r2);		
		
		if (r1 == r2)
			return r1;
		else{
			int n_r1=0, n_r2=0;							
				
			// count the appearances of majority element in left and right
			for(int i=left; i<=mid; i++)
				if (a[i] == r1)
					n_r1 +=1;
				else if (a[i] == r2)
					n_r2 +=1;			
			
			for(int i=mid+1; i<=right; i++)
				if (a[i] == r1)
					n_r1 +=1;
				else if (a[i] == r2)
					n_r2 +=1;			 			
	
//			System.out.println("n_r1=" + n_r1 + " n_r2=" + n_r2);
			
			int nd2 = (right - left +1)/2;
//			System.out.println("nd2=" + nd2);
			if (n_r1 > nd2) //strictly more than n/2
				return r1;			
			else if (n_r2 > nd2)
				return r2;
			else 
				return -1;
		}
	}		
				
    
	
	private static int getMajorityElementNaive(int[] a) {
		int[] b = new int[a.length];
		b[0] = a[0];
/*		for(int i=1; i<a.length; i++){
			while 
			if (b[i] != a[i])
				
		}*/
		return -1;
	}

    public static void main(String[] args) {
        FastScanner scanner = new FastScanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
       
/*
		Random rand = new Random();
		
		int n = rand.nextInt(100000) + 1;
		
		System.out.println("n = " + n); 
		
		int[] a = new int[n];
		for(int i=0; i<n; i++){
			a[i] = rand.nextInt(1000000000 + 1);
			System.out.print(a[i] + " ");
		}

		
//		System.out.println("a.length-1=" + (a.length-1));
		System.out.println();
*/
		if (getMajorityElement(a, 0, (a.length-1)) != -1) {
				System.out.println(1);
		} 
		else {
				System.out.println(0);
		}
    }		
		

    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner(InputStream stream) {
            try {
                br = new BufferedReader(new InputStreamReader(stream));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }
}

